package server;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import data.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {
	
	private JTextArea serverLog;
	private JLabel serverStatus;
	
	private String serverName;
	
	private int connectedClients = 0;
	
	public Server() {
		super(8300);
		// TODO Auto-generated constructor stub
	}
	
	public void clientConnected(ConnectionToClient client) {
		// TODO not really sure what to send back tbh maybe just connection successful
		connectedClients += 1;
	}
	
	public void setName(String name) {
		serverName = name;
	}
	
	public void serverStarted() {
		serverLog.append("Server '" + serverName + "' started on port '" + this.getPort() + "'\n");
		serverStatus.setText("RUNNING");
		serverStatus.setForeground(Color.GREEN);
	}
	
	public void serverStopped() {
		serverLog.append("Server Stopped");
		serverStatus.setText("STOPPED");
		serverStatus.setForeground(Color.RED);
	}
	
	public void setLog(JTextArea log) {
		serverLog = log;
	}
	
	public void setStatusLabel(JLabel label) {
		serverStatus = label;
	}
	
	public void listeningException(Throwable exception) {
		
		serverLog.append("Listening Exception Occurred: " + exception.getMessage());
		serverLog.append("Restart Required");
	}
	
	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		
		if (arg0 instanceof LoginData) {
			// TODO login here
			
			
		} else if (arg0 instanceof CreateAccountData) {
			// TODO create account here
		} else if (arg0 instanceof PlayerData) {
			// TODO load player data server side, this may be redundant with clientConnected();
		}
		
	}

}
