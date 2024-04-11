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
import game_utilities.RocketLauncher;
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
	private long reload_time = 0;
	
	private CardLayout cl;
	
	private ConcurrentHashMap<String, PlayerObject> players = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, RocketLauncher> launchers = new ConcurrentHashMap<>();
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
			RocketLauncher tempLauncher = new RocketLauncher((int) p.getCenterX(), (int) p.getCenterY(), 24, 6);
			tempPlayer.setUsername(p.getUsername());
			tempPlayer.setColor(p.getColor());
			tempLauncher.setOwner(p.getUsername());
			launchers.put(p.getUsername(), tempLauncher);
			players.put(p.getUsername(), tempPlayer);			
		}
		gamePanel.setPlayers(players);
		gamePanel.setLaunchers(launchers);
		gamePanel.setRockets(rockets);
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
			launchers.get(e.getKey()).moveLauncher((int) players.get(e.getKey()).getCenterX(), (int) players.get(e.getKey()).getCenterY(), 20);
		}
	}
	
	public synchronized void updateMissileData(ArrayList<Missile> r) {
		rockets.clear();
		for (Missile m : r) {
			rockets.add(m);
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
			reload_time -= 50;
			if (reload_time <= 0) {screen.setRandomLabel("ready");}
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
		if (reload_time <= 0) {
			screen.setRandomLabel("reloading");
			reload_time = 3000;
			Missile missile = new Missile(launchers.get(username).getEndX(), launchers.get(username).getEndY(), username);
			missile.setDirection(mouseX, mouseY);
			rockets.add(missile);
			PlayerActionData m = new PlayerActionData(client.getGameID(), username, "ROCKET_FIRED", "The missile knows where it is at all times. It knows this because it knows where it isn't. By subtracting where it is from where it isn't, or where it isn't from where it is (whichever is greater), it obtains a difference, or deviation. The guidance subsystem uses deviations to generate corrective commands to drive the missile from a position where it is to a position where it isn't, and arriving at a position where it wasn't, it now is. Consequently, the position where it is, is now the position that it wasn't, and it follows that the position that it was, is now the position that it isn't.\r\n" + "In the event that the position that it is in is not the position that it wasn't, the system has acquired a variation, the variation being the difference between where the missile is, and where it wasn't. If variation is considered to be a significant factor, it too may be corrected by the GEA. However, the missile must also know where it was.\r\n" + "The missile guidance computer scenario works as follows. Because a variation has modified some of the information the missile has obtained, it is not sure just where it is. However, it is sure where it isn't, within reason, and it knows where it was. It now subtracts where it should be from where it wasn't, or vice-versa, and by differentiating this from the algebraic sum of where it shouldn't be, and where it was, it is able to obtain the deviation and its variation, which is called error.");
			m.setMousePos(mouseX, mouseY);
			try {
				client.sendToServer(m);
			} catch (IOException theMissileDoesntKnowWhereItIs) {
				theMissileDoesntKnowWhereItIs.printStackTrace();
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		launchers.get(username).rotate(mouseX, mouseY);
		// TODO send mouse position to server so that rotations for every player can be replicated
		// but do it in a way that doesn't spam the server bc mousemoved is triggered like 10000 times a second
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
