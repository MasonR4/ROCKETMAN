package server_utilities;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import controller.GameEvent;
import data.GameLobbyData;
import data.GenericRequest;
import data.PlayerAction;
import data.PlayerStatistics;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import data.StartGameData;
import game_utilities.Block;
import game_utilities.Missile;
import game_utilities.Player;
import game_utilities.RocketLauncher;
import game_utilities.SpawnBlock;
import menu_utilities.EightBitLabel;
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
	
	private int missileCounter = 0;
	
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
	private ConcurrentHashMap<String, PlayerStatistics> playerInfo = new ConcurrentHashMap<String, PlayerStatistics>();
	private ConcurrentHashMap<String, ConnectionToClient> playerConnections = new ConcurrentHashMap<String, ConnectionToClient>();
	
	private ConcurrentHashMap<String, RocketLauncher> launchers = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Missile> rockets = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	
	// these might be useful might not be 
	private ConcurrentLinkedQueue<PlayerAction> inboundEventQueue = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<GameEvent> outboundEventQueue = new ConcurrentLinkedQueue<>();
	
	private final ReentrantLock lock = new ReentrantLock();
	
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
		for (Entry<String, PlayerStatistics> e : playerInfo.entrySet()) {
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
		blocks.putAll(server.loadMap(info.getMap()));
		CopyOnWriteArrayList<SpawnBlock> spawns = new CopyOnWriteArrayList<>();
		for (Block s : blocks.values()) {
			if (s instanceof SpawnBlock) {
				spawns.add((SpawnBlock) s);
			}
		}
		for (Entry<String, PlayerStatistics> e : playerInfo.entrySet()) {
			Player newPlayer = new Player(20, random.nextInt(50, 850), random.nextInt(50, 850));
			RocketLauncher newLauncher = new RocketLauncher((int) newPlayer.getCenterX(), (int) newPlayer.getCenterY(), 24, 6);
			newPlayer.setUsername(e.getKey());
			newPlayer.setBlocks(blocks);
			newPlayer.setColor(new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255)));
			boolean spawned = false;
			while (!spawned) {
				int chosenSpawn = random.nextInt(spawns.size());
				if (spawns.get(chosenSpawn).isOccupied()) {
					spawned = false;
				} else {
					newPlayer.updatePosition((int) spawns.get(chosenSpawn).getCenterX(), (int) spawns.get(chosenSpawn).getCenterY());
					spawns.get(chosenSpawn).setOccupied(true);
					spawned = true;
				}
			}
			newLauncher.setOwner(newPlayer.getUsername());
			launchers.put(newPlayer.getUsername(), newLauncher);
			players.put(e.getKey(), newPlayer);
		}
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
		}
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
	}
	
	public void addPlayer(ConnectionToClient c, PlayerJoinLeaveData usr) {
		PlayerStatistics temp = new PlayerStatistics();
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
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
	}
	
	public boolean playersReady() {
		boolean playersReady = true;
		for (PlayerStatistics p : playerInfo.values()) {
			if (!p.isReady()) {
				playersReady = false;
			}
		}
		return playersReady;
	}
	
	public void updatePlayerInfoInLobbyForClients(ArrayList<PlayerJoinLeaveData> a) {
		GenericRequest rq = new GenericRequest("LOBBY_PLAYER_INFO");
		rq.setData(a);
		updateClients(rq);
	}
	
	public synchronized void updateClients(Object data) {
		for (ConnectionToClient c : playerConnections.values()) {
				try {
					c.sendToClient(data);
				} catch (IOException CLIENT_DOESNT_LIKE_YOU) {
					CLIENT_DOESNT_LIKE_YOU.printStackTrace();
					server.logMessage("could not update client " + c.getId());
				}
		}
	}
	
	public GameLobbyData generateGameListing() {
		GameLobbyData tempInfo = new GameLobbyData(lobbyName, hostUsername, playerCount, playerCap, gameID);
		return tempInfo;
	}
	
	public void handlePlayerAction(PlayerAction a) {
		lock.lock();
		String usr = a.getUsername();
		String type = a.getType();
		switch (type) {
		case "MOVE":
			players.get(usr).updatePosition(a.getX(), a.getY());
			players.get(usr).setVelocity(a.getAction());
			break;
		case "CANCEL_MOVE":
			players.get(usr).updatePosition(a.getX(), a.getY());
			players.get(usr).cancelVelocity(a.getAction());
			break;
		case "LAUNCHER_ROTATION":
			launchers.get(usr).rotate(a.getMouseX(), a.getMouseY());
			break;
		case "ROCKET_FIRED":
			Missile missile = new Missile((int) launchers.get(usr).getCenterX(), (int) launchers.get(usr).getCenterY(), usr);
			missile.setDirection(a.getMouseX(), a.getMouseY());
			missile.setBlocks(blocks);
			missile.setPlayers(players);
			rockets.put(missileCounter, missile);
			a.setMissileNumber(missileCounter);
			missileCounter++;
			break;
		}
		lock.unlock();
		updateClients(a);
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
		rq1.setData(players, "PLAYERS");
		rq1.setData(blocks, "MAP");
		updateClients(rq1);
		
		while (gameStarted) {
			long startTime = System.currentTimeMillis();
			
			for (Player p : players.values()) {
				p.move();
				launchers.get(p.getUsername()).moveLauncher((int) p.getCenterX(), (int) p.getCenterY(), p.getBlockSize());
			}
			for (Iterator<Map.Entry<Integer, Missile>> it = rockets.entrySet().iterator(); it.hasNext();) {
				System.out.println("iterato missile " + rockets.values().size());
				Map.Entry<Integer, Missile> entry = it.next();
				Missile r = entry.getValue();
				if (!r.isExploded()) {
					int col = r.checkCollision();
					String hit = r.checkPlayerCollision();
					if (col == -1 || col > 0) {
						GameEvent g = new GameEvent();
						g.addEvent("MISSILE_EXPLODES", entry.getKey());
						System.out.println("BOOOM exploded missile " + entry.getKey());
						if (col != -1) {
							g.addEvent("BLOCK_DESTROYED", col);
							blocks.remove(col);
						}
						outboundEventQueue.add(g);
					} else if (!hit.isBlank()) {
						GameEvent g = new GameEvent();
						g.addEvent("MISSILE_EXPLODES", entry.getKey());
						System.out.println("BOOOM exploded missile " + entry.getKey());
						System.out.println("PLAYER HIT BRUIH");
						g.addEvent("PLAYER_HIT", hit);
						EightBitLabel msg = new EightBitLabel(r.getOwner() + " exploded " + hit, Font.PLAIN, 25f);
						g.addEvent("LOG_MESSAGE", msg);
						outboundEventQueue.add(g);
						//players.get(hit).die(r.getOwner());
					}
					if(r.isExploded()) {
						it.remove();
					}
				}
			}
			GameEvent g;
			while ((g = outboundEventQueue.poll()) != null) {
			    updateClients(g);
			}
			long endTime = System.currentTimeMillis();
			long delta = endTime - startTime;
			long sleepTime = TARGET_DELTA - delta;
			try {
				if (sleepTime <= 0) {
					sleepTime = TARGET_DELTA;
				}
				Thread.sleep(sleepTime);
				
			} catch (InterruptedException DEAD) {
				Thread.currentThread().interrupt();
				gameStarted = false;
			}
		}
	}
}
