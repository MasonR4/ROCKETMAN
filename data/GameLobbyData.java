package data;

import java.io.Serializable;

import server.Client;

public class GameLobbyData implements Serializable {
	
	private String name;
	private String hostUsername;
	
	private int playerCount;
	private int maxPlayers;
	private int gameID;

	public GameLobbyData(String n, String hn, int p, int mp, int gid) {
		name = n;
		maxPlayers = mp;	
		hostUsername = hn;
		gameID = gid;
		playerCount = p;
	}
	
	public void setHostUsername(String hn) {
		hostUsername = hn;
	}
	
	public String getName() {
		return name;
	}
	
	public String getHostName() {
		return hostUsername;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
	
	public int getGameID() {
		return gameID;
	}	
}
