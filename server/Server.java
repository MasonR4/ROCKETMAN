package server;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
	
	private LinkedHashMap<Integer, GameLobby> games = new LinkedHashMap<Integer, GameLobby>(); 
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
	
	protected void clientException(ConnectionToClient client, Exception CLIENT_PRONOUNCED_DEAD) {
		serverLog.append("[Client " + client.getId() + "] Client Exception Occurred " + "\n");
		CLIENT_PRONOUNCED_DEAD.printStackTrace();
	}
	
	public void setName(String name) {
		serverName = name;
	}
	
	public ArrayList<String> getConnectedPlayers() {
		return connectedPlayers;
	}
	
	public int getConnectedPlayerCount() {
		return connectedPlayerCount;
	}
	
	public ArrayList<GameLobbyData> getGames() {
		ArrayList<GameLobbyData> gameList = new ArrayList<GameLobbyData>();
//		for (Entry<Integer, GameLobby> e : games.entrySet()) {
//			GameLobby g = e.getValue();
//			if (g.isEmpty()) {
//				games.remove(e.getKey());
//			}
//		}
		for (Entry<Integer, GameLobby> e : games.entrySet()) {
			GameLobby g = e.getValue();
			if (!g.isStarted()) {
				gameList.add(e.getValue().generateGameListing());
			}
		}
		return gameList;
	}
	
	protected void serverStarted() {
		serverLog.append("Server '" + serverName + "' started on port '" + this.getPort() + "'\n");
		serverStatus.setText("RUNNING");
		serverStatus.setForeground(Color.GREEN);
	}
	
	protected void serverStopped() {
		for (Entry<Integer, GameLobby> e :  games.entrySet()) {
			cancelGame(e.getKey());
		}
		// TODO save player data to database upon server stopping
		serverLog.append("Server Stopped\n");
		serverStatus.setText("STOPPED");
		serverStatus.setForeground(Color.RED);
	}
	
	public void listeningException(Throwable exception) {
		serverLog.append("Listening Exception Occurred: " + exception.getMessage() + "\n");
		serverLog.append("Restart Required\n");
	}
	
	public void cancelGame(int id) {
		//GameLobby g = games.get(id);
		serverLog.append("[Info] Canceled Game " + id + "\n");
		games.remove(id);
		serverMenuController.addGameListings(getGames());
	}
	
	public void logMessage(String msg) {
		serverLog.append("[Server] " + msg + "\n");
	}
	
	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
			GenericRequest rq;
			switch (action) { 			// lol PROLIFIC user of SWITCH STATEMENTS 500 years eternal imprisonment
			case "REQUEST_GAMES_INFO":
				serverLog.append("[Client " + arg1.getId() + "] Requested games info\n");
				rq = new GenericRequest("GAMES_INFO");
				ArrayList<GameLobbyData> gameList = getGames();
				serverMenuController.addGameListings(gameList);
				rq.setData(gameList);
				try {
					arg1.sendToClient(rq);
				} catch (IOException CLIENT_IS_MAYBE_DEAD) {
					CLIENT_IS_MAYBE_DEAD.printStackTrace();
					serverLog.append("[Server] Could not send game info to client\n");
				}
				break;
			case "REQUEST_TO_JOIN_GAME":
				String rqData = (String) ((GenericRequest) arg0).getData();
				String[] rqSplitData = rqData.split(":"); // LOL
				String usr = rqSplitData[0];
				int gameID = Integer.parseInt(rqSplitData[1]);
				if (games.containsKey(gameID)) {
					if (!games.get(gameID).isFull()) {
						games.get(gameID).addPlayer(arg1, usr);
						try {
							arg1.sendToClient(games.get(gameID).generateGameListing());
							serverMenuController.addGameListings(getGames());
							serverLog.append("[Client " + arg1.getId() + "] Joined game " + gameID + " as " + usr + "\n");
							// TODO update client on other players within the lobby
							
							} catch (IOException CLIENT_VITALS_CANNOT_BE_CONFIRMED) {
								CLIENT_VITALS_CANNOT_BE_CONFIRMED.printStackTrace();
						}
					} else {
						try {
							rq = new GenericRequest("GAME_FULL");
							arg1.sendToClient(rq);
							serverLog.append("[Client " + arg1.getId() + "] Failed to join game " + gameID + ": game is full\n");
						} catch (IOException CLIENT_DID_NOT_CARE) {
							CLIENT_DID_NOT_CARE.printStackTrace();
						}
					}
				} else {
					try {
						rq = new GenericRequest("GAME_NOT_FOUND");
						arg1.sendToClient(rq);
						serverLog.append("[Client " + arg1.getId() + "] Failed to join game " + gameID + ": game no longer exists\n");
					} catch (IOException CLIENT_DIED) {
						CLIENT_DIED.printStackTrace();
					}
				}
				break;
				
			case "CLIENT_DISCONNECTING":
				String username = (String) ((GenericRequest) arg0).getData();
				if (username != "default") {
					// TODO save player data to database
					for (Entry<Integer, GameLobby> e : games.entrySet()) {
						GameLobby game = e.getValue();
						if (game.getPlayerUsernames().contains(username)) {
							game.removePlayer(arg1, username);
							serverLog.append("[Client " + arg1.getId() + "] " + username + " left game " + e.getValue().getGameID() + ": " + e.getValue().getlobbyName() + "\n");
						}
					}
					try {
						GenericRequest rq2 = new GenericRequest("CONFIRM_DISCONNECT_AND_EXIT");
						//arg1.sendToClient(rq2);
						arg1.close();
						connectedPlayers.remove(username);
						connectedPlayerCount -= 1;
						serverLog.append("[Client " + arg1.getId() + "] Logged out as " + username + "\n");
						serverMenuController.addGameListings(getGames());
					} catch (IOException CLIENT_ALREADY_GONE) {
						CLIENT_ALREADY_GONE.printStackTrace();
					}
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
			// TODO query database for playerdata
			// may change this to a genericRequest that receives only username from the client
			// and sends PlayerData back
			
		} else if (arg0 instanceof GameLobbyData) {
			GameLobbyData info = (GameLobbyData) arg0;
			try {
				GameLobby newGame = new GameLobby(info.getName(), info.getHostName(), info.getMaxPlayers(), gameCount, this);
				newGame.addPlayer(arg1, info.getHostName());
				arg1.sendToClient(newGame.generateGameListing());
				gameCount += 1;
				games.put(newGame.getGameID(), newGame);
				ArrayList<GameLobbyData> gameList = getGames();
				serverLog.append("[Client " + arg1.getId() + "] Created Game ID: " + newGame.getGameID() + ", Name: " + info.getName() +", Host: " + info.getHostName() + ", Max Players: " + info.getMaxPlayers() + "\n");
				serverMenuController.addGameListings(gameList);
			} catch (IOException CLIENT_WENT_TO_SLEEP) {
				CLIENT_WENT_TO_SLEEP.printStackTrace();
			}
		} else if (arg0 instanceof PlayerReadyData) {
			PlayerReadyData info = (PlayerReadyData) arg0;
			int gameID = info.getGameID();
			GameLobby game = games.get(gameID);
			game.readyPlayer(info);
		} 
	}
}
