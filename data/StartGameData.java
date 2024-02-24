package data;

import java.io.Serializable;

public class StartGameData implements Serializable {
	
	// TODO all values we want to be configured from the lobby screen go here
	
	private int gameID;
	
	private int playerLives;
	private String map;
	
	// player speed?
	// reload speed?
	// time limit?
	
	public StartGameData(int gid) {
		gameID = gid;
		playerLives = 4;
		map = "default";
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public int getPlayerLives() {
		return playerLives;
	}
	
	public String getMap() {
		return map;
	}
	
}
