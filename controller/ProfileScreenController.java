package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import data.GenericRequest;
import game.ClientUI;
import menu_panels.FindGameScreen;
import menu_panels.ProfileScreen;
import server.Client;
import server.Database;

public class ProfileScreenController implements ActionListener {

	private Client client;
	private ClientUI clientUI;
	private ProfileScreen screen;
	private JPanel clientPanel;
	
	private CardLayout cl;
	
	public ProfileScreenController(Client c, JPanel p, ClientUI ui, Database db) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = new ProfileScreen(db); // Pass the database object to ProfileScreen
		screen = (ProfileScreen) clientPanel.getComponent(8);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
		case "Back":
			cl.show(clientPanel, "MAIN");
			break;
			
		case "Logout":
			//cl.show(clientPanel, "PROFILE");
			break;
		
		}
	}
	
	public void setScreenInfoLabels() {
		screen.setInfoLabels(client.getUsername()); 
	}
	
	
}
