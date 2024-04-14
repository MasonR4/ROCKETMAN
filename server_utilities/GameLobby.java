package server_utilities;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import data.GameLobbyData;
import data.GenericRequest;
import data.LiveMissileData;
import data.PlayerActionData;
import data.PlayerData;
import data.PlayerJoinLeaveData;
import data.PlayerPositionsData;
import data.PlayerReadyData;
import data.StartGameData;
import game_utilities.Block;
import game_utilities.Missile;
import game_utilities.Player;
import game_utilities.RocketLauncher;
import ocsf.server.ConnectionToClient;
import server.Server;

public class GameLobby implements Runnable {
	private final long TARGET_DELTA = 16;
	
	private Server server;
	private boolean gameStarted = false;
	
	private String lobbyName;
	private String hostUsername;
	
	private int playerCount = 0;
	private int playerCap;
	private int gameID;
	
	private Random random = new Random();
	
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
	private ConcurrentHashMap<String, PlayerData> playerInfo = new ConcurrentHashMap<String, PlayerData>();
	private ConcurrentHashMap<String, ConnectionToClient> playerConnections = new ConcurrentHashMap<String, ConnectionToClient>();
	
	private ConcurrentHashMap<String, RocketLauncher> launchers = new ConcurrentHashMap<>();
	private CopyOnWriteArrayList<Missile> rockets = new CopyOnWriteArrayList<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	
	public GameLobby(String n, String hn, int mp, int gid, Server s) {
		lobbyName = n;
		hostUsername = hn;
		gameID = gid;
		playerCap = mp;
		server = s;
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public String getlobbyName() {
		return lobbyName;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
	
	public ArrayList<String> getPlayerUsernames() {
		List<String> temp = playerInfo.keySet().stream().collect(Collectors.toList());
		return new ArrayList<>(temp);
	}
	
	public ArrayList<PlayerJoinLeaveData> getJoinedPlayerInfo() {
		ArrayList<PlayerJoinLeaveData> joinedplayerInfo = new ArrayList<PlayerJoinLeaveData>();
		for (Entry<String, PlayerData> e : playerInfo.entrySet()) {
			PlayerJoinLeaveData player = new PlayerJoinLeaveData(e.getKey());
			player.setReady(e.getValue().isReady());
			player.setHost(hostUsername.equals(e.getKey()));
			player.setGameID(gameID);
			joinedplayerInfo.add(player);
		}
		return joinedplayerInfo;
	}
	
	public boolean isFull() {
		return playerCount == playerCap;
	}
	
	public boolean isEmpty() {
		return playerCount == 0;
	}
	
	public boolean isStarted() {
		return gameStarted;
	}
	
	public void startGame(StartGameData info) {
		for (Entry<String, PlayerData> e : playerInfo.entrySet()) {
			// TODO prevent players from spawning within blocks on the map
			Player newPlayer = new Player(20, random.nextInt(50, 850), random.nextInt(50, 850));
			RocketLauncher newLauncher = new RocketLauncher((int) newPlayer.getCenterX(), (int) newPlayer.getCenterY(), 24, 6);
			newPlayer.setUsername(e.getKey());
			newPlayer.setBlocks(blocks);
			newPlayer.setColor(new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255)));
			newLauncher.setOwner(newPlayer.getUsername());
			launchers.put(newPlayer.getUsername(), newLauncher);
			players.put(e.getKey(), newPlayer);
		}
		
		blocks.putAll(server.loadMap(info.getMap()));
		
		GenericRequest mapInfo = new GenericRequest("MAP_INFO");
		mapInfo.setData(blocks);
		updateClients(mapInfo);
		
		gameStarted = true;		
	}
	
	public void stopGame() {
		gameStarted = false;
		Thread.currentThread().interrupt();		
	}
	
	// TODO remove players from running games if they disconnect (low priority)
	public void removePlayer(ConnectionToClient c, PlayerJoinLeaveData usr) {
		playerCount -= 1;
		playerInfo.remove(usr.getUsername());
		playerConnections.remove(usr.getUsername());
		if (usr.getUsername().equals(hostUsername)) {
			String[] usernames = playerInfo.keySet().toArray(new String[0]);
			if (usernames.length > 0) {
				hostUsername = usernames[0];
			}			
		} 
		if (playerCount <= 0) {
			gameStarted = false;
			server.cancelGame(gameID, true);
			System.out.println("Game is toast");
		}
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
	}
	
	public void addPlayer(ConnectionToClient c, PlayerJoinLeaveData usr) {
		PlayerData temp = new PlayerData();
		temp.setUsername(usr.getUsername());
		if (playerCount == 0) {
			hostUsername = usr.getUsername();
			temp.setHost();
		}
		playerCount += 1;
		playerConnections.put(usr.getUsername(), c);
		playerInfo.put(usr.getUsername(), temp);
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
	}
	
	public void readyPlayer(PlayerReadyData r) {
		String name = r.getUsername();
		playerInfo.get(name).setReady(r.isReady());
		setReadyClients();
	}
	
	public void setReadyClients() {
		GenericRequest rq;
		for (Entry<String, PlayerData> e : playerInfo.entrySet()) {
			if (e.getValue().isReady()) {
				rq = new GenericRequest("CONFIRM_READY");
			} else {
				rq = new GenericRequest("CONFIRM_UNREADY");
			}
			updateClients(rq);
		}
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
	}
	
	public void updatePlayerInfoInLobbyForClients(ArrayList<PlayerJoinLeaveData> a) {
		GenericRequest rq = new GenericRequest("LOBBY_PLAYER_INFO");
		rq.setData(a);
		updateClients(rq);
	}
	
	public void updateClients(Object data) {
		for (ConnectionToClient c : playerConnections.values()) {
			synchronized(c) {
				try {
					c.sendToClient(data);
				} catch (IOException CLIENT_DOESNT_LIKE_YOU) {
					CLIENT_DOESNT_LIKE_YOU.printStackTrace();
					server.logMessage("could not update client " + c.getId());
				}
			}
		}
	}
	
	public GameLobbyData generateGameListing() {
		GameLobbyData tempInfo = new GameLobbyData(lobbyName, hostUsername, playerCount, playerCap, gameID);
		return tempInfo;
	}
	
	public void handlePlayerAction(PlayerActionData a) {
		String usr = a.getUsername();
		String type = a.getType();
		switch (type) {
		case "MOVE":
			players.get(usr).updatePosition(a.getX(), a.getY());
			players.get(usr).setVelocity(a.getAction());
			
			//a.setPosition((int) players.get(usr).getX(), (int) players.get(usr).getY());
			updateClients(a);
			break;
		case "CANCEL_MOVE":
			players.get(usr).updatePosition(a.getX(), a.getY());
			players.get(usr).cancelVelocity(a.getAction());
			
			//a.setPosition((int) players.get(usr).getX(), (int) players.get(usr).getY());
			updateClients(a);
			break;
		case "LAUNCHER_ROTATION":
			// TODO fix rotation
			//players.get(usr).getLauncher().rotate(Integer.parseInt(a.getAction().split(",")[0]), Integer.parseInt(a.getAction().split(",")[1]));;
			break;
		case "ROCKET_FIRED":
			Missile missile = new Missile((int) launchers.get(usr).getCenterX(), (int) launchers.get(usr).getCenterY(), usr);
			missile.setDirection(a.getMouseX(), a.getMouseY());
			rockets.add(missile);
		}
	}
	
	public void run() {		
		while (!gameStarted) {
			try {
				Thread.currentThread();
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				
			}
		}
		
		GenericRequest rq1 = new GenericRequest("GAME_STARTED");
		rq1.setData(players);
		updateClients(rq1);
		gameStarted = true;
		
		while (gameStarted) {
			long startTime = System.currentTimeMillis();
			
			PlayerPositionsData ppd = new PlayerPositionsData();
			
			for (Player p : players.values()) {
				p.move();
				launchers.get(p.getUsername()).moveLauncher((int) p.getCenterX(), (int) p.getCenterY(), p.getBlockSize());
				ppd.addPlayerPos(p.getUsername(), p.x, p.y);
			}
			
			//updateClients(ppd);
			
			if (!rockets.isEmpty()) {
				for (Missile m : rockets) {
					m.move();
					// TODO check missile collision and also change to send positional data instead of actual missile objects?
				}
				updateClients(new LiveMissileData(rockets));
			}

			
			
			long endTime = System.currentTimeMillis();
			long delta = endTime - startTime;
			long sleepTime = TARGET_DELTA - delta;
			try {
				Thread.sleep(sleepTime);
				
			} catch (InterruptedException DEAD) {
				Thread.currentThread().interrupt();
				gameStarted = false;
			}
			
		}
	}
}
