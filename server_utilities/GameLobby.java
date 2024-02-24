package server_utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import data.GameLobbyData;
import data.GenericRequest;
import data.PlayerData;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import data.StartGameData;
import ocsf.server.ConnectionToClient;
import server.Server;

public class GameLobby {
	
	private Server server;
	private boolean gameStarted = false;
	
	private String lobbyName;
	private String hostUsername;
	
	private int playerCount = 0;
	private int playerCap;
	private int gameID;
	
	private LinkedHashMap<String, PlayerData> players = new LinkedHashMap<String, PlayerData>();
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
		List<String> temp = players.keySet().stream().collect(Collectors.toList());
		return new ArrayList<>(temp);
	}
	
	public ArrayList<PlayerJoinLeaveData> getJoinedPlayers() {
		ArrayList<PlayerJoinLeaveData> joinedPlayers = new ArrayList<PlayerJoinLeaveData>();
		for (Entry<String, PlayerData> e : players.entrySet()) {
			PlayerJoinLeaveData player = new PlayerJoinLeaveData(e.getKey());
			player.setReady(e.getValue().isReady());
			player.setHost(hostUsername.equals(e.getKey()));
			player.setGameID(gameID);
			joinedPlayers.add(player);
		}
		return joinedPlayers;
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
		playerLives = info.getPlayerLives();
		mapName = info.getMap();
		
		// map = server.getMap(mapName);
	}
	
	public void removePlayer(ConnectionToClient c, PlayerJoinLeaveData usr) {
		playerCount -= 1;
		ConnectionToClient conn = players.get(usr.getUsername()).getConnection();
		if (usr.getUsername().equals(hostUsername)) {
			players.remove(usr.getUsername());
			playerConnections.remove(usr.getUsername());
			
			String[] usernames = players.keySet().toArray(new String[0]);
			if (usernames.length > 0) {
				hostUsername = usernames[0];
			}			
		} else {
			players.remove(usr.getUsername());
			playerConnections.remove(usr.getUsername());
		}
		
		if (playerCount == 0) {
			server.cancelGame(gameID);
		}
		updatePlayersInLobbyForClients(getJoinedPlayers());
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
		players.put(usr.getUsername(), temp);
		updatePlayersInLobbyForClients(getJoinedPlayers());
	}
	
	public void readyPlayer(PlayerReadyData r) {
		String name = r.getUsername();
		players.get(name).setReady(r.isReady());
		setReadyClients();
	}
	
	public void setReadyClients() {
		GenericRequest rq;
		for (Entry<String, PlayerData> e : players.entrySet()) {
			if (e.getValue().isReady()) {
				rq = new GenericRequest("CONFIRM_READY");
			} else {
				rq = new GenericRequest("CONFIRM_UNREADY");
			}
			try {
				playerConnections.get(e.getKey()).sendToClient(rq);
				updatePlayersInLobbyForClients(getJoinedPlayers());
			} catch (IOException CLIENT_WAS_NOT_READY) {
				CLIENT_WAS_NOT_READY.printStackTrace();
			}
		}
	}
	
	public void updatePlayersInLobbyForClients(ArrayList<PlayerJoinLeaveData> a) {
		GenericRequest rq = new GenericRequest("LOBBY_PLAYER_INFO");
		rq.setData(a);
		updateClients(rq);
	}
	
	public void updateClients(Object data) {
		for (Entry<String, PlayerData> e : players.entrySet()) {
			try {
				playerConnections.get(e.getKey()).sendToClient(data);
				server.logMessage("sent player data to clients");
			} catch (IOException CLIENT_DOESNT_LIKE_YOU) {
				CLIENT_DOESNT_LIKE_YOU.printStackTrace();
			}
		}
	}
	
	public GameLobbyData generateGameListing() {
		GameLobbyData tempInfo = new GameLobbyData(lobbyName, hostUsername, playerCount, playerCap, gameID);
		return tempInfo;
	}


}
