package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.ConcurrentHashMap;

import server_utilities.GameLobby;

@SuppressWarnings("serial")
public class Missile extends Rectangle {
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int MISSILE_SIZE = 9;
	private int speed = 12;
	
	private boolean exploded = false;
	
	private String owner;
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
	
	public Missile(int nx, int ny, String s) {
		x = nx;
		y = ny;
		owner = s;
		height = MISSILE_SIZE;
		width = MISSILE_SIZE;
		setBounds(nx, ny, MISSILE_SIZE, MISSILE_SIZE);
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(35, 35, 35));
		g.fillOval(x, y, MISSILE_SIZE, MISSILE_SIZE);
		//g.setColor(Color.BLACK);
		//g.drawOval(x, y, MISSILE_SIZE, MISSILE_SIZE);
		//g.fillRect(x, y, MISSILE_SIZE, MISSILE_SIZE);
	}
	
	public void setBlocks(ConcurrentHashMap<Integer, Block> b) {
		blocks = b;
	}
	
	public void setPlayers(ConcurrentHashMap<String, Player> p) {
		players = p;
	}
	
	public void move() {
		x += xVelocity;
		y += yVelocity;		
	}
	
	public synchronized int checkBlockCollision() {
		for (Block block : blocks.values()) {
			if (block.isCollideable() && block.contains(getCenterX(), getCenterY())) {
				exploded = true;
				return block.getBlockNumber();
			}
		}
        return 0;
	}
	
	public boolean checkBoundaryCollision() {
	    if (getBounds().x < -1200 || getBounds().x > 1200 || getBounds().y < -1200 || getBounds().y > 1200) {
	        exploded = true;
	        return true;
	    }
	    return false;
	}
	
	public String checkPlayerCollision() {
		for (Player p : players.values()) {
			if (p.isAlive() && p.contains(getCenterX(), getCenterY()) && !p.getUsername().equals(owner)) {
				exploded = true;
				return p.getUsername();
			}
		}
		return "";
	}
	
	public void setXVelocity(double xVel) {
		xVelocity = xVel;
	}
	
	public void setYVelocity(double yVel) {
		yVelocity = yVel;
	}
	
	public int getFutureX() {
		return x += xVelocity * .1;
	}
	
	public int getFutureY() {
		return y += yVelocity * .1;
	}
	
	public void setDirection(int mouseX, int mouseY) {
		int xDistance = mouseX - x;
		int yDistance = mouseY - y;
		
		double length = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
		
		double normalizedDx = xDistance / length;
        double normalizedDy = yDistance / length;   
        
        setXVelocity((normalizedDx * speed));
        setYVelocity((normalizedDy * speed));
	}

	public String getOwner() {
		return owner;
	}

	public boolean isExploded() {
		return exploded;
	}
}
