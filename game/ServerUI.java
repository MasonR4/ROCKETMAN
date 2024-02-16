package game;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import javax.swing.*;
import controller.ServerMenuScreenController;
import menu_panels.ServerMenuScreen;
import server.Server;
import server.Database;

public class ServerUI extends JFrame {
	
	// important (or not)
	private Database database;
	private Server server;
	
	private File config;
	private LinkedHashMap<String, String> configData = new LinkedHashMap<String, String>();
	private Scanner scanner;
	
	private ServerMenuScreen mainPanel;
	private ServerMenuScreenController controller;
	
	// stuff
	private static final Dimension WINDOW_SIZE = new Dimension(1100, 900);
	
	
	public ServerUI() {
		setTitle("ROCKETMAN - SERVER");
		setSize(WINDOW_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		server = new Server();
		mainPanel = new ServerMenuScreen();
		
		controller = new ServerMenuScreenController(server, mainPanel, this);
		
        
        mainPanel.setController(controller);
		
        // READ CONFIG
     	config = new File("server_config.txt");
     	try {
     		scanner = new Scanner(config);
     		
     		while (scanner.hasNextLine()) {
     			String[] input = scanner.nextLine().split("\\: ");
     			if (input.length > 1) {
     				configData.put(input[0], input[1]);
     			} else {
     				configData.put(input[0], "");
     			}
     		}
     		
     		scanner.close();
     		
     	} catch (FileNotFoundException e) {
     		System.out.println("Could not read config.");
     		System.out.println("falling back to defaults");
     		
     		configData.put("server_name", "ROCKETMAN-SERVER");
     		configData.put("default_port", "8300");
     		configData.put("default_timeout", "5000");

     	}
     	
     	this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingProcedure();
			}
     	});
     	
     	mainPanel.setDefaultInfo(configData.get("server_name"), configData.get("default_port"), configData.get("default_timeout"));
		server.setLog(mainPanel.getServerLog());
		server.setStatusLabel(mainPanel.getServerStatusLabel());
     	add(mainPanel);
		setVisible(true);
		
		
	}
	
	public void updateConfigData(String key, String value) {
		configData.put(key, value);
	}
	
	public void playerJoined() {
		// TODO graphical representation of the player in the lobby
	}
	
	public void closingProcedure() {
		// TODO write some stuff to config and save player data in database
		// this one might be ugly if there are multiple games running at once
		
		try {
			FileWriter writer = new FileWriter(config, false);
			
			for(Entry<String, String> entry : configData.entrySet()) {
				writer.write(entry.getKey() + ": " + entry.getValue());
				writer.write("\n");
			} 
			
			writer.close();
		} catch (IOException wompwomp) {
			wompwomp.printStackTrace();
		}
		System.exit(0);
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
