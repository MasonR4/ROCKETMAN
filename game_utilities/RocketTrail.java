package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RocketTrail extends Effect {

	private static final long serialVersionUID = -8184447855958197270L;
	
	protected double heightRatio = 1;
	protected double widthRatio = 1;
	protected double sizeRatio = 1;
	
	public RocketTrail(int x, int y) {
		super(x, y);
		animated = true;
		frames = 21;
		height = 8;
		width = 8;
		color = new Color(173, 173, 173);
	}
	
	@Override
	public void setScale(double hr, double wr, double sr) {
		heightRatio = hr;
		widthRatio = wr;
		sizeRatio = sr;
		height = (int) (height * sizeRatio);
		width = (int) (width * sizeRatio);
		x = (int) (x * widthRatio);
		y = (int) (y * heightRatio);
	}
	
	@Override
	public void animate() {
		frameCounter++;
		if (frameCounter % 3 == 0) {
			height -= (int) (1 * sizeRatio);
			width -= (int) (1 * sizeRatio);
		}
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(color);
		g2d.fillRect(x + (frameCounter / 3), y + (frameCounter / 3), width, height);
	}
}
