package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import game.ServerUI;
import menu_panels.ServerMenuScreen;
import server.Server;

public class ServerMenuScreenController implements ActionListener {

	private Server server;
	private ServerUI serverUI;
	
	private ServerMenuScreen screen;
	
	private JTextArea log;
	private JLabel status;
	
	public ServerMenuScreenController(Server s, ServerMenuScreen p, ServerUI ui) {
		server = s;
		screen = p;
		serverUI = ui;
		
		log = screen.getServerLog();
		status = screen.getServerStatusLabel();
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "Start":
			// TODO start server and get info from fields
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
					
					//log.append("Server '" + name + "' started on port '" + port + "'\n");
					//status.setText("RUNNING");
					//status.setForeground(Color.GREEN);
				} else {
					log.append("Server already started.\n");
				}
				
			} catch (IOException oops) {
				server.listeningException(oops);
			}
			
			break;
			
		case "Stop":
			// TODO stop server
		
			try {
				server.close();
				screen.enableQuitButton(true);
				//log.append("Server Stopped");
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
				
			case "/quit":
				System.exit(0);
				break;
			}
			break;
		}
	}
}
