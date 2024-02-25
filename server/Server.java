package server;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
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
	private LinkedHashMap<Integer, Future<?>> runningGames = new LinkedHashMap<Integer, Future<?>>();
	private int gameCount = 0;
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	
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
			logMessage("[Client " + client.getId() + "] Connected");
		} catch (IOException CLIENT_IS_MIA) {
			CLIENT_IS_MIA.printStackTrace();
		}
	}
	
	protected void clientDisconnected(ConnectionToClient client) {
		logMessage("[Client " + client.getId() + "] disconnected");
	}
	
	protected void clientException(ConnectionToClient client, Exception CLIENT_PRONOUNCED_DEAD) {
		logMessage("[Client " + client.getId() + "] Client Exception Occurred");
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
		for (Entry<Integer, GameLobby> e : games.entrySet()) {
			GameLobby g = e.getValue();
			if (!g.isStarted()) {
				gameList.add(e.getValue().generateGameListing());
			}
		}
		return gameList;
	}
	
	public ArrayList<GameLobbyData> getAllGames() {
		ArrayList<GameLobbyData> gameList = new ArrayList<GameLobbyData>();
		for (Entry<Integer, GameLobby> e : games.entrySet()) {
			GameLobby g = e.getValue();
			gameList.add(e.getValue().generateGameListing());
		}
		return gameList;
	}
	
	protected void serverStarted() {
		logMessage("Server '" + serverName + "' started on port '" + this.getPort());
		serverStatus.setText("RUNNING");
		serverStatus.setForeground(Color.GREEN);
	}
	
	protected void serverStopped() {
		for (Entry<Integer, GameLobby> e :  games.entrySet()) {
			cancelGame(e.getKey());
		}
		connectedPlayerCount = 0;
		connectedPlayers.clear();
		// TODO save player data to database upon server stopping
		logMessage("Server Stopped");
		serverStatus.setText("STOPPED");
		serverStatus.setForeground(Color.RED);
	}
	
	public void listeningException(Throwable exception) {
		logMessage("Listening Exception Occurred: " + exception.getMessage());
		logMessage("Restart Required");
	}
	
	public void cancelGame(int id) {
		logMessage("[Info] Canceled Game " + id);
		if (games.get(id).isStarted()) {
			runningGames.get(id).cancel(true);
			runningGames.remove(id);
		}
		games.remove(id);
		serverMenuController.addGameListings(getAllGames());
	}
	
	public void logMessage(String msg) {
		serverLog.append(msg + "\n");
		serverLog.setCaretPosition(serverLog.getDocument().getLength());
	}
	
	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
			GenericRequest rq;
			switch (action) { 			// PROLIFIC user of SWITCH STATEMENTS 500 years eternal imprisonment
			case "REQUEST_GAMES_INFO":
				logMessage("[Client " + arg1.getId() + "] Requested games info");
				rq = new GenericRequest("GAMES_INFO");
				ArrayList<GameLobbyData> gameList = getGames();
				rq.setData(gameList);
				try {
					arg1.sendToClient(rq);
				} catch (IOException CLIENT_IS_MAYBE_DEAD) {
					CLIENT_IS_MAYBE_DEAD.printStackTrace();
					logMessage("[Server] Could not send game info to client");
				}
				break;
				
			case "CLIENT_DISCONNECTING":
				String username = (String) ((GenericRequest) arg0).getData();
				try {
					GenericRequest rq2 = new GenericRequest("CONFIRM_DISCONNECT_AND_EXIT");
					arg1.sendToClient(rq2);
					arg1.close();
					connectedPlayers.remove(username);
					connectedPlayerCount -= 1;
					logMessage("[Client " + arg1.getId() + "] Logged out as " + username);
					SwingUtilities.invokeLater(() -> serverMenuController.addGameListings(getAllGames()));
				} catch (IOException CLIENT_ALREADY_GONE) {
					CLIENT_ALREADY_GONE.printStackTrace();
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
					logMessage("[Client " + arg1.getId() + "] Sucessfully logged in as " + username);
					connectedPlayers.add(username);
					connectedPlayerCount += 1;
				} catch (IOException CLIENT_LIKELY_DEPARTED) {
					CLIENT_LIKELY_DEPARTED.printStackTrace();
					logMessage("[Client " + arg1.getId() + "] Login Failed");
				}
			} else {
				GenericRequest rq = new GenericRequest("INVALID_LOGIN");
				rq.setData("User is logged in elsewhere"); // cant show this to client yet
				try {
					arg1.sendToClient(rq);
					logMessage("[Client " + arg1.getId() + "] Denied duplicate login as " + username);
				} catch (IOException CLIENT_MAY_BE_AN_IMPOSTOR) {
					CLIENT_MAY_BE_AN_IMPOSTOR.printStackTrace();
					logMessage("[Client " + arg1.getId() + "] Denied duplicate login as " + username + " but failed to notify client (HOW)");
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
				logMessage("[Client " + arg1.getId() + "] Created account '" + username + "' and logged in successfully");
				connectedPlayers.add(username);
				connectedPlayerCount += 1;
			} catch (IOException CLIENT_POSSIBLY_DECEASED) {
				CLIENT_POSSIBLY_DECEASED.printStackTrace();
				logMessage("[Client " + arg1.getId() + "] Error creating account");
			}
			
		} else if (arg0 instanceof PlayerData) {
			// TODO query database for playerdata
			// may change this to a genericRequest that receives only username from the client
			// and sends PlayerData back
			
		} else if (arg0 instanceof GameLobbyData) {
			GameLobbyData info = (GameLobbyData) arg0;
			try {
				GameLobby newGame = new GameLobby(info.getName(), info.getHostName(), info.getMaxPlayers(), gameCount, this);
				PlayerJoinLeaveData firstPlayer = new PlayerJoinLeaveData(info.getHostName());
				newGame.addPlayer(arg1, firstPlayer);
				arg1.sendToClient(newGame.generateGameListing());
				gameCount += 1;
				games.put(newGame.getGameID(), newGame);
				logMessage("[Client " + arg1.getId() + "] Created Game ID: " + newGame.getGameID() + ", Name: " + info.getName() +", Host: " + info.getHostName() + ", Max Players: " + info.getMaxPlayers());
				serverMenuController.addGameListings(getAllGames());
			} catch (IOException CLIENT_WENT_TO_SLEEP) {
				CLIENT_WENT_TO_SLEEP.printStackTrace();
			}
			
		} else if (arg0 instanceof PlayerReadyData) {
			PlayerReadyData info = (PlayerReadyData) arg0;
			int gameID = info.getGameID();
			GameLobby game = games.get(gameID);
			game.readyPlayer(info);
			
		} else if (arg0 instanceof PlayerJoinLeaveData) {
			PlayerJoinLeaveData info = (PlayerJoinLeaveData) arg0;
			int gameID = info.getGameID();
			String username = info.getUsername();
			if (info.isJoining()) {
				if (games.containsKey(gameID)) {
					if (!games.get(gameID).isFull()) {
						games.get(gameID).addPlayer(arg1, info);
						try {
							arg1.sendToClient(games.get(gameID).generateGameListing());
							serverMenuController.addGameListings(getAllGames());
							logMessage("[Client " + arg1.getId() + "] Joined game " + gameID + " as " + username);
							} catch (IOException CLIENT_VITALS_CANNOT_BE_CONFIRMED) {
								CLIENT_VITALS_CANNOT_BE_CONFIRMED.printStackTrace();
						}
					} else {
						try {
							arg1.sendToClient(new GenericRequest("GAME_FULL"));
							logMessage("[Client " + arg1.getId() + "] Failed to join game " + gameID + ": game is full");
						} catch (IOException CLIENT_DID_NOT_CARE) {
							CLIENT_DID_NOT_CARE.printStackTrace();
						}
					}
				} else {
					try {
						arg1.sendToClient(new GenericRequest("GAME_NOT_FOUND"));
						logMessage("[Client " + arg1.getId() + "] Failed to join game " + gameID + ": game no longer exists");
					} catch (IOException CLIENT_DIED) {
						CLIENT_DIED.printStackTrace();
					}
				}
			} else if (!info.isJoining()) {
				logMessage("[Client " + arg1.getId() + "] " + username + " left game " + gameID + ": " + games.get(gameID).getlobbyName());
				games.get(gameID).removePlayer(arg1, info);
				try {
					arg1.sendToClient(new GenericRequest("CONFIRM_LEAVE_GAME"));
				} catch (IOException CLIENT_NONRESPONSIVE) {
					CLIENT_NONRESPONSIVE.printStackTrace();
				}
			}
		} else if (arg0 instanceof StartGameData) {
			StartGameData info = (StartGameData) arg0;
			int gid = info.getGameID();
			games.get(gid).startGame(info);
			runningGames.put(gid, executor.submit(games.get(gid)::run));
		}
	}
}
