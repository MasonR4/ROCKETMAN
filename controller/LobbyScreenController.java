package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import data.GameLobbyData;
import data.GenericRequest;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import game.ClientUI;
import menu_panels.LobbyScreen;
import menu_utilities.PlayerListingPanel;
import server.Client;

public class LobbyScreenController implements ActionListener {
	
	private Client client;
	private ClientUI clientUI;
	
	private LobbyScreen screen;
	
	private JPanel clientPanel;
	private JPanel playerPanel;
	
	private CardLayout cl;
	
	public LobbyScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
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
		screen.setLobbyInfo(info.getHostName(), info.getPlayerCount(), info.getMaxPlayers());
		screen.updateLobbyInfo();
	}
	
	public void leaveGameLobby() {
		cl.show(clientPanel, "FIND_GAME");
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
			// TODO start game pass all info to server and game id and uhhh
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
