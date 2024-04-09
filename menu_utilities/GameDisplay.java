package menu_utilities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import game_utilities.Player;

public class GameDisplay extends JPanel { 
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
	
	private static final long serialVersionUID = 1L;
	private static final Dimension SIZE = new Dimension(900, 900);
	
	public GameDisplay() {
		setSize(SIZE);
		setLayout(null);
		setBorder(BorderFactory.createEtchedBorder());
	}
	
	public void setPlayerState(String usr, Player p) {
		players.put(usr, p);
	}
	
	public void setPlayers(ConcurrentHashMap<String, Player> players2) {
		players = players2;
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
	
}
