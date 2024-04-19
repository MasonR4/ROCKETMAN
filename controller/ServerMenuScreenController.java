package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import data.GameLobbyData;
import data.GenericRequest;
import game.ServerUI;
import menu_panels.ServerMenuScreen;
import server.Server;
import server_utilities.ServerGameListingPanel;

public class ServerMenuScreenController implements ActionListener {

	private Server server;
	private ServerUI serverUI;
	
	private ServerMenuScreen screen;
	
	private JTextArea log;
	//private JLabel status;
	
	private JPanel gamesPanel;
	
	public ServerMenuScreenController(Server s, ServerMenuScreen p, ServerUI ui) {
		server = s;
		screen = p;
		serverUI = ui;
		
		log = screen.getServerLog();
		//status = screen.getServerStatusLabel();
		gamesPanel = screen.getGamesPanel();
	}	
	
	public void addGameListings(ArrayList<GameLobbyData> games) {
		gamesPanel.removeAll();
		for (GameLobbyData g : games) {
				ServerGameListingPanel temp = new ServerGameListingPanel(g);
				temp.setController(this);
				gamesPanel.add(temp);
		}
		gamesPanel.repaint();
		gamesPanel.revalidate();
	}
	
	public void bruh() {
		gamesPanel.removeAll();
		gamesPanel.repaint();
		gamesPanel.revalidate();
		screen.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		String name = screen.getServerName();
		int port = screen.getPort();
		int timeout = screen.getTimeout();
		
		switch (action) {
		case "Start":
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
			server.sendToAllClients(new GenericRequest("FORCE_DISCONNECT"));
			server.stopServer();
			screen.enableQuitButton(true);
			break;
			
		case "Quit":
			serverUI.closingProcedure();
			break;
			
		case "Submit": // commands just like a real server console			
			String[] args = screen.getCommand();
			switch(args[0]) {
			
			case "/start":
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
				
			case "/stop":
				server.stopServer();
				screen.enableQuitButton(true);
				break;
			
			case "/list_connections":
				log.append("Connected Clients:\n");
				for (Thread c : server.getClientConnections()) {
					log.append(c.getName() + "\n");
				}
				break;
				
			case "/list_players":
				log.append("Connected Players:\n");
				for (String s : server.getConnectedPlayers()) {
					log.append(s + "\n");
				}
				log.append("Total: " + Integer.toString(server.getConnectedPlayerCount()) + "\n");
				break;
				
			case "/refresh_lobbies":
				addGameListings(server.getGames());
				log.append("Refreshed Lobbies\n");
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
