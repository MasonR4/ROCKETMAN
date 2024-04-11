package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedHashMap;

public class PlayerObject extends Rectangle {
	private String username;
	private int size;
	private Color color;
	
	private int lastX, lastY;
	
	private LinkedHashMap<String, Integer> velocities = new LinkedHashMap<String, Integer>();
	
	public PlayerObject(int size, int x, int y) {
		super(x, y, size, size);
		this.size = size;
		updatePosition(x, y);
		
		velocities.put("DOWN", 0);
	    velocities.put("UP", 0);
	    velocities.put("RIGHT", 0);
	    velocities.put("LEFT", 0);
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
	}
	
	public void setUsername(String usr) {
		username = usr;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void updatePosition(int newX, int newY) {
		lastX = x;
		lastY = y;
		
		x = newX;
		y = newY;
	}
	
	public void interpolatePosition(float alpha) {
		if (alpha >= 1) {
			alpha = 1;
		}
        x = lastX + Math.round(alpha * (this.x - lastX));
        y = lastY + Math.round(alpha * (this.y - lastY));
	}
	
	public void setVelocity(String dir) {
		velocities.put(dir, 5);
	}
	
	public int getVelocity(String dir) {
		return velocities.get(dir);
	}
	
	public void cancelVelocity(String dir) {
		velocities.put(dir, 0);
	}	
}
