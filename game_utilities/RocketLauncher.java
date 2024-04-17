package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

@SuppressWarnings("serial")
public class RocketLauncher extends Rectangle {
	private int height;
	private int width;
	
	private String owner;
	private double angle = 0.0;
	
	private int endX;
	private int endY;
	
	private int mouseX;
	private int mouseY;
	
    public RocketLauncher(int newX, int newY, int h, int w) {
        x = newX;
        y = newY;
        width = w;
        height = h;
    }
	// TODO fix rocket rotation
    public void draw(Graphics g) {
        Graphics2D rocketGraphics = (Graphics2D) g.create();
        rocketGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        double rotationCenterY = y + (width / 4);

        rocketGraphics.rotate(Math.toRadians(angle), x, rotationCenterY);
        
        double endXAfterRotation = x + (height * Math.cos(Math.toRadians(angle)));
        double endYAfterRotation = rotationCenterY + (height * Math.sin(Math.toRadians(angle)));

        endX = (int) endXAfterRotation;
        endY = (int) endYAfterRotation;
        
        rocketGraphics.setColor(Color.GRAY);
        rocketGraphics.fillRect(x, y, height, width);

        rocketGraphics.dispose();
    }
		
    public void rotate(int mouseX, int mouseY) {
        double deltaX = (mouseX - x) - 340;
        double deltaY = mouseY - y;
        
        this.angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
    }
	
    public void moveLauncher(int nx, int ny, int size) {
        x = nx - (size / 6);
        y = ny - (size / 6);
    }
	
    public void setMousePos(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public double getAngle() {
		return angle;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}
}
