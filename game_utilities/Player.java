package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Rectangle implements Serializable {
	private String username;
	
	private int size;
	private int lives = 3;
	private int speed = 5;
	
	private Color color;
	
	private LinkedHashMap<String, Integer> velocities = new LinkedHashMap<String, Integer>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	
	
	public Player(int size, int x, int y) {
		super(x, y, size, size);
		this.size = size;
		velocities.put("DOWN", 0);
	    velocities.put("UP", 0);
	    velocities.put("RIGHT", 0);
	    velocities.put("LEFT", 0);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
	}
	
	public void updatePosition(int nx, int ny) {
		x = nx;
		y = ny;
	}
	
	public void setVelocity(String dir) {
		velocities.put(dir, speed);
	}
	
	public int getVelocity(String dir) {
		return velocities.get(dir);
	}
	
	public void setBlocks(ConcurrentHashMap<Integer, Block> b) {
		blocks = b;
	}
	
	public void cancelVelocity(String dir) {
		velocities.put(dir, 0);
	}
	
	public void move() {		
		int newX = x + (velocities.get("RIGHT") - velocities.get("LEFT"));
		int newY = y + (velocities.get("DOWN") - velocities.get("UP"));
		if (!checkCollision(newX, y)) {x = newX;}
		if (!checkCollision(x, newY)) {y = newY;}
	}
	
	public boolean checkCollision(int newX, int newY) {
		Rectangle futureBounds = new Rectangle(newX, newY, size, size);
		
		for (Block block : blocks.values()) {
			if (futureBounds.intersects(block.getBounds())) {return true;}
		}
		if (futureBounds.x < 0 || futureBounds.x > 900 - size) {return true;}
        if (futureBounds.y < 0 || futureBounds.y > 865 - size) {return true;}
		return false;
	}
	
	public int getXVelocity() {
		return velocities.get("RIGHT") - velocities.get("LEFT");
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
