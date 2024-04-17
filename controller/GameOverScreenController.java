package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import data.EndGameData;
import data.PlayerJoinLeaveData;
import game.ClientUI;
import menu_panels.GameOverScreen;
import server.Client;

public class GameOverScreenController implements ActionListener {
	private Client client;
	private ClientUI clientUI;
	
	private GameOverScreen screen;
	private JPanel clientPanel;
	
	private CardLayout cl;
	
	public GameOverScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();		
		screen = (GameOverScreen) clientPanel.getComponent(9);
	}

	public void setEndGameStats(EndGameData e) {
		screen.setEndGameStats(e);
		cl.show(clientPanel, "GAME_OVER");
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
