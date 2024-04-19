package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JPanel;

import client.Client;
import client.ClientUI;
import data.CreateAccountData;
import menu_panels.CreateAccountScreen;

public class CreateAccountScreenController implements ActionListener {

	private Client client;
	private ClientUI clientUI;
	
	private CreateAccountScreen screen;
	private JPanel clientPanel;
	
	private CardLayout cl;
	
	public CreateAccountScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();		
		screen = (CreateAccountScreen) clientPanel.getComponent(3);
	}
	
	public void setError(String msg) {
		screen.setError(msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "Create Account":
			String username = screen.getUsername();
			char[] password = screen.getPassword();
			char[] confirmPassword = screen.getConfirmPassword();
			
			System.out.println("attempting to create account");
			
			if (password.length >= 8) {
				if (Arrays.equals(password, confirmPassword)) {
					if (username.length() >= 3) {
						CreateAccountData newAccount = new CreateAccountData(username, password);
						try {
							client.sendToServer(newAccount);
							clientUI.updateConfigData("last_user", username);
						} catch (IOException SERVER_HATES_UR_ACC_LOL) {
							SERVER_HATES_UR_ACC_LOL.printStackTrace();
							screen.setError("Server encountered an error during account creation");
						}						
					} else {
						screen.setError("Username must be at least 3 characters in length.");
					}
				} else {
					screen.setError("Passwords do not match.");
				}
			} else {
				screen.setError("Password must be at least 8 characters in length.");
			}
			
			break;
		case "ACCOUNT_CREATED":
			cl.show(clientPanel, "MAIN");
			break;
		case "Back":
			screen.clearFields();
			cl.show(clientPanel, "SPLASH");
			break;
		}
	}
}
