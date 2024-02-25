package server;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import controller.CreateAccountScreenController;
import controller.FindGameScreenController;
import controller.GameScreenController;
import controller.LobbyScreenController;
import controller.LoginScreenController;
import controller.MainMenuScreenController;
import controller.ServerConnectionScreenController;
import controller.SplashScreenController;
import data.GenericRequest;
import data.PlayerData;
import data.PlayerJoinLeaveData;
import data.GameLobbyData;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {
	
	private String username = "default";
	private String serverName;
		
	private int gameID = -1;
	
	// controllers for each menu
	// possible that we won't need all of them in the client class
	private CreateAccountScreenController createAccountController;
	private FindGameScreenController findGameController;
	private LoginScreenController loginController;
	private LobbyScreenController lobbyController;
	private GameScreenController gameController;
	
	private MainMenuScreenController mainMenuController;
	private ServerConnectionScreenController serverConnectionController;
	private SplashScreenController splashController;
	
	public Client() {
		super("localhost", 8300);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object arg0) {
		if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
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
				
			case "FORCE_DISCONNECT":
				serverConnectionController.connectionTerminated();
//				try {
//					closeConnection();
//				} catch (IOException YOU_CANT_LEAVE) {
//					YOU_CANT_LEAVE.printStackTrace();
//				}
				break;
			case "CONFIRM_DISCONNECT_AND_EXIT":
				serverConnectionController.connectionTerminated();
//				try {
//					closeConnection();
//				} catch (IOException YOU_CANT_LEAVE) {
//					YOU_CANT_LEAVE.printStackTrace();
//				}
				break;
			} 
		} else if (arg0 instanceof GameLobbyData) {
			GameLobbyData info = (GameLobbyData) arg0;
			gameID = info.getGameID();
			findGameController.changeToGameLobbyMenu();
			lobbyController.joinGameLobby(info);
			
			
		} else if (arg0 instanceof PlayerJoinLeaveData) {
			// for when a player joins lobby client is currently connected to
		} else if (arg0 instanceof PlayerData) {
			// for when client request player statistics from the server
		} 		
	}
	
	public void setCreateAccountController(CreateAccountScreenController c) {
		createAccountController = c;
	}
	
	public void setFindGameController(FindGameScreenController c) {
		findGameController = c;
	}
	
	public void setLoginController(LoginScreenController c) {
		loginController = c;
	}
	
	public void setMainMenuController(MainMenuScreenController c) {
		mainMenuController = c;
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
	
	protected void connectionClosed() {
		System.out.println("connection terminated");
	}
 }
