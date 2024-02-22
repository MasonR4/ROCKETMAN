package data;

import java.io.Serializable;

import server.Client;

public class NewGameData implements Serializable {
	
	private String name;
	private String hostUsername;
	
	private int maxPlayers;
	private int gameID;

	public NewGameData(String n, String hn, int mp, int gid) {
		name = n;
		maxPlayers = mp;	
		hostUsername = hn;
		gameID = gid;
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
	
	public int getGameID() {
		return gameID;
	}	
}
