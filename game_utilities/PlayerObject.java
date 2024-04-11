package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PlayerObject extends Rectangle {
	private static final long serialVersionUID = 5999865541601247731L;
	private String username;
	private int size;
	private int speed;
	private Color color;
	
	public PlayerObject(int size, int x, int y) {
		super(x, y, size, size);
		this.size = size;
		updatePosition(x, y);
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
