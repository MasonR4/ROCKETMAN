package data;

import java.io.Serializable;

import game_utilities.PlayerCollision;

public class PlayerActionData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int gameID;
	private String username;
	
	// TODO add more parameters (player position, mouse position etc.)
	private String type;
	private String action;
	
	private PlayerCollision collision;
	
	public PlayerActionData(int g, String s, String t, String a) {
		gameID = g;
		username = s;
		type = t;
		action = a;
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

	public PlayerCollision getCollision() {
		return collision;
	}

	public void setCollision(PlayerCollision collision) {
		this.collision = collision;
	}
}
