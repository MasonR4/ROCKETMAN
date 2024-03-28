package data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import ocsf.server.ConnectionToClient;

public class PlayerData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private boolean ready = false;
	private boolean isHost = false;
	
	// TODO when we get the database ready we will add variables here to store
	private LinkedHashMap<String, Integer> statistics = new LinkedHashMap<String, Integer>();
	
	public PlayerData() {}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public boolean isHost() {
		return isHost;
	}
	
	public void setHost() {
		isHost = true;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
}
