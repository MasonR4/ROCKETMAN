package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import game.ClientUI;
import menu_panels.MainMenuScreen;
import server.Client;

public class MainMenuScreenController implements ActionListener {
	
	private Client client;
	private ClientUI clientUI;
	
	private JPanel clientPanel;
	private MainMenuScreen mainScreen;
	
	private CardLayout cl;
	
	public MainMenuScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		mainScreen = (MainMenuScreen) clientPanel.getComponent(4); 
	}
	
	public void setClientUI(ClientUI c) {
		clientUI = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
		case "Play":
			
			break;
			
		case "Profile":
			
			break;
			
		case "Options":
			
			break;
			
		case "Quit":
			clientUI.closingProcedure();
			break;
		}
		
	}

}
