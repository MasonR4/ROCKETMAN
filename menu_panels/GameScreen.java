package menu_panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game_utilities.Player;

public class GameScreen extends JLayeredPane {
	
	private JPanel gamePanel;
	
	private JTextArea log;
	private JScrollPane logScrollPane;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	private LinkedHashMap<String, Player> players = new LinkedHashMap<String, Player>();
	
	ActionListener controller;
	
	public GameScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBounds(340, 0, 900, 900);
		gamePanel.setBorder(BorderFactory.createEtchedBorder());
		
		//add(gamePanel, 1);
	}
	
	public JPanel getGamePanel() {
		return gamePanel;
	}
	
	public void setPlayerState(String usr, Player p) {
		players.put(usr, p);
	}
	
	public void setPlayers(LinkedHashMap<String, Player> p) {
		players = p;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Player p : players.values()) {
			//p.draw(g);
			g.setColor(p.getColor());
			//g.drawRect(p.x, p.y, 20, 20);
			g.fillRect(p.x, p.y, 20, 20);
		}
	}
	
	public void setController(ActionListener c) {
		controller = c;
	}
}
