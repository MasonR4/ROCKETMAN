package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import game.ClientUI;
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
		//screen = new ProfileScreen(); // Pass the database object to ProfileScreen
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
