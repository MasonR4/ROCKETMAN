package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.ServerUI;
import menu_panels.ServerMenuScreen;
import server.Server;

public class ServerMenuScreenController implements ActionListener {

	private Server server;
	private ServerUI serverUI;
	
	private ServerMenuScreen screen;
	
	public ServerMenuScreenController(Server s, ServerMenuScreen p, ServerUI ui) {
		server = s;
		screen = p;
		serverUI = ui;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "Start":
			// TODO start server and get info from fields
			String name = screen.getServerName();
			int port = screen.getPort();
			
			server.setPort(port);
			server.setName(name);
			
			break;
			
		case "Stop":
			// TODO stop server
			
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
				
			case "/quit":
				System.exit(0);
				break;
			}
			break;
		}
	}
}
