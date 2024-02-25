package server_utilities;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;
import data.GameLobbyData;
import data.GenericRequest;
import data.PlayerActionData;
import data.PlayerData;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import data.StartGameData;
import game_utilities.Player;
import ocsf.server.ConnectionToClient;
import server.Server;

public class GameLobby {
	private final long TARGET_DELTA = 16;
	
	private Server server;
	private boolean gameStarted = false;
	
	private String lobbyName;
	private String hostUsername;
	
	private int playerCount = 0;
	private int playerCap;
	private int gameID;
	
	private Random random = new Random();
	
	private LinkedHashMap<String, PlayerData> playerInfo = new LinkedHashMap<String, PlayerData>();
	private LinkedHashMap<String, Player> players = new LinkedHashMap<String, Player>();
	private LinkedHashMap<String, ConnectionToClient> playerConnections = new LinkedHashMap<String, ConnectionToClient>();
	
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
			Player newPlayer = new Player(20, random.nextInt(10, 900), random.nextInt(10, 900));
			newPlayer.setUsername(e.getKey());
			newPlayer.setColor(new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255)));
			players.put(e.getKey(), newPlayer);
		}
		
		//playerLives = info.getPlayerLives();
		//mapName = info.getMap();
		// map = server.getMap(mapName);
		
		GenericRequest rq = new GenericRequest("GAME_STARTED");
		rq.setData(players);
		for (Entry<String, ConnectionToClient> e : playerConnections.entrySet()) {
			try {
				e.getValue().sendToClient(rq);
			} catch (IOException CLIENT_DECEASED) {
				CLIENT_DECEASED.printStackTrace();
				server.logMessage("failed to send game start to [Client " + e.getValue().getId() + "] " + e.getKey());
			}
		}
		gameStarted = true;
	}
	
	public void stopGame() {
		// TODO make this more elaborate 
		// also may not need TBD
		gameStarted = false;
		Thread.currentThread().interrupt();
	}
	
	public void removePlayer(ConnectionToClient c, PlayerJoinLeaveData usr) {
		playerCount -= 1;
		if (usr.getUsername().equals(hostUsername)) {
			playerInfo.remove(usr.getUsername());
			playerConnections.remove(usr.getUsername());
			
			String[] usernames = playerInfo.keySet().toArray(new String[0]);
			if (usernames.length > 0) {
				hostUsername = usernames[0];
			}			
		} else {
			playerInfo.remove(usr.getUsername());
			playerConnections.remove(usr.getUsername());
		}
		
		if (playerCount == 0) {
			gameStarted = false;
			server.cancelGame(gameID);
		}
		updateplayerInfoInLobbyForClients(getJoinedPlayerInfo());
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
		updateplayerInfoInLobbyForClients(getJoinedPlayerInfo());
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
			try {
				playerConnections.get(e.getKey()).sendToClient(rq);
				updateplayerInfoInLobbyForClients(getJoinedPlayerInfo());
			} catch (IOException CLIENT_WAS_NOT_READY) {
				CLIENT_WAS_NOT_READY.printStackTrace();
			}
		}
	}
	
	public void updateplayerInfoInLobbyForClients(ArrayList<PlayerJoinLeaveData> a) {
		GenericRequest rq = new GenericRequest("LOBBY_PLAYER_INFO");
		rq.setData(a);
		updateClients(rq);
	}
	
	public void updateClients(Object data) {
		for (Entry<String, PlayerData> e : playerInfo.entrySet()) {
			try {
				playerConnections.get(e.getKey()).sendToClient(data);
			} catch (IOException CLIENT_DOESNT_LIKE_YOU) {
				CLIENT_DOESNT_LIKE_YOU.printStackTrace();
			}
		}
	}
	
	public GameLobbyData generateGameListing() {
		GameLobbyData tempInfo = new GameLobbyData(lobbyName, hostUsername, playerCount, playerCap, gameID);
		return tempInfo;
	}
	
	public void handlePlayerAction(PlayerActionData data) {
		// TODO rectify actions sent by clients
	}
	
	public void run() {
		Thread.currentThread();
		// TODO yeah...
		while (!Thread.interrupted() && gameStarted) {
			long startTime = System.currentTimeMillis();
			// check collision for all players
			// check collision for rockets and stuff
			// check block updates
			// send info to clients
			server.logMessage("test" + gameID);
			long endTime = System.currentTimeMillis();
			long delta = endTime - startTime;
			long sleepTime = TARGET_DELTA - delta;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				gameStarted = false;
				break;
				
			}
		}
	}
}
