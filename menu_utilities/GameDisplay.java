package menu_utilities;
import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import game_utilities.Block;
import game_utilities.Effect;
import game_utilities.Missile;
import game_utilities.Player;
import game_utilities.RocketLauncher;

public class GameDisplay extends JPanel { 
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, RocketLauncher> launchers = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Missile> rockets = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Effect> effects = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	
	private static final long serialVersionUID = 1L;
	private static final Dimension SIZE = new Dimension(900, 900);
	
	public GameDisplay() {
		setSize(SIZE);
		setLayout(null);
		setDoubleBuffered(true);
		setBorder(BorderFactory.createEtchedBorder());
//		try {
//			setCursor(this);
//		} catch (IOException CURSOR_IS_KILL) {
//			CURSOR_IS_KILL.printStackTrace();
//		}
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
	
	public void setEffects(ConcurrentHashMap<Integer, Effect> e) {
		effects = e;
	}
	
	public void setCursor(JPanel panel) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("assets/crosshair1.png"));
        Image resizedImage = originalImage.getScaledInstance(6, 6, Image.SCALE_SMOOTH);
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = cursorImg.createGraphics();
        graphics2D.drawImage(resizedImage, 0, 0, null);
        graphics2D.dispose();
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "custom cursor");
        panel.setCursor(cursor);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Block b : blocks.values()) {
			g2d.setColor(b.getColor());
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, b.getOpacity()));
			g2d.fillRect(b.x, b.y, b.getBlockSize(), b.getBlockSize());
		}
		for (Missile m : rockets.values()) {
			m.draw(g);
		}
		for (Player p : players.values()) {
			if (p.isAlive()) {
				p.draw(g);
				launchers.get(p.getUsername()).draw(g);
			}
		}
		for (Effect e : effects.values()) {
			if (e.isAnimated()) {
				if (e.getFrameCount() != e.getFrames()) {
					e.animate();
					e.draw(g);
				}
			} else {
				e.draw(g);
			}
		}
	}
}
