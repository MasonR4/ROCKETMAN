package data;

import java.io.Serializable;

public class PlayerJoinLeaveData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	
	private int gameID;
	
	private boolean isJoining;
	private boolean isHost;
	private boolean ready;
	
	
	public PlayerJoinLeaveData(String s) {
		username = s;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isHost() {
		return isHost;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void setReady(boolean r) {
		ready = r;
	}
	
	public void setHost(boolean h) {
		isHost = h;
	}

	public boolean isJoining() {
		return isJoining;
	}

	public void setJoining(boolean isJoining) {
		this.isJoining = isJoining;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
