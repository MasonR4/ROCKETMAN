package data;

import java.io.Serializable;

public class StartGameData implements Serializable {
	
	private static final long serialVersionUID = -7049710051366582248L;

	private int gameID;
	
	private int playerLives;
	private String map;
	
	// player speed?
	// reload speed?
	// time limit?
	
	public StartGameData(int gid, String m, int lives) {
		gameID = gid;
		playerLives = lives;
		map = m;
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public int getPlayerLives() {
		return playerLives;
	}
	
	public void setMap(String s) {
		map = s;
	}
	
	public String getMap() {
		return map;
	}
}
