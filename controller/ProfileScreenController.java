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

public class ProfileScreenController implements ActionListener {

	private Client client;
	private ClientUI clientUI;
	private ProfileScreen screen;
	private JPanel clientPanel;
	
	private CardLayout cl;
	
	public ProfileScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (ProfileScreen) clientPanel.getComponent(5);
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
