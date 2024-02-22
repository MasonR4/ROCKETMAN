package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import data.LoginData;
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
		
		String username = screen.getUsername();
		char[] password = screen.getPassword();
		
		switch(action) {
		case "Login":
			// TODO make some check where client sends credentials to server
			
			LoginData loginInfo = new LoginData(username, password);
			try {
				client.sendToServer(loginInfo);
			} catch (IOException SERVER_HATES_UR_LOGIN_LOL) {
				SERVER_HATES_UR_LOGIN_LOL.printStackTrace();
			}
			
			
			break;
			
		case "Back":
			screen.clearFields();
			cl.show(clientPanel, "SPLASH");
			break;
			
		case "LOGIN_CONFIRMED":
			clientUI.updateConfigData("last_user", username);	
			cl.show(clientPanel, "MAIN");
			break;
			
		case "INVALID_LOGIN":
			screen.setError("Invalid username or password");
			break;
		}	
	}
}
