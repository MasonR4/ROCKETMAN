package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Missile extends Rectangle {
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int dmg;
	private int MISSILE_SIZE = 8;
	private int speed = 12;
	
	private String owner;
	
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
	
	public void move() {
		x += (double)xVelocity;
		y += (double)yVelocity;
	}
	
	public void setXVelocity(double xVel) {
		xVelocity = xVel;
	}
	
	public void setYVelocity(double yVel) {
		yVelocity = yVel;
	}
	
	public void setDirection(int mouseX, int mouseY) {
		int xDistance = mouseX - x;
		int yDistance = mouseY - y;
		
		double length = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
		
		double normalizedDx = xDistance / length;
        double normalizedDy = yDistance / length;   
        
        setXVelocity((normalizedDx * speed));
        setYVelocity((normalizedDy * speed));
        //System.out.println(xVelocity + " " + yVelocity);
	}

	public String getOwner() {
		return owner;
	}
}
