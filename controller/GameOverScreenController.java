package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientUI;
import data.EndGameData;
import data.PlayerJoinLeaveData;
import menu_panels.GameOverScreen;

public class GameOverScreenController extends MenuController {
	private GameOverScreen screen;

	public GameOverScreenController(Client c, JPanel p, ClientUI ui) {
		super(c, p, ui);
	}

	public void setScreens() {
		screen = (GameOverScreen) clientPanel.getComponent(9);
	}

	public void setEndGameStats(EndGameData e, String s) {
		screen.setEndGameStats(e, s);
		SwingUtilities.invokeLater(() -> {
			cl.show(clientPanel, "GAME_OVER");
		});
	}

	public void returnToLobby() {
		client.fixTheReadyButtonNotSayingReady();
		cl.show(clientPanel, "LOBBY");
	}

	public void reset() {
		screen.reset();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action) {
		case "GO_LOBBY":
			returnToLobby();
			break;
		case "LEAVE":
			PlayerJoinLeaveData leaveData = new PlayerJoinLeaveData(client.getUsername());
			leaveData.setJoining(false);
			leaveData.setGameID(client.getGameID());
			try {
				client.sendToServer(leaveData);
			} catch (IOException LEAVING_FAILED_YOU_ARE_TRAPPED) {
				LEAVING_FAILED_YOU_ARE_TRAPPED.printStackTrace();
			}
			break;
		}
	}
}
