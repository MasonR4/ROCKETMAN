package data;

import java.io.Serializable;
import java.util.ArrayList;

public class GameLobbyData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String hostUsername;

	private int playerCount;
	private int maxPlayers;
	private int gameID;

	private ArrayList<String> maps;

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

	public ArrayList<String> getMaps() {
		return maps;
	}

	public void setMaps(ArrayList<String> maps) {
		this.maps = maps;
	}
}
