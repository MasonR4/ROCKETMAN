package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Rectangle implements Serializable {
	private static final long serialVersionUID = 1370509841513275945L;

	private String username;

	private int size = 20;
	private int speed = 5;

	private int lives = 3;
	private boolean alive;
	
	private double heightRatio = 1;
	private double widthRatio = 1;
	private double sizeRatio = 1;
	
	private Color color;
	private Color colorFromWhenTheyWereAlive;

	private LinkedHashMap<String, Integer> velocities = new LinkedHashMap<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();

	public Player(int size, int x, int y) {
		super(x, y, size, size);
		this.size = size;
		setAlive(true);
		velocities.put("DOWN", 0);
	    velocities.put("UP", 0);
	    velocities.put("RIGHT", 0);
	    velocities.put("LEFT", 0);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
	}
	
	public void setScale(double hr, double wr, double sr) {
		heightRatio = hr;
		widthRatio = wr;
		sizeRatio = sr;
		//x = (int) (x * widthRatio);
		//y = (int) (y * heightRatio);
		size = (int) (size * sizeRatio);
		speed = (int) (speed * sizeRatio);
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
			if (block.isCollideable() && futureBounds.intersects(block.getBounds())) {return true;}
		}
		if (futureBounds.x < 0 || futureBounds.x > (900 * widthRatio) - size || futureBounds.y < 0 || futureBounds.y > (900 * heightRatio) - size) {return true;}
		return false;
	}

	public void die() {
		lives = 0;
		colorFromWhenTheyWereAlive = color;
		alive = false;
	}

	public void takeHit() {
		lives--;
		colorFromWhenTheyWereAlive = color;
		if (lives <= 0) {
			alive = false;
			die();
		}
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

	public Color getColorFromWhenTheyWereNotDeadAsInAlive() {
		return colorFromWhenTheyWereAlive;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Color getColorFromWhenTheyWereAlive() {
		return colorFromWhenTheyWereAlive;
	}
}
