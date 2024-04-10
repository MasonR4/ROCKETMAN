package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import data.PlayerActionData;
import game.ClientUI;
import game_utilities.Block;
import game_utilities.Missile;
import game_utilities.Player;
import menu_panels.GameScreen;
import menu_utilities.GameDisplay;
import server.Client;

public class GameScreenController implements MouseListener, ActionListener, Runnable {
	private volatile boolean running = false;
	
	private Client client;
	private String username;
	
	private GameScreen screen;
	private GameDisplay gamePanel;
	private JPanel clientPanel;
	
	private final long TARGET_DELTA = 16;

	private CardLayout cl;
	
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
	
	private ArrayList<Missile> rockets = new ArrayList<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();	
	
	
	public GameScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		
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
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "MOVE_LEFT");
		gamePanel.getActionMap().put("MOVE_LEFT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (players.get(username).getVelocity("LEFT") == 0) {
					players.get(username).setVelocity("LEFT");
					PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "LEFT");
					try {
						client.sendToServer(playerAction);
						System.out.println("going LEFT");
					} catch (IOException ACTION_DENIED) {
						ACTION_DENIED.printStackTrace();
					}
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "CANCEL_MOVE_LEFT");
		gamePanel.getActionMap().put("CANCEL_MOVE_LEFT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				players.get(username).cancelVelocity("LEFT");
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "LEFT");
				try {
					client.sendToServer(playerAction);
					System.out.println("stopped going LEFT");
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
				
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "MOVE_DOWN");
		gamePanel.getActionMap().put("MOVE_DOWN", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (players.get(username).getVelocity("DOWN") == 0) {
					players.get(username).setVelocity("DOWN");
					PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "DOWN");
					try {
						client.sendToServer(playerAction);
						System.out.println("going DOWN");
					} catch (IOException ACTION_DENIED) {
						ACTION_DENIED.printStackTrace();
					}
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "CANCEL_MOVE_DOWN");
		gamePanel.getActionMap().put("CANCEL_MOVE_DOWN", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				players.get(username).cancelVelocity("DOWN");
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "DOWN");
				try {
					client.sendToServer(playerAction);
					System.out.println("stopped going DOWN");
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
				
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "MOVE_RIGHT");
		gamePanel.getActionMap().put("MOVE_RIGHT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (players.get(username).getVelocity("RIGHT") == 0) {
					players.get(username).setVelocity("RIGHT");
					PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "RIGHT");
					try {
						client.sendToServer(playerAction);
						System.out.println("going RIGHT");
					} catch (IOException ACTION_DENIED) {
						ACTION_DENIED.printStackTrace();
					}
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "CANCEL_MOVE_RIGHT");
		gamePanel.getActionMap().put("CANCEL_MOVE_RIGHT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				players.get(username).cancelVelocity("RIGHT");
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "RIGHT");
				try {
					client.sendToServer(playerAction);
					System.out.println("stopped going RIGHT");
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
				
			}
		});
		
	}
	
	public void addPlayers(ConcurrentHashMap<String, Player> newPlayers) {
		players.putAll(newPlayers);
	}
	
	public void addMap(ConcurrentHashMap<Integer, Block> m) {
		blocks.putAll(m);
	}
	
	public void startGame() {
		username = client.getUsername();
		screen.setUsername(username);
		running = true;
	}
	
	public void stopGame() {
		running = false;
		Thread.currentThread().interrupt();
	}
	
	public boolean isStarted() {
		return running;
	}
	
	public void handlePlayerAction(PlayerActionData data) {
		String type = data.getType();
		String username = data.getUsername();
		// TODO change to update player attributes directly not set
		switch (type) {
		case "MOVE":
			players.get(username).setVelocity(data.getAction());
			break;
			
		case "CANCEL_MOVE":
			players.get(username).cancelVelocity(data.getAction());
			break;
		}
		System.out.println("Handled action: " + type + " " + data.getAction() + " from " + username); // DEBUG remove later
	}
	
	public void updatePlayerPositions(HashMap<String, int[]> positions) {
		for (Entry<String, int[]> e : positions.entrySet()) {
			players.get(e.getKey()).x = e.getValue()[0];
			players.get(e.getKey()).y = e.getValue()[1];
		}
	}
	
	@Override
	public void run() {
		while (running && !Thread.currentThread().isInterrupted()) {
			long startTime = System.currentTimeMillis();
			
			SwingUtilities.invokeLater(() -> {
				gamePanel.setBlocks(blocks);
				gamePanel.setPlayers(players);
			});
			
			// TODO set missiles
			
			
			
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
