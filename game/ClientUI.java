package game;

import javax.swing.SwingUtilities;
import controller.*;
import data.GenericRequest;
import data.PlayerJoinLeaveData;
import menu_panels.*;
import menu_panels.SplashScreen;
import server.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class ClientUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private Client client;

	// read some stuff from the config
	private LinkedHashMap<String, String> configData = new LinkedHashMap<String, String>();
	private File config;
	private Scanner scanner;

	// controllers
	private ServerConnectionScreenController serverConnectionScreenController;
	private SplashScreenController splashScreenController;
	private LoginScreenController loginScreenController;
	private CreateAccountScreenController createAccountScreenController;
	private MainMenuScreenController mainMenuScreenController;
	private FindGameScreenController findGameScreenController;
	private LobbyScreenController lobbyScreenController;
	private GameScreenController gameScreenController;
	private ProfileScreenController profileScreenController;
	private GameOverScreenController gameOverScreenController;

	// menus
	private ServerConnectionScreen serverConnectionScreen;
	private SplashScreen splashScreen;
	private LoginScreen loginScreen;
	private CreateAccountScreen createAccountScreen;
	private MainMenuScreen mainMenuScreen;
	private ProfileScreen profileScreen;
	private FindGameScreen findGameScreen;
	private LobbyScreen lobbyScreen;
	private GameScreen gameScreen;
	private GameOverScreen gameOverScreen;

	// layout
	private JPanel containerPanel;

	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 938);
	private static final CardLayout CL = new CardLayout();
	private static final String DEFAULT_MENU = "SERVER_CONNECTION";

	public ClientUI() {
		setTitle("ROCKETMAN");
		setSize(DEFAULT_SIZE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		client = new Client();

		containerPanel = new JPanel(CL);
		containerPanel.setSize(DEFAULT_SIZE);

		// READ CONFIG
		config = new File("config.txt");
		try {
			scanner = new Scanner(config);
			while (scanner.hasNextLine()) {
				String[] input = scanner.nextLine().split("\\: ");
				if (input.length > 1) {
					configData.put(input[0], input[1]);
				} else {
					configData.put(input[0], "");
				}
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println("Could not read config.");
			System.out.println("falling back to defaults");

			configData.put("default_server", "127.0.0.1");
			configData.put("default_port", "8300");
			configData.put("last_user", "");
		}

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingProcedure();
			}
		});

		// MENU PANELS
		serverConnectionScreen = new ServerConnectionScreen();
		splashScreen = new SplashScreen();
		loginScreen = new LoginScreen();
		createAccountScreen = new CreateAccountScreen();
		mainMenuScreen = new MainMenuScreen();
		findGameScreen = new FindGameScreen();
		lobbyScreen = new LobbyScreen();
		gameScreen = new GameScreen();
		gameOverScreen = new GameOverScreen();
		profileScreen = new ProfileScreen();
		
		// ADD THEM
		containerPanel.add(serverConnectionScreen, "SERVER_CONNECTION");
		containerPanel.add(splashScreen, "SPLASH");
		containerPanel.add(loginScreen, "LOGIN");
		containerPanel.add(createAccountScreen, "CREATE_ACCOUNT");
		containerPanel.add(mainMenuScreen, "MAIN");
		containerPanel.add(findGameScreen, "FIND_GAME");
		containerPanel.add(lobbyScreen, "LOBBY");
		containerPanel.add(gameScreen, "GAME");
		containerPanel.add(profileScreen, "PROFILE");
		containerPanel.add(gameOverScreen, "GAME_OVER");

		// WE ARE DECLARING A NUMBER OF CONTROLLERS
		serverConnectionScreenController = new ServerConnectionScreenController(client, containerPanel, this);
		splashScreenController = new SplashScreenController(client, containerPanel);
		loginScreenController = new LoginScreenController(client, containerPanel, this);
		createAccountScreenController = new CreateAccountScreenController(client, containerPanel, this);
		mainMenuScreenController = new MainMenuScreenController(client, containerPanel, this);
		findGameScreenController = new FindGameScreenController(client, containerPanel, this);
		lobbyScreenController = new LobbyScreenController(client, containerPanel, this);
		gameScreenController = new GameScreenController(client, containerPanel, this);
		profileScreenController = new ProfileScreenController(client, containerPanel, this);
		gameOverScreenController = new GameOverScreenController(client, containerPanel, this);
		
		
		// ANNOYING EXTRA STEP
		serverConnectionScreen.setController(serverConnectionScreenController);
		splashScreen.setController(splashScreenController);
		loginScreen.setController(loginScreenController);
		createAccountScreen.setController(createAccountScreenController);
		mainMenuScreen.setController(mainMenuScreenController);
		findGameScreen.setController(findGameScreenController);
		lobbyScreen.setController(lobbyScreenController);
		gameScreen.setController(gameScreenController); 
		gameScreen.addMouseListener(gameScreenController);
		gameScreen.addMouseMotionListener(gameScreenController);
		profileScreen.setController(profileScreenController);
		gameOverScreen.setController(gameOverScreenController);

		// ANNOYING EXTRA EXTRA STEP
		client.setSplashController(splashScreenController);
		client.setCreateAccountController(createAccountScreenController);
		client.setFindGameController(findGameScreenController);
		client.setLoginController(loginScreenController);
		client.setMainMenuController(mainMenuScreenController);
		client.setServerConnectionController(serverConnectionScreenController);
		client.setLobbyController(lobbyScreenController);
		client.setGameController(gameScreenController);
		client.setProfileScreenController(profileScreenController);
		client.setGameOverController(gameOverScreenController);
		
		// pass a few default values
		serverConnectionScreen.setDefaultConnectionInfo(configData.get("default_server"), configData.get("default_port"));
		loginScreen.setDefaultUsername(configData.get("last_user"));

		// SHOW THE INITAL PANEL
		CL.show(containerPanel, DEFAULT_MENU);
		add(containerPanel);
		setVisible(true);

		// lol?
		serverConnectionScreenController.actionPerformed(new ActionEvent(this, 0, "BYPASS_CONNECTION_AND_ATTEMPT_LOGIN"));

		//CL.show(containerPanel, "GAME"); // FOR VIEWING UI WITHOUT GOING THROUGH LOGIN PROCESS (DEBUGGIN)
	}

	public void updateConfigData(String key, String value) {
		configData.put(key, value);
	}
	
	public void disconnectProcedure() {
		try {
			if (client.getGameID() != -1) {
				PlayerJoinLeaveData leaveData = new PlayerJoinLeaveData(client.getUsername());
				leaveData.setGameID(client.getGameID());
				leaveData.setJoining(false);
				client.sendToServer(leaveData);
			}
			if (gameScreenController.isStarted()) {
				client.cancelGame();
			}
			if(client.isConnected()) {
				GenericRequest rq = new GenericRequest("CLIENT_DISCONNECTING");
				rq.setData(client.getUsername());
				client.sendToServer(rq);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logoutProcedure() {
		GenericRequest l = new GenericRequest("CLIENT_LOGOUT");
		l.setData(client.getUsername());
		try {
			client.sendToServer(l);
		} catch (IOException bruh) {
			bruh.printStackTrace();
		}
	}
	
	public void closingProcedure() {
		if (client.isConnected()) {
			disconnectProcedure();
		}
		try {
			FileWriter writer = new FileWriter(config, false);

			for (Entry<String, String> entry : configData.entrySet()) {
				writer.write(entry.getKey() + ": " + entry.getValue());
				writer.write("\n");
			}
			writer.close();
		} catch (IOException wompwomp) {
			wompwomp.printStackTrace();
		}
		System.exit(0);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ClientUI();
			}
		});
	}
}
