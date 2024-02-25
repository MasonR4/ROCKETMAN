package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import data.PlayerActionData;
import data.PlayerJoinLeaveData;
import game.ClientUI;
import game_utilities.Player;
import menu_panels.GameScreen;
import server.Client;

public class GameScreenController implements MouseListener, ActionListener, Runnable {
	private volatile boolean running = false;
	
	private Client client;
	//private ClientUI clientUI;
	
	private GameScreen screen;
	private JPanel gamePanel;
	private JPanel clientPanel;
	


	private CardLayout cl;
	
	private LinkedHashMap<String, Player> players = new LinkedHashMap<String, Player>();
	
	public GameScreenController(Client c, JPanel p, ClientUI ui) {
		client = c;
		clientPanel = p;
		//clientUI = ui;
		
		cl = (CardLayout) clientPanel.getLayout();
		screen = (GameScreen) clientPanel.getComponent(7);
		gamePanel = screen.getGamePanel();
		
		// KEY BINDING STUFF HAPPENS HERE
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "MOVE_UP");
		gamePanel.getActionMap().put("MOVE_UP", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "MOVE_LEFT");
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "MOVE_DOWN");
		gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "MOVE_RIGHT");
		
		
		
		
	}
	
	public void addPlayers(ArrayList<PlayerJoinLeaveData> newPlayers) {
		
	}
	
	public void handlePlayerAction(PlayerActionData data) {
	
	}
	
	
	
	@Override
	public void run() {
		while (running) {
			// TODO game cycle similar to the one in game lobby but only for rendering objects locally
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException DEAD) {
				Thread.currentThread().interrupt();
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
