package client;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controller.CreateAccountScreenController;
import controller.FindGameScreenController;
import controller.GameOverScreenController;
import controller.GameScreenController;
import controller.LobbyScreenController;
import controller.LoginScreenController;
import controller.MainMenuScreenController;
import controller.ProfileScreenController;
import controller.ServerConnectionScreenController;
import controller.SplashScreenController;
import data.GenericRequest;
import data.PlayerJoinLeaveData;
import menu_panels.CreateAccountScreen;
import menu_panels.FindGameScreen;
import menu_panels.GameOverScreen;
import menu_panels.GameScreen;
import menu_panels.LobbyScreen;
import menu_panels.LoginScreen;
import menu_panels.MainMenuScreen;
import menu_panels.ProfileScreen;
import menu_panels.ServerConnectionScreen;
import menu_panels.SplashScreen;

public class ClientUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private Client client;

	// read some stuff from the config
	private LinkedHashMap<String, String> configData = new LinkedHashMap<>();
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

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 938);
	private static final CardLayout CL = new CardLayout();
	private static final String DEFAULT_MENU = "SERVER_CONNECTION";

	public ClientUI() {
		setTitle("ROCKETMAN");
		setSize(DEFAULT_SIZE);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		actualSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (actualSize.getWidth() > DEFAULT_SIZE.getWidth() && actualSize.getHeight() > DEFAULT_SIZE.getHeight()) {
			actualSize = DEFAULT_SIZE;
		}
		heightRatio = actualSize.getHeight() / DEFAULT_SIZE.getHeight();
		widthRatio = actualSize.getWidth() / DEFAULT_SIZE.getWidth();
		sizeRatio = (actualSize.getWidth() * actualSize.getHeight()) / (DEFAULT_SIZE.getWidth() * DEFAULT_SIZE.getHeight());
		setSize(actualSize);
		System.out.println("window size: " + actualSize);
		client = new Client();

		containerPanel = new JPanel(CL);
		containerPanel.setSize(actualSize);

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

		// WE ARE DECLARING A NUMBER OF CONTROLLERS
		serverConnectionScreenController = new ServerConnectionScreenController(client, containerPanel, this);
		splashScreenController = new SplashScreenController(client, containerPanel, this);
		loginScreenController = new LoginScreenController(client, containerPanel, this);
		createAccountScreenController = new CreateAccountScreenController(client, containerPanel, this);
		mainMenuScreenController = new MainMenuScreenController(client, containerPanel, this);
		findGameScreenController = new FindGameScreenController(client, containerPanel, this);
		lobbyScreenController = new LobbyScreenController(client, containerPanel, this);
		gameScreenController = new GameScreenController(client, containerPanel, this);
		profileScreenController = new ProfileScreenController(client, containerPanel, this);
		gameOverScreenController = new GameOverScreenController(client, containerPanel, this);

		// MENU PANELS
		serverConnectionScreen = new ServerConnectionScreen(serverConnectionScreenController);
		splashScreen = new SplashScreen(splashScreenController);
		loginScreen = new LoginScreen(loginScreenController);
		createAccountScreen = new CreateAccountScreen(createAccountScreenController);
		mainMenuScreen = new MainMenuScreen(mainMenuScreenController);
		findGameScreen = new FindGameScreen(findGameScreenController);
		lobbyScreen = new LobbyScreen(lobbyScreenController);
		gameScreen = new GameScreen(gameScreenController);
		gameOverScreen = new GameOverScreen(gameOverScreenController);
		profileScreen = new ProfileScreen(profileScreenController);

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

		// ANNOYING EXTRA STEP
		serverConnectionScreenController.setScreens();
		createAccountScreenController.setScreens();
		findGameScreenController.setScreens();
		gameOverScreenController.setScreens();
		gameScreenController.setScreens();
		lobbyScreenController.setScreens();
		loginScreenController.setScreens();
		profileScreenController.setScreens();

		// ANNOYING EXTRA EXTRA STEP
		gameScreen.addMouseListener(gameScreenController);
		gameScreen.addMouseMotionListener(gameScreenController);

		// ANNOYING EXTRA EXTRA EXTRA STEP
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

		//CL.show(containerPanel, "LOBBY"); // FOR VIEWING UI WITHOUT GOING THROUGH LOGIN PROCESS (DEBUGGING)
		// maybe you think there's some hacky reason or that im not experienced enough to use java layout managers but think again
		// truth is i hate every single one besides boxlayout and thats only useful in 0.005% of cases
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

	public double getHeightRatio() {
		return heightRatio;
	}

	public double getWidthRatio() {
		return widthRatio;
	}

	public Dimension getActualSize() {
		return actualSize;
	}

	public double getSizeRatio() {
		return sizeRatio;
	}
}
