package data;

import java.io.Serializable;

import server.Client;

public class NewGameData implements Serializable {
	private String name;
	private int maxPlayers;
	
	private String hostUsername;
	private Client host;
	
	public NewGameData(String n, int mp) {
		name = n;
		maxPlayers = mp;	
		//host = c;
		//hostUsername = host.getUserName();
	}

	public String getName() {
		return name;
	}
	
	public String getHostName() {
		return hostUsername;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public Client getHost() {
		return host;
	}
	
}
