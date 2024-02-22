package server;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.ServerMenuScreenController;
import data.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server_utilities.GameLobby;

public class Server extends AbstractServer {
	
	private JTextArea serverLog;
	private JLabel serverStatus;
	
	private String serverName;
	
	private ServerMenuScreenController serverMenuController;
	
	private ArrayList<GameLobby> games = new ArrayList<GameLobby>(); 
	private int gameCount = 0;
	
	// potentially not using these (?)
	private ArrayList<String> connectedPlayers = new ArrayList<String>();
	private int connectedPlayerCount = 0;
	
	public Server() {
		super(8300);
	}
	
	public void setLog(JTextArea log) {
		serverLog = log;
	}
	
	public void setStatusLabel(JLabel label) {
		serverStatus = label;
	}
	
	public void setServerMenuController(ServerMenuScreenController c) {
		serverMenuController = c;
	}
	
	protected void clientConnected(ConnectionToClient client) {
		try {
			GenericRequest rq = new GenericRequest("SERVER_INFO");
			rq.setData(serverName);
			client.sendToClient(rq);
			serverLog.append("[Client " + client.getId() + "] Connected\n");
		} catch (IOException CLIENT_IS_MIA) {
			CLIENT_IS_MIA.printStackTrace();
		}
	}
	
	protected void clientDisconnected(ConnectionToClient client) {
		serverLog.append("[Client " + client.getId() + "] disconnected\n");
	}
	
	protected void clientException(ConnectionToClient client) {
		
	}
	
	public void setName(String name) {
		serverName = name;
	}
	
	public ArrayList<String> getConnectedPlayers() {
		return connectedPlayers;
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
		serverLog.append("Listening Exception Occurred: " + exception.getMessage() + "\n");
		serverLog.append("Restart Required\n");
	}
	
	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		
		if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
			// lol PROLIFIC user of SWITCH STATEMENTS 500 years eternal imprisonment
			switch (action) {
			case "REQUEST_GAMES_INFO":
				serverLog.append("[Client " + arg1.getId() + "] Requested games info\n");
				GenericRequest rq = new GenericRequest("GAMES_INFO");
				ArrayList<GameLobbyData> gameList = new ArrayList<GameLobbyData>();
				for (GameLobby g : games) {
					gameList.add(g.generateGameListing());
				}
				serverMenuController.addGameListings(gameList);
				rq.setData(gameList);
				try {
					arg1.sendToClient(rq);
				} catch (IOException CLIENT_IS_MAYBE_DEAD) {
					CLIENT_IS_MAYBE_DEAD.printStackTrace();
					serverLog.append("Could not send game info to client\n");
				}
				break;
			case "REQUEST_JOIN_GAME":
				
				break;
				
			case "PLAYER_DISCONNECTING":
				String username = (String) ((GenericRequest) arg0).getData();
				if (username != "default") {
					// TODO save player data to database
					connectedPlayers.remove(username);
					connectedPlayerCount -= 1;
					serverLog.append("[Client " + arg1.getId() + "] Logged out as " + username + "\n");
					clientDisconnected(arg1);
				}
				break;
			}
			
			
		} else if (arg0 instanceof LoginData) {
			String username = ((LoginData) arg0).getUsername();
			String password = ((LoginData) arg0).getPassword();
			
			// TODO database login check here (currently you just press the button and it lets you in)
			// if (validLogin) login!
			if (!connectedPlayers.contains(username)) {
				try {
					GenericRequest rq = new GenericRequest("LOGIN_CONFIRMED");
					rq.setData(username);
					arg1.sendToClient(rq);
					serverLog.append("[Client " + arg1.getId() + "] Sucessfully logged in as " + username + "\n");
					connectedPlayers.add(username);
					connectedPlayerCount += 1;
				} catch (IOException CLIENT_LIKELY_DEPARTED) {
					CLIENT_LIKELY_DEPARTED.printStackTrace();
					serverLog.append("[Client " + arg1.getId() + "] Login Failed\n");
				}
			} else {
				GenericRequest rq = new GenericRequest("INVALID_LOGIN");
				rq.setData("User is logged in elsewhere"); // cant show this to client yet
				try {
					arg1.sendToClient(rq);
					serverLog.append("[Client " + arg1.getId() + "] Denied duplicate login as " + username + "\n");
				} catch (IOException CLIENT_MAY_BE_AN_IMPOSTOR) {
					CLIENT_MAY_BE_AN_IMPOSTOR.printStackTrace();
					serverLog.append("[Client " + arg1.getId() + "] Denied duplicate login as " + username + " but failed to notify client (HOW)\n");
				}
			}
			// else sendToClient(INVALID_LOGIN INFO) -- wrap in generic request
			// query dataBase and whatever
			
			
			
		} else if (arg0 instanceof CreateAccountData) {
			String username = ((CreateAccountData) arg0).getUsername();
			String password = ((CreateAccountData) arg0).getPassword();
			
			// TODO here is where it should submit the new account to the database
			// and if successful the code below runs
			
			try {
				GenericRequest rq = new GenericRequest("ACCOUNT_CREATED");
				rq.setData((String) username);
				arg1.sendToClient(rq);
				serverLog.append("[Client " + arg1.getId() + "] Created account '" + username + "' and logged in successfully \n");
				connectedPlayers.add(username);
				connectedPlayerCount += 1;
			} catch (IOException CLIENT_POSSIBLY_DECEASED) {
				CLIENT_POSSIBLY_DECEASED.printStackTrace();
				serverLog.append("[Client " + arg1.getId() + "] Error creating account\n");
			}
			
		} else if (arg0 instanceof PlayerData) {
			// TODO load player data server side
			
		} else if (arg0 instanceof GameLobbyData) {
			GameLobbyData info = (GameLobbyData) arg0;
			GameLobby newGame = new GameLobby(info.getName(), info.getHostName(), info.getMaxPlayers(), gameCount, this);
			gameCount += 1;
			ArrayList<GameLobbyData> gameList = new ArrayList<GameLobbyData>();
			games.add(newGame);
			serverLog.append("[Client " + arg1.getId() + "] Created Game ID: " + newGame.getGameID() + ", Name: " + info.getName() +", Host: " + info.getHostName() + ", Max Players: " + info.getMaxPlayers() + "\n");
			for (GameLobby g : games) {
				gameList.add(g.generateGameListing());
			}
			serverMenuController.addGameListings(gameList);
			
			// TODO connect client to the game they created automatically
			newGame.addPlayer(arg1, info.getHostName());
		} 
	}
}
