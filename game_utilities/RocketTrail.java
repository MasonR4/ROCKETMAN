package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RocketTrail extends Effect {

	private static final long serialVersionUID = -8184447855958197270L;

	public RocketTrail(int x, int y) {
		super(x, y);
		animated = true;
		frames = 120;
		height = 7;
		width = 7;
		color = new Color(173, 173, 173);
	}
	
	public void animate() {
		frameCounter++;
		if (frameCounter % 40 == 0) {
			height -= 2;
			width -= 2;
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(color);
		g2d.fillRect(x, y, width, height);
	}
}
