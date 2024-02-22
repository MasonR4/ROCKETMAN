package server;

import java.util.ArrayList;

import controller.CreateAccountScreenController;
import controller.FindGameScreenController;
import controller.LoginScreenController;
import controller.MainMenuScreenController;
import controller.ServerConnectionScreenController;
import controller.SplashScreenController;
import data.GenericRequest;
import data.NewGameData;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {
	
	private String username;
	private int userID;
		
	private boolean isHost = false;
	
	// controllers for each menu
	// possible that we won't need all of them in the client class
	private CreateAccountScreenController createAccountController;
	private FindGameScreenController findGameController;
	private LoginScreenController loginController;
	private MainMenuScreenController mainMenuController;
	private ServerConnectionScreenController serverConnectionController;
	private SplashScreenController splashController;
	
	public Client() {
		super("localhost", 8300);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void handleMessageFromServer(Object arg0) {
		if (arg0 instanceof GenericRequest) {
			String action = ((GenericRequest) arg0).getMsg();
			switch(action) {
			case "GAMES_INFO":
				findGameController.addGameListings((ArrayList<NewGameData>) ((GenericRequest) arg0).getData());
				break;
			}
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
