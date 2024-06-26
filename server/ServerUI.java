package server;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import server_utilities.ServerMenuScreen;
import server_utilities.ServerMenuScreenController;

public class ServerUI extends JFrame {

	private Server server;

	private File config;
	private LinkedHashMap<String, String> configData = new LinkedHashMap<>();
	private Scanner scanner;

	private ServerMenuScreen mainPanel;
	private ServerMenuScreenController controller;

	// stuff
	private static final Dimension WINDOW_SIZE = new Dimension(1100, 900);
	private static final long serialVersionUID = 1L;

	public ServerUI() {
		setTitle("ROCKETMAN - SERVER");
		setSize(WINDOW_SIZE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		server = new Server();
		server.setDatabase(new Database());
		mainPanel = new ServerMenuScreen();
		controller = new ServerMenuScreenController(server, mainPanel, this);
        mainPanel.setController(controller);
		server.setServerMenuController(controller);
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

	public void closingProcedure() {
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
