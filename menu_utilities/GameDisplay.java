package menu_utilities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import game_utilities.AirBlock;
import game_utilities.Block;
import game_utilities.Missile;
import game_utilities.Player;
import game_utilities.PlayerObject;

public class GameDisplay extends JPanel { 
	private ConcurrentHashMap<String, PlayerObject> players = new ConcurrentHashMap<>();
	
	private ArrayList<Missile> rockets = new ArrayList<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	
	private static final long serialVersionUID = 1L;
	private static final Dimension SIZE = new Dimension(900, 900);
	
	public GameDisplay() {
		setSize(SIZE);
		setLayout(null);
		setDoubleBuffered(true);
		setBorder(BorderFactory.createEtchedBorder());
	}
	
	public void setBlocks(ConcurrentHashMap<Integer, Block> m) {
		blocks = m;
	}
	
	public void setPlayers(ConcurrentHashMap<String, PlayerObject> players2) {
		players = players2;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (PlayerObject p : players.values()) {
			g.setColor(p.getColor());
			g.fillRect(p.x, p.y, 20, 20);
		}
		for (Block b : blocks.values()) {
			g.setColor(b.getColor());
			g.fillRect(b.x, b.y, b.getBlockSize(), b.getBlockSize());
		}
	}
}
