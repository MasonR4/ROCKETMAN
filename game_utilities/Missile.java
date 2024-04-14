package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.ConcurrentHashMap;

import server_utilities.GameLobby;

public class Missile extends Rectangle {
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int dmg;
	private int MISSILE_SIZE = 8;
	private int speed = 17;
	
	private String owner;
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();
	
	private GameLobby g;
	
	public Missile(int nx, int ny, String s) {
		x = nx;
		y = ny;
		dmg = 10;
		owner = s;
		height = MISSILE_SIZE;
		width = MISSILE_SIZE;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, MISSILE_SIZE, MISSILE_SIZE);
	}
	
	public void setBlocks(ConcurrentHashMap<Integer, Block> b) {
		blocks = b;
	}
	
	public void move() {
		x += xVelocity;
		y += yVelocity;		
	}
	
	public int checkCollision() {
		System.out.println("yeah missile collision woooo");
		for (Block block : blocks.values()) {
			if (block.isCollideable() && intersects(block.getBounds())) {
				System.out.println("blyad " + block.getBlockNumber());
				return block.getBlockNumber();
			}
		}
		if (getBounds().x < -8000 || getBounds().x > 8000 - MISSILE_SIZE) {
			System.out.println("sheesh the edge");
			return -1;}
        if (getBounds().y < -8000 || getBounds().y > 8000 - MISSILE_SIZE) {
        	System.out.println("sheesh the edge");
        	return -1;}
        return 0;
	}
	
	public void setXVelocity(double xVel) {
		xVelocity = xVel;
	}
	
	public void setYVelocity(double yVel) {
		yVelocity = yVel;
	}
	
	public void addListener(GameLobby gl) {
		g = gl;
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
        //System.out.println(xVelocity + ", " + yVelocity); // debug
	}

	public String getOwner() {
		return owner;
	}
}
