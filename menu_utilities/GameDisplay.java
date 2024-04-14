package menu_utilities;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import game_utilities.Block;
import game_utilities.Missile;
import game_utilities.Player;
import game_utilities.RocketLauncher;

public class GameDisplay extends JPanel { 
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, RocketLauncher> launchers = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Missile> rockets = new ConcurrentHashMap<>();
	//private CopyOnWriteArrayList<Missile> rockets = new CopyOnWriteArrayList<>();
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
	
	public  void setPlayers(ConcurrentHashMap<String, Player> players2) {
		players = players2;
	}
	
	public void setLaunchers(ConcurrentHashMap<String, RocketLauncher> r) {
		launchers = r;
	}
	
	public void setRockets(ConcurrentHashMap<Integer, Missile> r) {
		rockets = r;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Player p : players.values()) {
			g2d.setColor(p.getColor());
			g2d.fillRect(p.x, p.y, 20, 20);
		}
		for (RocketLauncher l : launchers.values()) {
			l.draw(g);
		}
		for (Missile m : rockets.values()) {
			m.draw(g);
		}
		for (Block b : blocks.values()) {
			g2d.setColor(b.getColor());
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, b.getOpacity()));
			g2d.fillRect(b.x, b.y, b.getBlockSize(), b.getBlockSize());
		}
	}
}
