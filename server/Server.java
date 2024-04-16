package server;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import controller.ServerMenuScreenController;
import data.*;
import game_utilities.Block;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server_utilities.GameLobby;
import server_utilities.MapCreator;

public class Server extends AbstractServer {
	
	private JTextArea serverLog;
	private JLabel serverStatus;
	
	private String serverName;
	
	private ServerMenuScreenController serverMenuController;
	
	private ConcurrentHashMap<Integer, GameLobby> games = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, ScheduledFuture<?>> runningGames = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, ExecutorService> execGames = new ConcurrentHashMap<>();
	private int gameCount = 0;

	private Database serverDatabase = new Database();
	private static final MapCreator maps = new MapCreator();
	
	private ArrayList<String> connectedPlayers = new ArrayList<String>();
	private ConcurrentHashMap<String, ConnectionToClient> playerConnections = new ConcurrentHashMap<>();
	private int connectedPlayerCount = 0;
	
	private final ReentrantLock lock = new ReentrantLock();
	
	private Database database;
	
	public Server() {
		super(8300);
	}
	
	public ConcurrentHashMap<Integer, Block> loadMap(String m) {
		return maps.getMap(m);
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
	
	public void setDatabase(Database database) {
		serverDatabase = database;
	}

	public Database getDatabase() {
		return database;
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
			gameList.add(e.getValue().generateGameListing());
		}
		return gameList;
	}
	
	protected void serverStarted() {
		logMessage("[Server] Server '" + serverName + "' started on port '" + this.getPort());
		serverStatus.setText("RUNNING");
		serverStatus.setForeground(Color.GREEN);
	}
	
	protected void serverStopped() {
		connectedPlayerCount = 0;
		connectedPlayers.clear();
		// TODO save player data to database upon server stopping
		logMessage("[Server] Server Stopped");
		serverStatus.setText("STOPPED");
		serverStatus.setForeground(Color.RED);
	}
	
	public void listeningException(Throwable exception) {
		logMessage("[Server] Listening Exception Occurred: " + exception.getMessage());
		logMessage("[Server] Restart Required");
	}
	
	public void startGame(int id) {
		System.out.println("started game " + id);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		execGames.put(id, executor);
		executor.submit(games.get(id));
	}
	
	public void cancelGame(int id, boolean remove) {
		if (games.get(id).isStarted()) {
			games.get(id).stopGame();
			ScheduledFuture<?> f = runningGames.get(id);
			if (f != null) {
				f.cancel(true);
				runningGames.remove(id);
			}
		}
		if (remove) { 
			logMessage("[Info] Canceled Game " + id);
			games.remove(id);
		}
		serverMenuController.addGameListings(getAllGames());
	}
	
	public void stopServer() {
		for (Integer i : games.keySet()) {
			cancelGame(i, false);
		}
		games.clear();
		serverMenuController.bruh();
		try {
			sendToAllClients(new GenericRequest("FORCE_DISCONNECT"));
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logMessage(String msg) {
		lock.lock();
		serverLog.append(msg + "\n");
		serverLog.setCaretPosition(serverLog.getDocument().getLength());
		lock.unlock();
	}
	
	public void sendToPlayer(String usr, Object data) {
		try {
			playerConnections.get(usr).sendToClient(data);
		} catch (IOException PLAYER_WAS_OBLITERATED) {
			PLAYER_WAS_OBLITERATED.printStackTrace();
		}
	}
	
	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
			System.out.println("server: received generic request: " + action); // DEBUG remove later
			GenericRequest rq;
			switch (action) { 	// PROLIFIC user of SWITCH STATEMENTS 500 years eternal imprisonment
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
	        LoginData loginData = (LoginData) arg0;
	        String username = loginData.getUsername();
	        String password = loginData.getPassword();
	        
	        // Check if username is already connected
	        if (!connectedPlayers.contains(username)) {
	            if (serverDatabase.verifyAccount(username, password)) {
	                try {
	                    GenericRequest rq = new GenericRequest("LOGIN_CONFIRMED");
	                    rq.setData(username);
	                    arg1.sendToClient(rq);
	                    serverLog.append("[Client " + arg1.getId() + "] Successfully logged in as " + username + "\n");
	                    connectedPlayers.add(username);
	                    connectedPlayerCount += 1;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    serverLog.append("[Client " + arg1.getId() + "] Login Failed\n");
	                }
	            } else {
	                try {
	                    GenericRequest rq = new GenericRequest("INVALID_LOGIN");
	                    rq.setData("Incorrect username or password");
	                    arg1.sendToClient(rq);
	                    serverLog.append("[Client " + arg1.getId() + "] Failed login attempt\n");
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        } else {
	        	try {
                    GenericRequest rq = new GenericRequest("INVALID_LOGIN");
                    rq.setData("Incorrect username or password");
                    arg1.sendToClient(rq);
                    serverLog.append("[Client " + arg1.getId() + "] Failed login attempt\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
	        }
	        
		} else if (arg0 instanceof CreateAccountData) {
	        CreateAccountData createAccountData = (CreateAccountData) arg0;
	        String username = createAccountData.getUsername();
	        String password = createAccountData.getPassword();
	        
	        if (serverDatabase.createNewAccount(username, password)) {
	            try {
	                GenericRequest rq = new GenericRequest("ACCOUNT_CREATED");
	                rq.setData(username);
	                arg1.sendToClient(rq);
	                serverLog.append("[Client " + arg1.getId() + "] Created account '" + username + "' successfully \n");
	                connectedPlayers.add(username);
	                connectedPlayerCount += 1;
	            } catch (IOException e) {
	                e.printStackTrace();
	                serverLog.append("[Client " + arg1.getId() + "] Error creating account\n");
	            }
	        } else {
	            try {
	                GenericRequest rq = new GenericRequest("ACCOUNT_CREATION_FAILED");
	                rq.setData("Username already exists");
	                arg1.sendToClient(rq);
	                serverLog.append("[Client " + arg1.getId() + "] Failed to create acc)ount '" + username + "'\n");
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		} else if (arg0 instanceof PlayerStatistics) {
			// TODO query database for playerdata
			// may change this to a genericRequest that receives only username from the client
			// and sends PlayerData back
			
		} else if (arg0 instanceof GameLobbyData) {
			System.out.println("server: received gamelobbydata"); // DEBUG
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
			try {
				arg1.sendToClient(new GenericRequest(info.isReady() ? "CONFIRM_READY" : "CONFIRM_UNREADY"));
			} catch (IOException CLIENT_WAS_NOT_READY) {
				CLIENT_WAS_NOT_READY.printStackTrace();
			}
			
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
			
			if (!games.get(gid).isStarted()) {
				if (games.get(gid).playersReady()) {
					games.get(gid).startGame(info);
					startGame(gid);
				} else {
					try {
						GenericRequest nr = new GenericRequest("PLAYERS_NOT_READY");
						nr.setData("All players must ready before starting match");
						arg1.sendToClient(nr);
					} catch (IOException CLIENT_UNTO_DUST) {
						CLIENT_UNTO_DUST.printStackTrace();
					}
				}
			}

		} else if (arg0 instanceof PlayerAction) {
			PlayerAction a = (PlayerAction) arg0;
			int gid = a.getGameID();
			games.get(gid).addPlayerAction(a);
		}
	} 
}
