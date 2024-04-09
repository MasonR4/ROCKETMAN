package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import data.GenericRequest;
import menu_panels.SplashScreen;
import server.Client;

public class SplashScreenController implements ActionListener {

	private Client client;
	
	private JPanel clientPanel;
	private SplashScreen screenPanel; 
	
	private CardLayout cl;
	
	public SplashScreenController(Client c, JPanel p) {
		client = c;
		clientPanel = p;
		
		cl = (CardLayout) clientPanel.getLayout();
		screenPanel = (SplashScreen) p.getComponent(1); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch(action) {
		
		case "Login":
			cl.show(clientPanel, "LOGIN");
			break;
			
		case "Create Account":
			cl.show(clientPanel, "CREATE_ACCOUNT");
			break;
			
		case "Disconnect":
			try {
				client.closeConnection();
			} catch(IOException what) {
				System.out.println("DISCONNECT FAILED ABANDON SHIP");
				what.printStackTrace();
			}
			cl.show(clientPanel, "SERVER_CONNECTION");
			break;
		}
		
	}

}
