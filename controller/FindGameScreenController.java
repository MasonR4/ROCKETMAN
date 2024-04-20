package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import client.Client;
import client.ClientUI;
import data.GameLobbyData;
import data.GenericRequest;
import data.PlayerJoinLeaveData;
import menu_panels.FindGameScreen;
import menu_utilities.EightBitButton;
import menu_utilities.GameCreationPanel;
import menu_utilities.GameListingPanel;

public class FindGameScreenController extends MenuController {
	private JPanel gamesPanel;
	private FindGameScreen screen;
	private GameCreationPanel newGameScreen;

	private static final int MAX_PLAYERS = 8;
	private static final int MIN_PLAYERS = 2;

	public FindGameScreenController(Client c, JPanel p, ClientUI ui) {
		super (c, p, ui);
	}

	public void setScreens() {
		screen = (FindGameScreen) clientPanel.getComponent(5);
		newGameScreen = screen.getGameCreationPanel();
		gamesPanel = screen.getGamesPanel();
	}

	public void addGameListings(ArrayList<GameLobbyData> games) {
		gamesPanel.removeAll();
		for (GameLobbyData g : games) {
			GameListingPanel temp = new GameListingPanel(g);
			temp.setController(this);
			gamesPanel.add(temp);
		}
		gamesPanel.repaint();
		gamesPanel.revalidate();
	}

	public void setScreenInfoLabels() {
		screen.setInfoLabels(client.getServerName(), client.getUsername());
	}

	public void changeToGameLobbyMenu() {
		cl.show(clientPanel, "LOBBY");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch(action) {
		case "New Game":
			newGameScreen.setFieldDefaults(client.getUsername());
			newGameScreen.setVisible(true);
			break;
		case "Back":
			cl.show(clientPanel, "MAIN");
			break;
		case "Confirm":
			String lobbyName = newGameScreen.getLobbyName();
			int maxPlayers = -1;
			if (newGameScreen.getMaxPlayers().isBlank()) {
				newGameScreen.setError("Max players cannot be blank");
			} else {
				maxPlayers = Integer.parseInt(newGameScreen.getMaxPlayers());
				if (maxPlayers >= MIN_PLAYERS && maxPlayers <= MAX_PLAYERS) {
					if (lobbyName.length() < 3) {
						newGameScreen.setError("Lobby name must be at least 3 characters in length");
					} else {
						GameLobbyData GameLobbyData = new GameLobbyData(lobbyName, client.getUsername(), 0, maxPlayers, client.getGameID());
						try {
							client.sendToServer(GameLobbyData);
							newGameScreen.setVisible(false);
						} catch (IOException SERVER_DENIED_GAME_CREATION) {
							SERVER_DENIED_GAME_CREATION.printStackTrace();
							newGameScreen.setError("Server Error Encountered, please try again later.");
						}
					}
				} else {
					newGameScreen.setError("Max Players must be between 2 & 8");
				}
			}
			break;

		case "Refresh":
			try {
				GenericRequest rq = new GenericRequest("REQUEST_GAMES_INFO");
				client.sendToServer(rq);
			} catch (IOException SERVER_DID_NOT_REFRESH) {
				SERVER_DID_NOT_REFRESH.printStackTrace();
			}
			break;

		case "Join +":
			EightBitButton buttonClicked = (EightBitButton) e.getSource();
			GameListingPanel sourceScreen = (GameListingPanel) buttonClicked.getParent();
			int gameID = sourceScreen.getGameID();
			PlayerJoinLeaveData joinData = new PlayerJoinLeaveData(client.getUsername());
			joinData.setGameID(gameID);
			joinData.setJoining(true);
			try {
				client.sendToServer(joinData);
			} catch (IOException SERVER_DENIED_JOINING) {
				SERVER_DENIED_JOINING.printStackTrace();
			}

			break;

		case "Cancel":
			newGameScreen.setVisible(false);
			break;

		case "GAME_CREATED":
			cl.show(clientPanel, "LOBBY");
			break;

		case "GAME_JOINED":
			cl.show(clientPanel, "LOBBY");
			break;

		case "GAME_FULL":
			screen.setError("Game is full");
			break;

		case "GAME_NOT_FOUND":
			screen.setError("Game not found - try refreshing");
			break;
		}
	}
}
