package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedHashMap;

public class PlayerObject extends Rectangle {
	private static final long serialVersionUID = 5999865541601247731L;
	private String username;
	private int size;
	private Color color;
	
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
	
	public void move() {		
		x += (velocities.get("RIGHT") - velocities.get("LEFT"));
		y += (velocities.get("DOWN") - velocities.get("UP"));
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
	
	public void updatePosition(int nx, int ny) {		
		x = nx;
		y = ny;
	}	
}
