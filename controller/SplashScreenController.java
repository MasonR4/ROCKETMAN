package controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;

import client.Client;
import client.ClientUI;

public class SplashScreenController implements ActionListener {

	private Client client;
	private ClientUI clientUI;
	private JPanel clientPanel;
//	private SplashScreen screenPanel; 
	
	private CardLayout cl;
	
	public SplashScreenController(Client c, JPanel p, ClientUI cui) {
		client = c;
		clientPanel = p;
		clientUI = cui;
		
		cl = (CardLayout) clientPanel.getLayout();
//		screenPanel = (SplashScreen) p.getComponent(1); 
	}
	
	public void showThis() {
		cl.show(clientPanel, "SPLASH");
	}
	
	public double getHeightRatio() {
		return clientUI.getHeightRatio();
	}
	
	public double getWidthRatio() {
		return clientUI.getWidthRatio();
	}
	
	public double getSizeRatio() {
		return clientUI.getSizeRatio();
	}
	
	public Dimension getActualSize() {
		return clientUI.getActualSize();
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
