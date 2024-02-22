package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import data.NewGameData;
import game.ServerUI;
import menu_panels.GameListingPanel;
import menu_panels.ServerMenuScreen;
import server.Server;
import server_utilities.ServerGameListingPanel;

public class ServerMenuScreenController implements ActionListener {

	private Server server;
	private ServerUI serverUI;
	
	private ServerMenuScreen screen;
	
	private JTextArea log;
	private JLabel status;
	
	private JPanel gamesPanel;
	
	public ServerMenuScreenController(Server s, ServerMenuScreen p, ServerUI ui) {
		server = s;
		screen = p;
		serverUI = ui;
		
		log = screen.getServerLog();
		status = screen.getServerStatusLabel();
		gamesPanel = screen.getGamesPanel();
	}	
	
//	public void addGameListings(ArrayList<NewGameData> games) {
//		for (NewGameData g : games) {
//			boolean newGame = true;
//			for (Component c : gamesPanel.getComponents()) {
//				if (c instanceof ServerGameListingPanel) {
//					if (((ServerGameListingPanel) c).getGameID() == g.getGameID()) {
//						newGame = false;
//					} 
//				}
//			}
//			if (newGame) {
//				ServerGameListingPanel temp = new ServerGameListingPanel(g);
//				temp.setController(this);
//				gamesPanel.add(temp);
//				
//			}
//		}
//		gamesPanel.revalidate();
//	}
	
	public void addGameListings(ArrayList<NewGameData> games) {
		gamesPanel.removeAll();
		for (NewGameData g : games) {
			ServerGameListingPanel temp = new ServerGameListingPanel(g);
			temp.setController(this);
			gamesPanel.add(temp);
		}
		gamesPanel.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "Start":
			String name = screen.getServerName();
			int port = screen.getPort();
			int timeout = screen.getTimeout();
			
			server.setPort(port);
			server.setName(name);
			server.setTimeout(timeout);
			
			try {
				if (!server.isListening()) {
					server.listen();
					
					serverUI.updateConfigData("server_name", name);
					serverUI.updateConfigData("default_port", Integer.toString(port));
					serverUI.updateConfigData("default_timeout", Integer.toString(timeout));
					
					screen.enableQuitButton(false);
				} else {
					log.append("Server already started.\n");
				}
				
			} catch (IOException oops) {
				oops.printStackTrace();
			}
			
			break;
			
		case "Stop":
			// TODO stop server
		
			try {
				server.close();
				screen.enableQuitButton(true);
			} catch (IOException bruh) {
				
			}
			
			break;
			
		case "Quit":
			
			serverUI.closingProcedure();
			System.exit(0);
			
			break;
			
		case "Submit":
			// TODO i want to make commands if we have time
			
			String[] args = screen.getCommand();
			switch(args[0]) {
			
			case "/start":
				// TODO start the server LOL
				break;
				
			case "/stop":
				// TODO STOP THE SERVER NO WAY
				break;
			
			case "/listconnections":
				for (Thread c : server.getClientConnections()) {
					log.append(c.getName() + "\n");
				}
				break;
				
			case "/quit":
				System.exit(0);
				break;
				
			default:
				log.append("Command not recognized.\n");
			}
			break;
		}
	}
}
