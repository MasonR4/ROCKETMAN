package data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MatchSettings implements Serializable {
private int gameID;

	private int playerLives;
	private String map;


	public MatchSettings(int gid, String m, int lives) {
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
