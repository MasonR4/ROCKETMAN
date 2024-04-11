package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import game_utilities.PlayerObject;
import menu_panels.GameScreen;
import menu_utilities.GameDisplay;
import server.Client;

public class GameScreenController implements MouseListener, MouseMotionListener, ActionListener, Runnable {
	private volatile boolean running = false;
	
	private Client client;
	private String username;
	
	private GameScreen screen;
	private GameDisplay gamePanel;
	private JPanel clientPanel;
	
	private final long TARGET_DELTA = 16;

	private CardLayout cl;
	
	private ConcurrentHashMap<String, PlayerObject> players = new ConcurrentHashMap<>();
	
	private ArrayList<Missile> rockets = new ArrayList<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();	
	
	private int mouseX, mouseY;
	
	@SuppressWarnings("serial")
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
					PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "UP");
					try {
						client.sendToServer(playerAction);
					} catch (IOException ACTION_DENIED) {
						ACTION_DENIED.printStackTrace();
					}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "CANCEL_MOVE_UP");
		gamePanel.getActionMap().put("CANCEL_MOVE_UP", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "UP");
				try {
					client.sendToServer(playerAction);
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
				
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "MOVE_LEFT");
		gamePanel.getActionMap().put("MOVE_LEFT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
					PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "LEFT");
					try {
						client.sendToServer(playerAction);
					} catch (IOException ACTION_DENIED) {
						ACTION_DENIED.printStackTrace();
					}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "CANCEL_MOVE_LEFT");
		gamePanel.getActionMap().put("CANCEL_MOVE_LEFT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "LEFT");
				try {
					client.sendToServer(playerAction);
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "MOVE_DOWN");
		gamePanel.getActionMap().put("MOVE_DOWN", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "DOWN");
				try {
					client.sendToServer(playerAction);
				} catch (IOException ACTION_DENIED) {						
					ACTION_DENIED.printStackTrace();
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "CANCEL_MOVE_DOWN");
		gamePanel.getActionMap().put("CANCEL_MOVE_DOWN", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "DOWN");
				try {
					client.sendToServer(playerAction);
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "MOVE_RIGHT");
		gamePanel.getActionMap().put("MOVE_RIGHT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "MOVE", "RIGHT");
				try {
					client.sendToServer(playerAction);
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "CANCEL_MOVE_RIGHT");
		gamePanel.getActionMap().put("CANCEL_MOVE_RIGHT", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				PlayerActionData playerAction = new PlayerActionData(client.getGameID(), username, "CANCEL_MOVE", "RIGHT");
				try {
					client.sendToServer(playerAction);
				} catch (IOException ACTION_DENIED) {
					ACTION_DENIED.printStackTrace();
				}
			}
		});
	}
	
	public void addPlayers(ConcurrentHashMap<String, Player> newPlayers) {
		for (Player p : newPlayers.values()) {
			PlayerObject tempPlayer = new PlayerObject(p.getBlockSize(), p.x, p.y);
			tempPlayer.setUsername(p.getUsername());
			tempPlayer.setColor(p.getColor());
			players.put(p.getUsername(), tempPlayer);			
		}
		gamePanel.setPlayers(players);
	}
	
	public void addMap(ConcurrentHashMap<Integer, Block> m) {
		blocks.putAll(m);
		gamePanel.setBlocks(blocks);
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
	
	public void updatePlayerPositions(HashMap<String, int[]> positions) {
		for (Entry<String, int[]> e : positions.entrySet()) {
			players.get(e.getKey()).updatePosition(e.getValue()[0], e.getValue()[1]);
		}
	}
	
	@Override
	public void run() {
		while (running && !Thread.currentThread().isInterrupted()) {
			long startTime = System.currentTimeMillis();			
			
			gamePanel.repaint();
			
			
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
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
//		if (running) {
//			players.get(username).getLauncher().rotate(mouseX, mouseY);
//			PlayerActionData r = new PlayerActionData(client.getGameID(), username, "LAUNCHER_ROTATION", Integer.toString(mouseX) + "," + Integer.toString(mouseY));
//			try {
//				client.sendToServer(r);
//			} catch (IOException CannotRotateRocket) {
//				
//			}
//		}
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
	@Override
	public void mouseDragged(MouseEvent e) {}
}
