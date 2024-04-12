package controller;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.ClientUI;
import menu_panels.LoginScreen;
import menu_panels.ServerConnectionScreen;
import menu_utilities.EightBitButton;
import server.Client;

public class ServerConnectionScreenController implements ActionListener {

	private Client client;
	private ClientUI clientUI;
	
	private JPanel clientPanel;
	private ServerConnectionScreen screenPanel;
	
	private LoginScreen loginScreen;
	private LoginScreenController loginController;
	
	private CardLayout cl;
	
	public ServerConnectionScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screenPanel = (ServerConnectionScreen) clientPanel.getComponent(0); 
		loginScreen = (LoginScreen) clientPanel.getComponent(2);		
	}
	
	public void connect (String addr, int port) throws IOException {
		client.setHost(addr);
		client.setPort(port);
		client.openConnection();
	}
	
	public void setClientUI(ClientUI c) {
		clientUI = c;
	}
	
	public void connectionTerminated() {
		clientUI.disconnectProcedure();
		cl.show(clientPanel, "SERVER_CONNECTION");
		screenPanel.setError("Server Closed");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		String address = screenPanel.getAddress();
		int port = 0;
		
		screenPanel.setError("");
		
		try {
			port = Integer.parseInt(screenPanel.getPort());
		} catch (NumberFormatException SHUTUP) {
			// literally just so it doesnt print a giant error to the console
		}
		
		switch (action) {
		case "Connect":
			try {
				connect(address, port);				
				cl.show(clientPanel, "SPLASH");
				
			} catch (IOException oops) {
				oops.printStackTrace();
				screenPanel.setError("Server Connection Failed");
			}			
			break;
		
		case "BYPASS_CONNECTION_AND_ATTEMPT_LOGIN":
			for(Component c : loginScreen.getComponents()) {
				if (c instanceof EightBitButton) {
					loginController = (LoginScreenController) ((EightBitButton) c).getActionListeners()[0];
				}
			}			
			try {
				connect(address, port);
				cl.show(clientPanel, "LOGIN");
				loginController.actionPerformed(new ActionEvent(this, 0, "BYPASS_LOGIN"));
			} catch (IOException QUIET_EXCEPTION) {
				screenPanel.setError("Automatic Connection Failed, Please Connect Manually.");
			}
			break;
			
		case "Quit":
			clientUI.closingProcedure();
			break;
		}
	}
}
