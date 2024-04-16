package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RocketTrail extends Effect {

	private static final long serialVersionUID = -8184447855958197270L;

	public RocketTrail(int x, int y) {
		super(x, y);
		animated = true;
		frames = 21;
		height = 8;
		width = 8;
		color = new Color(173, 173, 173);
	}
	
	public void animate() {
		frameCounter++;
		if (frameCounter % 3 == 0) {
			height -= 1;
			width -= 1;
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(color);
		g2d.fillRect(x + (frameCounter / 3), y + (frameCounter / 3), width, height);
	}
}
