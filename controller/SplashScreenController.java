package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JPanel;
import client.Client;
import client.ClientUI;

public class SplashScreenController extends MenuController {
//	private SplashScreen screenPanel; 
	
	public SplashScreenController(Client c, JPanel p, ClientUI cui) {
		super(c, p, cui);
//		screenPanel = (SplashScreen) p.getComponent(1); 
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
