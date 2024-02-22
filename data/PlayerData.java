package data;

import java.io.Serializable;

import ocsf.server.ConnectionToClient;

public class PlayerData implements Serializable {
	
	private String username;
	private ConnectionToClient connection;
	
	private boolean ready = false;
	private boolean isHost = false;
	
	// TODO when we get the database ready we will add variables here to store
	public PlayerData() {}
	
	public String getUsername() {
		return username;
	}
	
	public ConnectionToClient getConnection() {
		return connection;
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

	public void setConnection(ConnectionToClient connection) {
		this.connection = connection;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
}
