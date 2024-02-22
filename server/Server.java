package server;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import data.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server_utilities.GameLobby;

public class Server extends AbstractServer {
	
	private JTextArea serverLog;
	private JLabel serverStatus;
	
	private String serverName;
	
	private ArrayList<GameLobby> games = new ArrayList<GameLobby>();
	
	public Server() {
		super(8300);
	}
	
	public void setLog(JTextArea log) {
		serverLog = log;
	}
	
	public void setStatusLabel(JLabel label) {
		serverStatus = label;
	}
	
	protected void clientConnected(ConnectionToClient client) {
		serverLog.append("Client " + client.getId() + " connected\n");
	}
	
	protected void clientDisconnected(ConnectionToClient client) {
		serverLog.append("Client " + client.getId() + " disconnected\n");
	}
	
	protected void clientException(ConnectionToClient client) {
		
	}
	
	public void setName(String name) {
		serverName = name;
	}
	
	protected void serverStarted() {
		serverLog.append("Server '" + serverName + "' started on port '" + this.getPort() + "'\n");
		serverStatus.setText("RUNNING");
		serverStatus.setForeground(Color.GREEN);
	}
	
	protected void serverStopped() {
		serverLog.append("Server Stopped\n");
		serverStatus.setText("STOPPED");
		serverStatus.setForeground(Color.RED);
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
			// TODO load player data server side
			
		} else if (arg0 instanceof NewGameData) {
			NewGameData info = (NewGameData) arg0;
			GameLobby newGame = new GameLobby(info.getName(), info.getMaxPlayers());
			games.add(newGame);
			serverLog.append("New Game Created: " + info.getName() + ", Max Players: " + info.getMaxPlayers() + ", Created By Client: " + arg1.getId() + "\n");
		} else if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
			// lol PROLIFIC user of SWITCH STATEMENTS 500 years eternal imprisonment
			switch (action) {
			case "REQUEST_GAMES_INFO":
				serverLog.append("Client " + arg1.getId() + " requested games info\n");
				GenericRequest temp = new GenericRequest("GAMES_INFO");
				ArrayList<NewGameData> gameList = new ArrayList<NewGameData>();
				for (GameLobby g : games) {
					gameList.add(g.generateGameListing());
				}
				temp.setData(gameList);
				try {
					arg1.sendToClient(temp);
				} catch (IOException CLIENT_IS_MAYBE_DEAD) {
					CLIENT_IS_MAYBE_DEAD.printStackTrace();
					serverLog.append("Could not send game info to client");
				}
				break;
				
			}
		}
	}
}
