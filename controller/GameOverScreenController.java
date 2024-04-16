package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
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

	
	
	public void returnToLobby() {
		cl.show(clientPanel, "LOBBY");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
	}
}
