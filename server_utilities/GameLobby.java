package server_utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import data.GameLobbyData;
import data.GenericRequest;
import data.PlayerData;
import data.PlayerJoinData;
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
	
	private LinkedHashMap<String, ConnectionToClient> playerConnections = new LinkedHashMap<String, ConnectionToClient>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	
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
		return playerNames;
	}
	
	public ArrayList<PlayerJoinData> getJoinedPlayers() {
		ArrayList<PlayerJoinData> players = new ArrayList<PlayerJoinData>();
		for (String s : playerNames) {
			PlayerJoinData player = new PlayerJoinData(s, (hostUsername == s));
			players.add(player);
		}
		return players;
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
	
	public void removePlayer(ConnectionToClient c, String usr) {
		playerConnections.remove(usr);
		playerNames.remove(usr);
		playerCount -= 1;
		
		if (playerCount == 0) {
			server.cancelGame(gameID);
		} else if (usr == hostUsername && playerNames.isEmpty() == false) {
			hostUsername = playerNames.get(0);
			updateClients(getJoinedPlayers());
		}
	}
	
	public void addPlayer(ConnectionToClient c, String usr) {
		playerConnections.put(usr, c);
		playerNames.add(usr);
		if (playerCount == 0) {
			hostUsername = usr;
		}
		playerCount += 1;
		updateClients(getJoinedPlayers());
	}
	
	public void updateClients(Object data) {
		GenericRequest rq = new GenericRequest("LOBBY_PLAYER_INFO");
		rq.setData(data);
		for (Entry<String, ConnectionToClient> e : playerConnections.entrySet()) {
			try {
				e.getValue().sendToClient(rq);
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
