package server_utilities;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import data.GameLobbyData;
import data.GenericRequest;
import data.PlayerActionData;
import data.PlayerData;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import data.StartGameData;
import game_utilities.Player;
import game_utilities.PlayerCollision;
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
	private ArrayList<PlayerActionData> events = new ArrayList<>();
	// TODO here is where we store stuff like player objects, the grid
	// other game relevant stuff
	private int playerLives;
	private String mapName;
	
	// private GameGrid map;
	
	// also all of the game logic that needs to be handled server side goes in here too
	
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
		// TODO start the game
		// this function needs to do a number of things:
		// - load map and sent it to all clients
		// - add in player game object representations
		for (Entry<String, PlayerData> e : playerInfo.entrySet()) {
			Player newPlayer = new Player(20, random.nextInt(50, 850), random.nextInt(50, 850));
			newPlayer.setUsername(e.getKey());
			newPlayer.setColor(new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255)));
			players.put(e.getKey(), newPlayer);
		}
		
		//playerLives = info.getPlayerLives();
		//mapName = info.getMap();
		// map = server.getMap(mapName);
		
//		GenericRequest rq1 = new GenericRequest("GAME_STARTED");
//		rq1.setData(players);
//		updateClients(rq1);
//		gameStarted = true;
		
		//run();
		//new Thread(this).start();
//		GenericRequest update = new GenericRequest("GAME_STATE_UPDATE"); // update clients maybe move
//		update.setData(players);
//		updateClients(update);
		
	}
	
	public void stopGame() {
		// TODO make this more elaborate 
		// also may not need TBD
		gameStarted = false;
		Thread.currentThread().interrupt();		
	}
	
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
		if (playerCount == 0) {
			gameStarted = false;
			server.cancelGame(gameID);
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
			players.get(usr).setVelocity(a.getAction());
			break;
		case "CANCEL_MOVE":
			players.get(usr).cancelVelocity(a.getAction());
		}
	}
	
	public void addEvent(PlayerActionData a) {
		events.add(a);
	}
	
	public void run() {
			// check collision for all players
			// check collision for rockets and stuff
			// check block updates
			// send info to clients
		
		GenericRequest rq1 = new GenericRequest("GAME_STARTED");
		rq1.setData(players);
		updateClients(rq1);
		gameStarted = true;
		
		while (gameStarted) {
			
			long startTime = System.currentTimeMillis();
			
			for (Player p : players.values()) {
				// TODO re-enable collisions after we have added in the map 
				//p.setCollision2(new PlayerCollision("HORIZONTAL", p.checkCollision(p.x + p.getXVelocity(), p.y)));
				//p.setCollision2(new PlayerCollision("VERTICAL", p.checkCollision(p.x, p.y + p.getYVelocity())));
				p.move();
			}
			
			if (!events.isEmpty()) {
				for (PlayerActionData a : events) {
					handlePlayerAction(a);
				}
				GenericRequest rq = new GenericRequest("GAME_STATE_UPDATE");
				rq.setData(players);
				updateClients(rq);
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
