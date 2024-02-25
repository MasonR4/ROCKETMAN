package data;

import java.io.Serializable;

public class PlayerActionData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int gameID;
	private String username;
	
	// TODO add more parameters (player position, mouse position etc.)
	private String type;
	private String action;
	
	public PlayerActionData(int g, String s, String t, String a) {
		setGameID(g);
		setUsername(s);
		setType(t);
		setAction(a);
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
