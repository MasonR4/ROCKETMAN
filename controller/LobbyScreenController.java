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
import game_utilities.Player;
import menu_panels.LobbyScreen;
import menu_utilities.PlayerListingPanel;
import server.Client;

public class LobbyScreenController implements ActionListener {
	
	private Client client;
	//private ClientUI clientUI;
	
	private LobbyScreen screen;
	
	private JPanel clientPanel;
	private JPanel playerPanel;
	
	private CardLayout cl;
	
	public LobbyScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		//clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (LobbyScreen) clientPanel.getComponent(6);
		playerPanel = screen.getPlayerPanel();
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
				screen.enableHostControls();
			} else if (d.getUsername().equals(client.getUsername())) {
				p.setHost("You");
			} else if (d.isHost()) {
				p.setHost("Host");
				screen.setDynamicLobbyInfo(d.getUsername(), data.size());
			}
			playerPanel.add(p);
		}
		SwingUtilities.invokeLater(() -> screen.updateLobbyInfo());
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
	
	public void leaveGameLobby() {
		SwingUtilities.invokeLater(() -> cl.show(clientPanel, "FIND_GAME"));
	}
	
	public void startGame() {
		SwingUtilities.invokeLater(() -> cl.show(clientPanel, "GAME"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
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
			try {
				StartGameData info = new StartGameData(client.getGameID());
				// TODO set other game parameters before sending once they are implemented
				client.sendToServer(info);
			} catch (IOException SERVER_DECLINED_TO_START_GAME) {
				SERVER_DECLINED_TO_START_GAME.printStackTrace();
			}
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
		}
		
	}

}
