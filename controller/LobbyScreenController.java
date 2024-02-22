package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import game.ClientUI;
import menu_panels.LobbyScreen;
import server.Client;

public class LobbyScreenController implements ActionListener {
	
	private Client client;
	private ClientUI clientUI;
	
	private JPanel clientPanel;
	private JPanel gamesPanel;
	private LobbyScreen screen;
	private JPanel playerPanel;
	
	private CardLayout cl;
	
	public LobbyScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (LobbyScreen) clientPanel.getComponent(6);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
