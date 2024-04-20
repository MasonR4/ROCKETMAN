package data;

import java.io.Serializable;

public class PlayerReadyData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private int gameID;

	private boolean ready;

	public PlayerReadyData(String s, int g, boolean r) {
		username = s;
		gameID = g;
		ready = r;
	}

	public String getUsername() {
		return username;
	}

	public int getGameID() {
		return gameID;
	}

	public boolean isReady() {
		return ready;
	}
}
