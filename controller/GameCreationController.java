package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import data.NewGameData;
import menu_utilities.GameCreationPanel;

public class GameCreationController implements ActionListener {
	
	private GameCreationPanel screen;
	
	public GameCreationController(GameCreationPanel gcp) {
		screen = gcp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
		case "Confirm":
			System.out.println("yoiuy");
			String lobbyName = screen.getLobbyName();
			int maxPlayers = Integer.parseInt(screen.getMaxPlayers()); 
			if (maxPlayers > 2 && maxPlayers <= 4) {
				NewGameData newGameData = new NewGameData(lobbyName, maxPlayers);
			} else {
				screen.setError("Max Players must be between 2 & 4.");
			}
			
			break;
		case "Cancel":
			
			break;
		}
	
		
	}

}
