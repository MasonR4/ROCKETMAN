package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import game.ClientUI;
import menu_panels.FindGameScreen;
import server.Client;

public class FindGameScreenController implements ActionListener {
	
	private Client client;
	private ClientUI clientUI;
	
	private JPanel clientPanel;
	private FindGameScreen screen;
	
	private CardLayout cl;
	
	public FindGameScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (FindGameScreen) clientPanel.getComponent(5); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
		case "New Game":
			screen.showGameCreationPanel();
			break;
		case "Back":
			cl.show(clientPanel, "MAIN");
			break;
		
		}
	}
}
