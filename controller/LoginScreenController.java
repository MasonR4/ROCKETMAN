package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JPanel;

import client.Client;
import client.ClientUI;
import data.LoginData;
import menu_panels.LoginScreen;

public class LoginScreenController extends MenuController {

	private LoginScreen screen;
	
	public LoginScreenController(Client c, JPanel p, ClientUI ui) {
		super(c, p, ui);
	}

	public void setScreens() {
		screen = (LoginScreen) clientPanel.getComponent(2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		String username = screen.getUsername();
		char[] password = screen.getPassword();

		switch(action) {
		case "Login":
			LoginData loginInfo = new LoginData(username, password);
			screen.clearFields();
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
