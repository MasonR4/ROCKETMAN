package data;

import java.io.Serializable;

public class PlayerJoinData implements Serializable {
	private String username;
	private boolean isHost;
	
	private boolean ready;
	
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
	
	public boolean isReady() {
		return ready;
	}
	
	public void setReady(boolean r) {
		ready = r;
	}
	
	public void setHost(boolean h) {
		isHost = h;
	}
}
