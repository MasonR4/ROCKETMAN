package game_utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Explosion extends Effect {

	private static final long serialVersionUID = -6047857817432681736L;
	
	public Explosion(int x, int y) {
		super(x, y);
		frames = 20;
		color = Color.ORANGE;
		animated = true;
		height = 41;
		width = 41;
		setBounds(x, y, width, height);
	}
	
	public void animate() {
		frameCounter++;
		height -= 2;
		width -= 2;
		opacity -= 0.04f;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2d.setColor(color);
		g2d.fillRect(x + frameCounter, y + frameCounter, width - frameCounter, height - frameCounter);
	}
}
