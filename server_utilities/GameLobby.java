package server_utilities;

import java.util.ArrayList;
import data.GameLobbyData;
import data.PlayerData;
import ocsf.server.ConnectionToClient;
import server.Server;

public class GameLobby {
	
	private Server server;
	
	private String lobbyName;
	private String hostUsername;
	
	private int playerCount = 0;
	private int playerCap;
	private int gameID;
	
	private ArrayList<ConnectionToClient> playerConnections = new ArrayList<ConnectionToClient>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	
	// TODO here is where we store stuff like player objects, the grid
	// other game relevant stuff
	
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
	
	public boolean isFull() {
		return playerCount == playerCap;
	}
	
	public boolean isEmpty() {
		return playerCount == 0;
	}
	
	public void removePlayer(ConnectionToClient c, String usr) {
		playerConnections.remove(c);
		playerNames.remove(usr);
		playerCount -= 1;
		
		if (playerCount == 0) {
			// TODO cancel game
		} else if (usr == hostUsername && playerNames.isEmpty() == false) {
			hostUsername = playerNames.get(0);
		}
	}
	
	public void addPlayer(ConnectionToClient c, String usr) {
		playerConnections.add(c);
		playerNames.add(usr);
		playerCount += 1;
	}
	
	// TODO public void updateClients() ?
	
	public GameLobbyData generateGameListing() {
		GameLobbyData tempInfo = new GameLobbyData(lobbyName, hostUsername, playerCount, playerCap, gameID);
		return tempInfo;
	}


}
