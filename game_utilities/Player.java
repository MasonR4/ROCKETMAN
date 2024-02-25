package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Player extends Rectangle {
	private String username;
	
	private int size;
	private int lives = 3;
	private int speed = 5;
	
	private Color color;
	
	private LinkedHashMap<String, Integer> velocities = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Boolean> collisions = new LinkedHashMap<String, Boolean>();
	
	private RocketLauncher rocketLauncher;
	
	public Player(int size, int x, int y) {
		super(x, y, size, size);
		this.size = size;
		
		velocities.put("DOWN", 0);
	    velocities.put("UP", 0);
	    velocities.put("RIGHT", 0);
	    velocities.put("LEFT", 0);
	    
	    collisions.put("HORIZONTAL", false);
	    collisions.put("VERTICAL", false);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
		//rocket.draw(g);
	}
	
	public void setVelocity(String dir) {
		velocities.put(dir, speed);
	}
	
	public void cancelVelocity(String dir) {
		velocities.put(dir, 0);
	}
	
	public void setCollision(String dir, boolean col) {
		collisions.put(dir, col);
	}
	
	public void setCollision2(PlayerCollision c) {
		collisions.put(c.getDirection(), c.isColliding());
	}
	
	public void move() {
		if (!collisions.get("HORIZONTAL")) {x += (velocities.get("LEFT") - velocities.get("RIGHT"));}
		if (!collisions.get("VERTICAL")) {y += (velocities.get("DOWN") - velocities.get("UP"));}
		//rocket.move();
	}
	
	public boolean checkCollision(int newX, int newY) {
		Rectangle futureBounds = new Rectangle(newX, newY, size, size);
		
//		for (Block block : new ArrayList<Block>()) {
//			if (futureBounds.intersects(block.getBounds())) {
//				return true;
//			}
//		}
		if (futureBounds.x < 350 || futureBounds.x > 1250 - size) {
        	return true;
        }
        if (futureBounds.y < 0 || futureBounds.y > 900 - size) {
        	return true;
        }
		return false;
	}
	
	public int getXVelocity() {
		return velocities.get("LEFT") - velocities.get("RIGHT");
	}
	
	public int getYVelocity() {
		return velocities.get("DOWN") - velocities.get("UP");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getBlockSize() {
		return size;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
