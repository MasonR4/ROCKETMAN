package controller;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import client.Client;
import client.ClientUI;
import menu_panels.ProfileScreen;

public class ProfileScreenController extends MenuController {
	private ProfileScreen screen;

	public ProfileScreenController(Client c, JPanel p, ClientUI ui) {
		super(c, p, ui);
	}

	public void setScreens() {
		screen = (ProfileScreen) clientPanel.getComponent(8);
	}

	public void setScreenInfoLabels(int[] stats) {
		screen.setInfoLabels(stats, client.getUsername());
		cl.show(clientPanel, "PROFILE");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch(action) {
		case "Back":
			cl.show(clientPanel, "MAIN");
			screen.revalidate();
			screen.repaint();
			break;

		case "Logout":
			clientUI.logoutProcedure();
			break;

		}
	}
}
