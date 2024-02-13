package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.ClientUI;
import menu_panels.LoginScreen;
import server.Client;

public class LoginScreenController implements ActionListener {

	private Client client;
	private ClientUI clientUI;
	
	private LoginScreen screen;
	private JPanel clientPanel;
	
	private CardLayout cl;
	
	public LoginScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();		
		screen = (LoginScreen) p.getComponent(2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		
		
		switch(action) {
		case "Login":
		
			
			// TODO make some check where client sends credentials to server
			
			screen.setError("joe mama");
			clientUI.updateConfigData("last_user", "test");	

			
			break;
			
		case "Back":
			screen.clearFields();
			cl.show(clientPanel, "SPLASH");
			break;
		}	
	}
}
