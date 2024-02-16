package server;

import ocsf.client.AbstractClient;

public class Client extends AbstractClient {
	
	private String username;
	private int userID;
	
	private boolean isHost = false;
	
	public Client() {
		super("localhost", 8300);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateUser(String usr, int id) {
		username = usr;
		userID = id;
	}

	public String getUserName() {
		return username;
	}
	
	protected void connectionClosed() {
		System.out.println("connection terminated");
	}
 }
