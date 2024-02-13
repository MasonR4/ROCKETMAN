package game;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import controller.ServerMenuScreenController;
import menu_panels.ServerMenuScreen;
import server.Server;
import server.Database;

public class ServerUI extends JFrame {
	
	// important (or not)
	private Database database;
	private Server server;
	
	private ServerMenuScreen mainPanel;
	private ServerMenuScreenController controller;
	
	// stuff
	
	private CardLayout cl = new CardLayout();
	private FlowLayout fl = new FlowLayout();
	
	private static final String DEFAULT_MENU = "LOBBY";
	private static final Dimension DEFAULT_SIZE = new Dimension(900, 900);
	private static final Dimension WINDOW_SIZE = new Dimension(1100, 900);
	
	
	public ServerUI() {
		setTitle("ROCKETMAN - SERVER");
		setSize(1100, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		server = new Server();
		
		controller = new ServerMenuScreenController(server, mainPanel, this);
		
        mainPanel = new ServerMenuScreen();
        mainPanel.setController(controller);
		
		add(mainPanel);
		setVisible(true);
		
		
	}
	
	public void playerJoined() {
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ServerUI();
			}
		});
	}
}
