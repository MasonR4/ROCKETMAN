package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import data.PlayerJoinData;
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
	
	private LinkedHashMap<String, PlayerListingPanel> players = new LinkedHashMap<String, PlayerListingPanel>();
	
	public LobbyScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (LobbyScreen) clientPanel.getComponent(6);
		playerPanel = screen.getPlayerPanel();
	}
	
	public void updateReadyPlayers() {
		playerPanel.removeAll();
		for (Entry<String, PlayerListingPanel> e : players.entrySet()) {
			playerPanel.add(e.getValue());
		}
		playerPanel.repaint();
		playerPanel.revalidate();
	}
	
	public void addPlayerListing(ArrayList<PlayerJoinData> data) {
		playerPanel.removeAll();
		for (PlayerJoinData d : data) {
			PlayerListingPanel p = new PlayerListingPanel(d.getUsername());
			System.out.println(d.getUsername() + " " + client.getUsername());
			if (d.getUsername().equals(client.getUsername()) && d.isHost()) {
				p.setHost("Host (You)");
				screen.enableHostControls();
			} else if (d.getUsername().equals(client.getUsername())) {
				p.setHost("You");
			} else if (d.isHost()) {
				p.setHost("Host");
			}
			players.put(d.getUsername(), p);
			playerPanel.add(p);
		}
		playerPanel.repaint();
		playerPanel.revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "Ready":
			screen.unreadyReadyButton();
			players.get(client.getUsername()).ready();
			
			break;
		case "Not Ready":
			System.out.println("oop");
			screen.unreadyReadyButton();
			players.get(client.getUsername()).unready();
			
			break;
			
		case "Start Game":
			// TODO start game pass all info to server and game id and uhhh
			break;
			
		case "Leave":
			// TODO notify server of player leaving lobby
			client.setGameID(-1);
			break;
		}
		
	}

}
