package game_utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Explosion extends Effect {

	private static final long serialVersionUID = -6047857817432681736L;
	
	public Explosion(int x, int y) {
		super(x, y);
		frames = 8;
		color = Color.ORANGE;
		animated = true;
		height = 33;
		width = 33;
		setBounds(x, y, width, height);
	}
	
	public void animate() {
		frameCounter++;
		height -= 4;
		width -= 4;
		opacity -= 0.125f;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2d.setColor(color);
		g2d.fillRect(x, y, width - (frameCounter * 4), height - (frameCounter * 4));
	}
}
