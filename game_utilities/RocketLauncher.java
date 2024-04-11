package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class RocketLauncher extends Rectangle {
	private int height;
	private int width;
	
	private int endX;
	private int endY;
	
	private double angle = 0.0;
	
	public RocketLauncher(int newX, int newY, int h, int w) {
		x = newX;
		y = newY;
		width = w;
		height = h;
	}
	
	public void draw(Graphics g) {
		Graphics2D launcherGraphics = (Graphics2D) g.create();
		
		double rotCenter = y + (width / 2);
		launcherGraphics.rotate(Math.toRadians(angle), x, rotCenter);
		
		double rotatedX = x + (height * Math.cos(Math.toRadians(angle)));
		double rotatedY = rotCenter + (height * Math.sin(Math.toRadians(angle)));
		
		endX = (int) rotatedX;
		endY = (int) rotatedY;
		
		launcherGraphics.setColor(Color.GRAY);
		launcherGraphics.fillRect(x,  y, width, height);
		launcherGraphics.dispose();
	}
	
	public void rotate(int mouseX, int mouseY) {
		double deltaX = mouseX = x;
		double deltaY = mouseY - y;
		angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
	}
	
	public void moveLauncher(int nx, int ny, int size) {
		x = nx;
		y = ny - (size / 3);
	}
	
	public double getAngle() {
		return angle;
	}
}
