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
	
	// panels
	private JPanel lobbyScreen;
	private JPanel gameScreen;
	
	private JPanel containerPanel;
	
	private JPanel logPanel;
	private JScrollPane logScrollPanel;
	
	private JPanel playersPanel;
	
	// stuff
	
	private CardLayout cl = new CardLayout();
	private FlowLayout fl = new FlowLayout();
	
	private String DEFAULT_MENU = "LOBBY";
	private Dimension DEFAULT_SIZE = new Dimension(900, 900);
	private Dimension WINDOW_SIZE = new Dimension(1100, 900);
	
	
	public ServerUI() {
		setTitle("ROCKETMAN - SERVER");
		setSize(WINDOW_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(fl);
		setLocationRelativeTo(null);
		
		lobbyScreen = new JPanel();
		lobbyScreen.setPreferredSize(DEFAULT_SIZE);
		lobbyScreen.setBorder(new LineBorder(Color.BLACK, 1));
		lobbyScreen.setBackground(Color.BLUE);
		
		gameScreen = new JPanel();
		gameScreen.setPreferredSize(DEFAULT_SIZE);
		gameScreen.setBorder(new LineBorder(Color.BLACK, 1));
		gameScreen.setBackground(Color.CYAN);
		
		containerPanel = new JPanel(cl);
		containerPanel.setPreferredSize(DEFAULT_SIZE);
		
		containerPanel.add(lobbyScreen, "LOBBY");
		containerPanel.add(gameScreen, "GAME");
		
		log = new JTextArea(30, 50);
		logScrollPanel = new JScrollPane(log);
		
		logPanel = new JPanel(new BorderLayout());
		logPanel.add(logScrollPanel, BorderLayout.CENTER);
		
		logLabel = new JLabel("Server Console");
		logPanel.add(logLabel, BorderLayout.NORTH);
		
		playersPanel = new JPanel();
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.PAGE_AXIS));
		playersPanel.setPreferredSize(new Dimension(200, 400));
		playersPanel.setBorder(new LineBorder(Color.BLACK, 1));
		playersPanel.setBackground(Color.RED);
		
		logPanel.add(playersPanel, BorderLayout.SOUTH);
		
		cl.show(containerPanel, DEFAULT_MENU);
		
		add(containerPanel);
		add(logPanel);
		pack();
		setVisible(true);
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
