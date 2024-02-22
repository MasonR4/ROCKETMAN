package data;

import java.io.Serializable;

public class PlayerJoinData implements Serializable {
	private String username;
	private boolean isHost;
	
	public PlayerJoinData(String s, boolean h) {
		username = s;
		isHost = h;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isHost() {
		return isHost;
	}
	
}
