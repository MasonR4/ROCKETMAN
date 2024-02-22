package game;

import javax.swing.SwingUtilities;
import controller.*;
import data.GenericRequest;
import menu_panels.*;
import menu_panels.SplashScreen;
import menu_utilities.TextFieldFilters;
import server.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class ClientUI extends JFrame {
	
	// hurr durr
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
	
	// menus
	private ServerConnectionScreen serverConnectionScreen;
	private SplashScreen splashScreen;
	private LoginScreen loginScreen;
	private CreateAccountScreen createAccountScreen;
	private MainMenuScreen mainMenuScreen;
	private FindGameScreen findGameScreen;
	private LobbyScreen lobbyScreen;
	
	// layout
	private JPanel containerPanel;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
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
			
		// i stole this from someone else's project but i think it would be doing everyone a disservice if we didnt have this
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
		
		// ADD THEM
		containerPanel.add(serverConnectionScreen, "SERVER_CONNECTION");
		containerPanel.add(splashScreen, "SPLASH");
		containerPanel.add(loginScreen, "LOGIN");
		containerPanel.add(createAccountScreen, "CREATE_ACCOUNT");
		containerPanel.add(mainMenuScreen, "MAIN");
		containerPanel.add(findGameScreen, "FIND_GAME");
		containerPanel.add(lobbyScreen, "LOBBY");
		
		// WE ARE DECLARING A NUMBER OF CONTROLLERS
		serverConnectionScreenController = new ServerConnectionScreenController(client, containerPanel, this);
		splashScreenController = new SplashScreenController(client, containerPanel);
		loginScreenController = new LoginScreenController(client, containerPanel, this);
		createAccountScreenController = new CreateAccountScreenController(client, containerPanel, this);
		mainMenuScreenController = new MainMenuScreenController(client, containerPanel, this);
		findGameScreenController = new FindGameScreenController(client, containerPanel, this);
		lobbyScreenController = new LobbyScreenController(client, containerPanel, this);
		
		// ANNOYING EXTRA STEP
		serverConnectionScreen.setController(serverConnectionScreenController);
		splashScreen.setController(splashScreenController);
		loginScreen.setController(loginScreenController);
		createAccountScreen.setController(createAccountScreenController);
		mainMenuScreen.setController(mainMenuScreenController);
		findGameScreen.setController(findGameScreenController);
		lobbyScreen.setController(lobbyScreenController);
		
		// ANNOYING EXTRA EXTRA STEP
		client.setSplashController(splashScreenController);
		client.setCreateAccountController(createAccountScreenController);
		client.setFindGameController(findGameScreenController);
		client.setLoginController(loginScreenController);
		client.setMainMenuController(mainMenuScreenController);
		client.setServerConnectionController(serverConnectionScreenController);
		client.setLobbyController(lobbyScreenController);
		
		// pass a few default values
		serverConnectionScreen.setDefaultConnectionInfo(configData.get("default_server"), configData.get("default_port"));
		loginScreen.setDefaultUsername(configData.get("last_user"));
		
		// SHOW THE INITAL PANEL
		CL.show(containerPanel, DEFAULT_MENU);
		add(containerPanel);
		setVisible(true);
		
		// lol?
		serverConnectionScreenController.actionPerformed(new ActionEvent(this, 0, "BYPASS_CONNECTION_AND_ATTEMPT_LOGIN"));
		
		//CL.show(containerPanel, "LOBBY"); // TODO FOR DEBUGGING REMOVE LATER
	}
	
	public void updateConfigData(String key, String value) {
		configData.put(key, value);		
	}
	
	public void closingProcedure() {
		try {
			GenericRequest rq = new GenericRequest("PLAYER_DISCONNECTING");
			rq.setData(client.getUserName());
			client.sendToServer(rq);
			client.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter(config, false);
			
			for(Entry<String, String> entry : configData.entrySet()) {
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
