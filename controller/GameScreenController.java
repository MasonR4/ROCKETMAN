package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import data.PlayerActionData;
import data.PlayerJoinLeaveData;
import game.ClientUI;
import game_utilities.Player;
import menu_panels.GameScreen;
import server.Client;

public class GameScreenController implements MouseListener, ActionListener {
	private volatile boolean running = false;
	
	private Client client;
	private String username;
	//private ClientUI clientUI;
	
	private GameScreen screen;
	private JPanel gamePanel;
	private JPanel clientPanel;
	
	private final long TARGET_DELTA = 16;

	private CardLayout cl;
	
	private LinkedHashMap<String, Player> players = new LinkedHashMap<String, Player>();
	
	public GameScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		username = client.getUsername();
		//clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (GameScreen) clientPanel.getComponent(7);
		gamePanel = screen.getGamePanel();
		
		// KEY BINDING STUFF HAPPENS HERE
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "MOVE_UP");
		gamePanel.getActionMap().put("MOVE_UP", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("w pressed");
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "MOVE_LEFT");
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "MOVE_DOWN");
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "MOVE_RIGHT");
		
	}
	
	public void addPlayers(LinkedHashMap<String, Player> newPlayers) {
		for (Entry<String, Player> e : newPlayers.entrySet()) {
			players.putAll(newPlayers);
		}
	}
	
	public void startGame() {
		running = true;
	}
	
	public void stopGame() {
		System.out.println("STOPPED GAMED");
		Thread.currentThread().interrupt();
		running = false;
	}
	
	public boolean isStarted() {
		return running;
	}
	
	public void handlePlayerAction(PlayerActionData data) {
	
	}
	
	
	public void run() {
		Thread.currentThread();
		while (!Thread.interrupted() && running) {
			long startTime = System.currentTimeMillis();
			// TODO game cycle similar to the one in game lobby but only for rendering objects locally

			for (Player p : players.values()) {
				// move player, collisions are checked server side
				p.move();
				SwingUtilities.invokeLater(() -> screen.setPlayers(players));
			}
			// for all blocks SwingUtilities.invokeLater(() -> gamePanel.paintBlocks());
			System.out.println("sglkhsgf");
			if (!client.isConnected()) {
				System.out.println("bro it died again");
			}
			
			long endTime = System.currentTimeMillis();
			long delta = endTime - startTime;
			long sleepTime = TARGET_DELTA - delta;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException DEAD) {
				Thread.currentThread().interrupt();
				running = false;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO launchRocket towards mouse cursor position use pythagorean theorem to calculate trajectory
		
	}
	
	// required function graveyard...
	@Override
	public void actionPerformed(ActionEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
