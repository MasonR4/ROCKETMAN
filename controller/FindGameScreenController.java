package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import data.GenericRequest;
import data.NewGameData;
import game.ClientUI;
import menu_panels.FindGameScreen;
import menu_panels.GameListingPanel;
import menu_utilities.EightBitButton;
import menu_utilities.GameCreationPanel;
import server.Client;

public class FindGameScreenController implements ActionListener {
	
	private Client client;
	private ClientUI clientUI;
	
	private JPanel clientPanel;
	private JPanel gamesPanel;
	private FindGameScreen screen;
	private GameCreationPanel newGameScreen;
	
	private CardLayout cl;
	
	public FindGameScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (FindGameScreen) clientPanel.getComponent(5);
		newGameScreen = screen.getGameCreationPanel();
		gamesPanel = screen.getGamesPanel();
		
	}
	
	public void addGameListings(ArrayList<NewGameData> games) {
		gamesPanel.removeAll();
		for (NewGameData g : games) {
			GameListingPanel temp = new GameListingPanel(g);
			temp.setController(this);
			gamesPanel.add(temp);
		}
		gamesPanel.revalidate();
	}
	
	public void setScreenInfoLabels() {
		screen.setInfoLabels(client.getServerName(), client.getUserName());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
		case "New Game":
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
				if (maxPlayers > 2 && maxPlayers <= 4) {
					if (lobbyName.length() < 3) {
						newGameScreen.setError("Lobby name must be at least 3 characters in length");
					} else {
						NewGameData newGameData = new NewGameData(lobbyName, client.getUserName(), maxPlayers, -1);
						// request new game be made on the server
						try {
							client.sendToServer(newGameData);
							cl.show(clientPanel, "LOBBY");
						} catch (IOException SERVER_DENIED_GAME_CREATION) {
							SERVER_DENIED_GAME_CREATION.printStackTrace();
							newGameScreen.setError("Server Error Encountered, please try again later.");
						}
					}
				} else {
					newGameScreen.setError("Max Players must be between 2 & 4");
				}
			}
			// also need: send game data to server from client
			// have client change to lobby screen upon receiving confirmation 
			// of game creation from server
			// egads
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
			System.out.println("attempted to join game NOT IMPLENENTED YET lol get rekt 5head SHUT UP");
			EightBitButton buttonClicked = (EightBitButton) e.getSource();
			GameListingPanel sourceScreen = (GameListingPanel) buttonClicked.getParent();
			int gameID = sourceScreen.getGameID();
			break;
			
		case "Cancel":
			newGameScreen.setVisible(false);
			break;
		
		}
	}
}
