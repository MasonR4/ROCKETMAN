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
	private int dmg;
	private int MISSILE_SIZE = 8;
	private int speed = 12;
	
	private boolean exploded = false;
	
	private String owner;
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
	
	public Missile(int nx, int ny, String s) {
		x = nx;
		y = ny;
		dmg = 10;
		owner = s;
		height = MISSILE_SIZE;
		width = MISSILE_SIZE;
		setBounds(nx, ny, MISSILE_SIZE, MISSILE_SIZE);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, MISSILE_SIZE, MISSILE_SIZE);
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
	
	public int checkCollision() {
		for (Block block : blocks.values()) {
			if (block.isCollideable() && block.contains(getCenterX(), getCenterY())) {
				exploded = true;
				System.out.println("collision with block " + block.getBlockNumber());
				return block.getBlockNumber();
			}
		}
		if (getBounds().x < -5000 || getBounds().x > 5000) {
			exploded = true;
			return -1;
		}
        if (getBounds().y < -5000 || getBounds().y > 5000) {
        	exploded = true;
        	return -1;
        }
        return 0;
	}
	
	public String checkPlayerCollision() {
		for (Player p : players.values()) {
			if (p.contains(getCenterX(), getCenterY()) && !p.getUsername().equals(owner)) {
				System.out.println("chjgskhj with + " + p.getUsername());
				return p.getUsername();
			}
		}
		return null;
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
