package server;

import ocsf.client.AbstractClient;

public class Client extends AbstractClient {
	
	private String username;
	private int userID;
	
	public Client() {
		super("localhost", 8300);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void updateUser(String usr, int id) {
		username = usr;
		userID = id;
	}

	private String getUserName() {
		return username;
	}
	
	private int getUserID() {
		return userID;
	}
 }
