package menu_utilities;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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

	private EightBitLabel announcement;
	
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;
	
	public GameDisplay(double hr, double wr, double sr) {
		heightRatio = hr;
		widthRatio = wr;
		sizeRatio = sr;
		setSize(new Dimension((int) (SIZE.getWidth() * widthRatio), (int) (SIZE.getHeight() * heightRatio)));
		setLayout(null);
		setDoubleBuffered(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) (5 * sizeRatio)));
		announcement = new EightBitLabel("", Font.PLAIN, (float) (75f * sizeRatio));
		announcement.setHorizontalAlignment(SwingConstants.CENTER);
		announcement.setBounds((int) (50 * widthRatio), (int) (300 * heightRatio), (int) (800 * widthRatio), (int) (200 * heightRatio));
		add(announcement);
		try {
			setCursor(this);
		} catch (IOException youshouldnthaveaddedthecursorbackafteritbrokethefirst4timesIDIOT) {
			System.out.println("guess what the cursor messed up and broke everything again");
			youshouldnthaveaddedthecursorbackafteritbrokethefirst4timesIDIOT.printStackTrace();
		}
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

	public void setAnnouncement(String s) {
		announcement.setText(s);
	}

	public void setCursor(JPanel panel) throws IOException {
		BufferedImage cursorImg = ImageIO.read(new File("assets/crosshair.png"));
        int xCenter = cursorImg.getWidth() / 2;
        int yCenter = cursorImg.getHeight() / 2;

        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(xCenter, yCenter), "center hotspot cursor");
        panel.setCursor(cursor);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Block b : blocks.values()) {
			g2d.setColor(b.getColor());
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, b.getOpacity()));
			g2d.fillRect(b.x, b.y, (int) b.getSize().getWidth(), (int) b.getSize().getHeight());
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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
