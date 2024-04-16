package game_utilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class DeathMarker extends Effect {
	private static final long serialVersionUID = -6224212747550482056L;
	
	private int xPos;
	private int yPos;
	
	private int width = 20;
	private int height = 20;
	
	String username;
	
	public DeathMarker(int x, int y, String s) {
		super(x, y);
		username = s;
		animated = false;
		setBounds(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(color);
        g2d.drawLine(xPos, yPos, xPos + width, yPos + height);  
        g2d.drawLine(xPos + width, yPos, xPos, yPos + height);  
	}
	
	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}
	
}
