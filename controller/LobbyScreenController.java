package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import data.GameLobbyData;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import data.StartGameData;
import game.ClientUI;
import menu_panels.LobbyScreen;
import menu_utilities.EightBitLabel;
import menu_utilities.PlayerListingPanel;
import server.Client;

public class LobbyScreenController implements ActionListener {
	
	private Client client;
	//private ClientUI clientUI;
	
	private LobbyScreen screen;
	
	private JPanel clientPanel;
	private JPanel playerPanel;
	
	private CardLayout cl;
	
	// STUFF FOR GAME STATS GOES HERE OH NO
	private ArrayList<String> mapNames = new ArrayList<String>();
	private int selectedMap = 0;
	
	private int livesCount = 3;
	private final int MAX_LIVES = 10;
	private final int MIN_LIVES = 1;
	
	private EightBitLabel map;
	private EightBitLabel lives;
	//private EightBitLabel reload;
	//private EightBitLabel time;
	
	//private HashMap<String, Integer> reloadSpeeds = new HashMap<>();
	// also set max time?
	
	public LobbyScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		//clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (LobbyScreen) clientPanel.getComponent(6);
		playerPanel = screen.getPlayerPanel();
		map = screen.getMapLabel();
		lives = screen.getLivesLabel();
		//reload = screen.getReloadLabel();
		//time = screen.getTimeLabel();
	}
	
	public void addPlayerListing(ArrayList<PlayerJoinLeaveData> data) {
		playerPanel.removeAll();
		for (PlayerJoinLeaveData d : data) {
			PlayerListingPanel p = new PlayerListingPanel(d.getUsername());
			if (d.isReady()) {
				p.ready();
			} else {
				p.unready();
			}
			if (d.getUsername().equals(client.getUsername()) && d.isHost()) {
				p.setHost("Host (You)");
				screen.setDynamicLobbyInfo(d.getUsername(), data.size());
				if (!screen.hasHostControls()) {
					screen.enableHostControls();
					screen.setHostControls(true);
				}
			} else if (d.getUsername().equals(client.getUsername())) {
				p.setHost("You");
			} else if (d.isHost()) {
				p.setHost("Host");
				screen.setDynamicLobbyInfo(d.getUsername(), data.size());
			}
			playerPanel.add(p);
		}
		screen.updateLobbyInfo();
		playerPanel.repaint();
		playerPanel.revalidate();
	}
	
	public void readyButton() {
		screen.readyReadyButton();
	}
	
	public void unreadyButton() {
		screen.unreadyReadyButton();
	}
	
	public void joinGameLobby(GameLobbyData info) {
		SwingUtilities.invokeLater(() -> screen.setLobbyInfo(info.getHostName(), info.getPlayerCount(), info.getMaxPlayers()));
		SwingUtilities.invokeLater(() -> screen.updateLobbyInfo());
	}
	
	public void setMapNames(ArrayList<String> m) {
		System.out.println("set maps to " + m);
		mapNames = m;
	}
	
	public void leaveGameLobby() {
		cl.show(clientPanel, "FIND_GAME");
	}
	
	public void switchToGameScreen() {
		cl.show(clientPanel, "GAME");
	}
	
	public void sendGameLobbySettings(StartGameData s) {
		try {
			client.sendToServer(s);
		} catch (IOException SETTINGS_REJECTION) {
			SETTINGS_REJECTION.printStackTrace();
		}
	}
	
	public void updateGameLobbySettings(StartGameData s) {
		if (!screen.getHostUsername().equals(client.getUsername())) {
			map.setText(s.getMap()); 
			if (s.getPlayerLives() == 1) {
				lives.setText("Sudden Death");
			} else {
				lives.setText(Integer.toString(s.getPlayerLives()));
			}
			screen.repaint();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		StartGameData info;
		switch (action) {
		case "Ready":
			if (client.isConnected()) {
				try {
					PlayerReadyData pr = new PlayerReadyData(client.getUsername(), client.getGameID(), true);
					client.sendToServer(pr);
				} catch (IOException READY_UP_DENIED) {
					READY_UP_DENIED.printStackTrace();
				}
			} else {
				System.out.println("Server connection lost");
			}
			break;
		case "Not Ready":
			if (client.isConnected()) {
				try {
					PlayerReadyData pr = new PlayerReadyData(client.getUsername(), client.getGameID(), false);
					client.sendToServer(pr);
				} catch (IOException UNREADY_UP_DENIED) {
					UNREADY_UP_DENIED.printStackTrace();
				}
			} else {
				System.out.println("Server connection Lost");
			}
			break;
			
		case "Start Game":
				info = new StartGameData(client.getGameID(), mapNames.get(selectedMap), livesCount, true);
				sendGameLobbySettings(info);
			break;
			
		case "Leave":
			PlayerJoinLeaveData leaveData = new PlayerJoinLeaveData(client.getUsername());
			leaveData.setJoining(false);
			leaveData.setGameID(client.getGameID());
			try {
				client.sendToServer(leaveData);
			} catch (IOException LEAVING_FAILED_YOU_ARE_TRAPPED) {
				LEAVING_FAILED_YOU_ARE_TRAPPED.printStackTrace();
			}
			break;
			
		case "MAP+":
			selectedMap++;
			if (selectedMap == mapNames.size()) {
				selectedMap = 0;
			}
			map.setText(mapNames.get(selectedMap));
			info = new StartGameData(client.getGameID(), mapNames.get(selectedMap), livesCount, false);
			sendGameLobbySettings(info);
			break;
			
		case "MAP-":
			selectedMap--;
			if (selectedMap < 0) {
				selectedMap = mapNames.size() - 1;
			}
			map.setText(mapNames.get(selectedMap));
			info = new StartGameData(client.getGameID(), mapNames.get(selectedMap), livesCount, false);
			sendGameLobbySettings(info);
			break;
			
		case "LIVES+":
			livesCount++;
			System.out.println("added lives");
			if (livesCount > MAX_LIVES) {
				livesCount = MAX_LIVES;
			}
			lives.setText(Integer.toString(livesCount));
			info = new StartGameData(client.getGameID(), mapNames.get(selectedMap), livesCount, false);
			sendGameLobbySettings(info);
			break;
			
		case "LIVES-":
			livesCount--;
			System.out.println("removed lives");
			if (livesCount <= MIN_LIVES) {
				livesCount = MIN_LIVES;
				lives.setText("Sudden Death");
			} else {
				lives.setText(Integer.toString(livesCount));
			}
			info = new StartGameData(client.getGameID(), mapNames.get(selectedMap), livesCount, false);
			sendGameLobbySettings(info);
			break;
		}
	}
}
