package client;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import controller.CreateAccountScreenController;
import controller.FindGameScreenController;
import controller.GameOverScreenController;
import controller.GameScreenController;
import controller.LobbyScreenController;
import controller.LoginScreenController;
import controller.ProfileScreenController;
import controller.ServerConnectionScreenController;
import controller.SplashScreenController;
import data.EndGameData;
import data.GameEvent;
import data.GameLobbyData;
import data.GenericRequest;
import data.MatchSettings;
import data.PlayerAction;
import data.PlayerJoinLeaveData;
import data.PlayerStatistics;
import game_utilities.Block;
import game_utilities.Player;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	private String username = "default";
	private String serverName;

	private int gameID = -1;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	
	// controllers for each menu
	// possible that we won't need all of them in the client class
	private CreateAccountScreenController createAccountController;
	private FindGameScreenController findGameController;
	private LoginScreenController loginController;
	private LobbyScreenController lobbyController;
	private GameScreenController gameController;
	private GameOverScreenController gameOverController;
	private ServerConnectionScreenController serverConnectionController;
	private SplashScreenController splashController;
	private ProfileScreenController profileController;

	public Client() {
		super("localhost", 8300);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object arg0) {
		if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
			System.out.println("recieved from server: " + action);
			switch(action) {
			case "GAMES_INFO":
				findGameController.addGameListings((ArrayList<GameLobbyData>) ((GenericRequest) arg0).getData());
				break;

			case "SERVER_INFO":
				serverName = (String) ((GenericRequest) arg0).getData();
				break;

			case "ACCOUNT_CREATED":
				username = (String) ((GenericRequest) arg0).getData();
				createAccountController.actionPerformed(new ActionEvent(0, 0, action));
				findGameController.setScreenInfoLabels();
				break;

			case "ACCOUNT_CREATION_FAILED":
				createAccountController.setError((String) ((GenericRequest) arg0).getData());
				break;

			case "LOGIN_CONFIRMED":
				username = (String) ((GenericRequest) arg0).getData();
				loginController.actionPerformed(new ActionEvent(0, 0, action));
				findGameController.setScreenInfoLabels();
				break;

			case "INVALID_LOGIN":
				loginController.actionPerformed(new ActionEvent(0, 0, action));
				break;

			case "GAME_FULL":
				findGameController.actionPerformed(new ActionEvent(0, 0, action));
				break;

			case "GAME_NOT_FOUND":
				findGameController.actionPerformed(new ActionEvent(0, 0, action));
				break;

			case "LOBBY_PLAYER_INFO":
				lobbyController.addPlayerListing((ArrayList<PlayerJoinLeaveData>) ((GenericRequest) arg0).getData());
				break;

			case "CONFIRM_READY":
				lobbyController.unreadyButton();
				break;

			case "CONFIRM_UNREADY":
				lobbyController.readyButton();
				break;

			case "START_ERROR":
				lobbyController.setReadyLabel((String) ((GenericRequest) arg0).getData());
				break;

			case "CONFIRM_LEAVE_GAME":
				gameID = -1;
				try {
					GenericRequest rq = new GenericRequest("REQUEST_GAMES_INFO");
					sendToServer(rq);
				} catch (IOException why) {
					why.printStackTrace();
				}
				lobbyController.leaveGameLobby();
				break;

			case "GAME_STARTED":
				lobbyController.switchToGameScreen();
				gameController.addMap((ConcurrentHashMap<Integer, Block>) ((GenericRequest) arg0).getData("MAP"));
				gameController.addPlayers((ConcurrentHashMap<String, Player>) ((GenericRequest) arg0).getData("PLAYERS"));
				gameController.startGame();
				executor.submit(gameController);
				break;
			case "FORCE_DISCONNECT":
				try {
					closeConnection();
					SwingUtilities.invokeLater(() -> serverConnectionController.connectionTerminated("Server closed"));
				} catch (IOException e) {

				}
				break;
			case "CONFIRM_DISCONNECT_AND_EXIT":
				try {
					closeConnection();
					SwingUtilities.invokeLater(() -> serverConnectionController.connectionTerminated("Logged out Successfully"));
				} catch (IOException e) {

				}
				break;
			case "CONFIRM_LOGOUT":
				username = "";
				splashController.showThis();
				break;
			case "PLAYER_STATS":
				int[] s = (int[]) ((GenericRequest) arg0).getData();
				profileController.setScreenInfoLabels(s);
			}
		} else if (arg0 instanceof GameLobbyData) {
			GameLobbyData info = (GameLobbyData) arg0;
			gameID = info.getGameID();
			findGameController.changeToGameLobbyMenu();
			fixTheReadyButtonNotSayingReady();
			lobbyController.setMapNames(info.getMaps());
			lobbyController.joinGameLobby(info);
		} else if (arg0 instanceof PlayerJoinLeaveData) {
			// for when a player joins lobby client is currently connected to
		} else if (arg0 instanceof PlayerStatistics) {
			// for when client request player statistics from the server
		} else if (arg0 instanceof PlayerAction) {
			PlayerAction action = (PlayerAction) arg0;
			if (gameController.isStarted()) {
				gameController.handlePlayerAction(action);
			} else if (!gameController.isStarted() && action.getType().equals("CHAT_MESSAGE")) {
				lobbyController.chatMessage(action.getAction());
			}

		} else if (arg0 instanceof GameEvent) {
			GameEvent event = (GameEvent) arg0;
			gameController.handleGameEvent(event);
		} else if (arg0 instanceof MatchSettings) {
			MatchSettings s = (MatchSettings) arg0;
			lobbyController.updateGameLobbySettings(s);
		} else if (arg0 instanceof EndGameData) {
			EndGameData data = (EndGameData) arg0;
			gameOverController.reset();
			lobbyController.setReadyLabel("");
			gameOverController.setEndGameStats(data, username);
			gameController.stopGame();
			gameController.resetGame();
		}
	}

	public void cancelGame() {
		gameController.stopGame();
		gameID = -1;
	}

	public void setCreateAccountController(CreateAccountScreenController c) {
		createAccountController = c;
	}

	public void setFindGameController(FindGameScreenController c) {
		findGameController = c;
	}

	public void setProfileScreenController(ProfileScreenController c) {
		profileController = c;
	}
	public void setLoginController(LoginScreenController c) {
		loginController = c;
	}
  
	public void setServerConnectionController(ServerConnectionScreenController c) {
		serverConnectionController = c;
	}

	public void setSplashController(SplashScreenController c) {
		splashController = c;
	}

	public void setLobbyController(LobbyScreenController c) {
		lobbyController = c;
	}

	public void setGameController(GameScreenController c) {
		gameController = c;
	}

	public void setGameOverController(GameOverScreenController c) {
		gameOverController = c;
	}

	public void setUsername(String s) {
		username = s;
	}

	public String getUsername() {
		return username;
	}

	public String getServerName() {
		return serverName;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int g) {
		gameID = g;
	}

	public void fixTheReadyButtonNotSayingReady() {
		lobbyController.readyButton();
	}

	@Override
	protected void connectionClosed() {
		System.out.println("connection terminated");
	}
 }
