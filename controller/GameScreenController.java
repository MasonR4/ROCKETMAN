package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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
import menu_utilities.GameDisplay;
import server.Client;

public class GameScreenController implements MouseListener, ActionListener, Runnable {
	private volatile boolean running = false;
	//private Thread thread = new Thread(this);
	
	private Client client;
	private String username;
	//private ClientUI clientUI;
	
	private GameScreen screen;
	private GameDisplay gamePanel;
	private JPanel clientPanel;
	
	private final long TARGET_DELTA = 16;

	private CardLayout cl;
	
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
	
	public GameScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		//clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (GameScreen) clientPanel.getComponent(7);
		gamePanel = screen.getGamePanel();
		
		// KEY BINDING STUFF HAPPENS HERE
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "MOVE_UP");
		gamePanel.getActionMap().put("MOVE_UP", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (players.get(username).getVelocity("UP") == 0) {
					players.get(username).setVelocity("UP");
					PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "UP");
					try {
						client.sendToServer(playerAction);
						System.out.println("going up");
					} catch (IOException ACTION_DENIED) {
						ACTION_DENIED.printStackTrace();
					}
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "CANCEL_MOVE_UP");
		gamePanel.getActionMap().put("CANCEL_MOVE_UP", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				players.get(username).cancelVelocity("UP");
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "UP");
				try {
					client.sendToServer(playerAction);
					System.out.println("stopped going up");
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
				
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "MOVE_LEFT");
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "MOVE_DOWN");
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "MOVE_RIGHT");
		
	}
	
	public void addPlayers(ConcurrentHashMap<String, Player> newPlayers) {
		players.putAll(newPlayers);
	}
	
	public void startGame() {
		username = client.getUsername();
		screen.setUsername(username);
		running = true;
		//run();
		//thread.start();
	}
	
	public void stopGame() {
		running = false;
		Thread.currentThread().interrupt();
		//Thread.currentThread().interrupt();
	}
	
	public boolean isStarted() {
		return running;
	}
	
	public void handlePlayerAction(PlayerActionData data) {
		String type = data.getType();
		String username = data.getUsername();
		
		switch (type) {
		case "MOVE":
			players.get(username).setVelocity(data.getAction());
			break;
			
		case "CANCEL_MOVE":
			players.get(username).cancelVelocity(data.getAction());
			break;
			// TODO add rest of actions (client)
		}
		System.out.println("Handled action: " + type + " " + data.getAction() + " from " + username);
	}
	
	@Override
	public void run() {
		while (running && !Thread.currentThread().isInterrupted()) {
			long startTime = System.currentTimeMillis();
			// TODO game cycle similar to the one in game lobby but only for rendering objects locally

			for (Player p : players.values()) {
				// move player, collisions are checked server side
				p.move();
				SwingUtilities.invokeLater(() -> gamePanel.setPlayers(players));
			}
			// for all blocks SwingUtilities.invokeLater(() -> gamePanel.paintBlocks());
			
			
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
