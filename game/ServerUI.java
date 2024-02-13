package game;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import server.Server;
import server.Database;

public class ServerUI extends JFrame {
	
	// important (or not)
	private Database database;
	private Server server;
	
	// some text labels
	private JTextArea log;
	private JLabel logLabel;
	private JPanel logPanel;
	private JScrollPane logScrollPanel;
	
	// panels
	private JPanel gamesScreen;
	private JPanel containerPanel;
	private JPanel serverInfoPanel;
	
	
	private JTextArea connectedClients;
	private JScrollPane clientsPanel;
	
	// stuff
	
	private CardLayout cl = new CardLayout();
	private FlowLayout fl = new FlowLayout();
	
	private static final String DEFAULT_MENU = "LOBBY";
	private static final Dimension DEFAULT_SIZE = new Dimension(900, 900);
	private static final Dimension WINDOW_SIZE = new Dimension(1100, 900);
	
	
	public ServerUI() {
		setTitle("ROCKETMAN - SERVER");
		setSize(WINDOW_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(fl);
		setLocationRelativeTo(null);
		
		gamesScreen = new JPanel();
		gamesScreen.setPreferredSize(DEFAULT_SIZE);
		gamesScreen.setBorder(new LineBorder(Color.BLACK, 1));
		gamesScreen.setBackground(Color.BLUE);
		
		containerPanel = new JPanel(cl);
		containerPanel.setPreferredSize(DEFAULT_SIZE);
		
		containerPanel.add(gamesScreen, "GAMES");
		
		log = new JTextArea(30, 50);
		logScrollPanel = new JScrollPane(log);
		
		logPanel = new JPanel(new BorderLayout());
		logPanel.add(logScrollPanel, BorderLayout.CENTER);
		
		logLabel = new JLabel("Server Console");
		logPanel.add(logLabel, BorderLayout.NORTH);
		
		serverInfoPanel = new JPanel();
		serverInfoPanel.setPreferredSize(new Dimension(200, 30));
		serverInfoPanel.setBackground(Color.MAGENTA);
		
		logPanel.add(serverInfoPanel, BorderLayout.CENTER);
		
		connectedClients = new JTextArea(20, 10);
		connectedClients.setEditable(false);
		
		clientsPanel = new JScrollPane(connectedClients);
		clientsPanel.setPreferredSize(new Dimension(200, 300));
		clientsPanel.setBorder(new LineBorder(Color.BLACK, 1));
		clientsPanel.setBackground(Color.RED);
		
		logPanel.add(clientsPanel, BorderLayout.SOUTH);
		
		cl.show(containerPanel, DEFAULT_MENU);
		
		add(containerPanel);
		add(logPanel);
		pack();
		setVisible(true);
		
		// PASS SERVER INTERACTABLE ELEMENTS BELOW
		server = new Server();
		
		server.setLog(log);
		
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
