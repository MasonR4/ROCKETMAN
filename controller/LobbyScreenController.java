package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientUI;
import data.GameLobbyData;
import data.MatchSettings;
import data.PlayerAction;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import data.StartGameData;
import menu_panels.LobbyScreen;
import menu_utilities.EightBitLabel;
import menu_utilities.PlayerListingPanel;

public class LobbyScreenController extends MenuController {

	private LobbyScreen screen;
	private JPanel playerPanel;

	// STUFF FOR GAME STATS GOES HERE OH NO
	private ArrayList<String> mapNames = new ArrayList<>();
	private int selectedMap = 0;

	private int livesCount = 3;
	private final int MAX_LIVES = 10;
	private final int MIN_LIVES = 1;

	private EightBitLabel map;
	private EightBitLabel lives;

	private JTextField chat;

	public LobbyScreenController(Client c, JPanel p, ClientUI ui) {
		super(c, p, ui);
	}

	public void setScreens() {
		screen = (LobbyScreen) clientPanel.getComponent(6);
		playerPanel = screen.getPlayerPanel();
		map = screen.getMapLabel();
		lives = screen.getLivesLabel();

		chat = screen.getChat();
		chat.setFocusable(false);

		screen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "CHATTING");
		screen.getActionMap().put("CHATTING", new AbstractAction() {
			private static final long serialVersionUID = -1058188108492781306L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chat.isFocusOwner()) {
					String msg = chat.getText();
					if (!msg.isBlank()) {
						PlayerAction chatting = new PlayerAction(client.getGameID(), client.getUsername(), "CHAT_MESSAGE", client.getUsername() + ": " + msg);
						try {
							client.sendToServer(chatting);
						} catch (IOException ChatIsThisReal) {
							ChatIsThisReal.printStackTrace();
						}
					}
					chat.setText("Press Enter to chat...");
					chat.setForeground(Color.GRAY);
					chat.setFocusable(false);
				} else if (!chat.isFocusOwner()) {
					chat.setForeground(Color.BLACK);
					chat.setText("");
					chat.setFocusable(true);
					chat.requestFocusInWindow();
				}
			}
		});
	}

	public void addPlayerListing(ArrayList<PlayerJoinLeaveData> data) {
		playerPanel.removeAll();
		for (PlayerJoinLeaveData d : data) {
			PlayerListingPanel p = new PlayerListingPanel(d.getUsername(), getHeightRatio(), getWidthRatio(), getSizeRatio());
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

	public void setReadyLabel(String msg) {
		screen.setReadyLabel(msg);
	}

	public void joinGameLobby(GameLobbyData info) {
		SwingUtilities.invokeLater(() -> screen.setLobbyInfo(info.getName(), info.getHostName(), info.getPlayerCount(), info.getMaxPlayers()));
		SwingUtilities.invokeLater(() -> screen.updateLobbyInfo());
	}

	public void setMapNames(ArrayList<String> m) {
		mapNames = m;
	}

	public void leaveGameLobby() {
		if (screen.hasHostControls()) {
			screen.disableHostControls();
			screen.setHostControls(false);
		}
		cl.show(clientPanel, "FIND_GAME");
	}

	public void switchToGameScreen() {
		cl.show(clientPanel, "GAME");
	}

	public void chatMessage(String msg) {
		screen.chatIsThisReal(msg);
	}

	public void sendGameLobbySettings(MatchSettings s) {
		try {
			client.sendToServer(s);
		} catch (IOException SETTINGS_REJECTION) {
			SETTINGS_REJECTION.printStackTrace();
		}
	}

	public void updateGameLobbySettings(MatchSettings s) {
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
		MatchSettings info;
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
				StartGameData start = new StartGameData(client.getGameID(), mapNames.get(selectedMap), livesCount, true);
				try {
					client.sendToServer(start);
				} catch (IOException CANT_START) {
					CANT_START.printStackTrace();
				}
			break;

		case "Leave":
			if (screen.hasHostControls()) {
				screen.disableHostControls();
				screen.setHostControls(false);
			}
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
			info = new MatchSettings(client.getGameID(), mapNames.get(selectedMap), livesCount);
			sendGameLobbySettings(info);
			break;

		case "MAP-":
			selectedMap--;
			if (selectedMap < 0) {
				selectedMap = mapNames.size() - 1;
			}
			map.setText(mapNames.get(selectedMap));
			info = new MatchSettings(client.getGameID(), mapNames.get(selectedMap), livesCount);
			sendGameLobbySettings(info);
			break;

		case "LIVES+":
			livesCount++;
			if (livesCount > MAX_LIVES) {
				livesCount = MAX_LIVES;
			}
			lives.setText(Integer.toString(livesCount));
			info = new MatchSettings(client.getGameID(), mapNames.get(selectedMap), livesCount);
			sendGameLobbySettings(info);
			break;

		case "LIVES-":
			livesCount--;
			if (livesCount <= MIN_LIVES) {
				livesCount = MIN_LIVES;
				lives.setText("Sudden Death");
			} else {
				lives.setText(Integer.toString(livesCount));
			}
			info = new MatchSettings(client.getGameID(), mapNames.get(selectedMap), livesCount);
			sendGameLobbySettings(info);
			break;
		}
	}
}
