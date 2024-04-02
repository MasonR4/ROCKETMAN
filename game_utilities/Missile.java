package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Missile extends Rectangle {
	
	private int xVelocity;
	private int yVelocity;
	private int damage;
	
	private double direction = 0.0;
	
	private final int MISSILE_SIZE = 8;
	
	public Missile(int x, int y, int dmg) {
		this.x = x;
		this.y = x;
		damage = dmg;
		this.height = MISSILE_SIZE * 2;
		this.width = MISSILE_SIZE;
	}
	
	public void draw(Graphics g) {
		Graphics2D missileGraphics = (Graphics2D) g.create(); // adding rotation purely for visual
		
		double rotationCenter = y + (width /2);
		missileGraphics.rotate(Math.toRadians(direction), x, rotationCenter);
		
		double endXAfterRotation = x + (height * Math.cos(Math.toRadians(direction)));
		double endYAfterRotation = rotationCenter + (height * Math.sin(Math.toRadians(direction)));
		
		missileGraphics.setColor(Color.GRAY);
		missileGraphics.fillRect(x, y, width, MISSILE_SIZE * 2);
		missileGraphics.dispose();	
	}
	
	public void move() {
		this.x += xVelocity;
		this.y += yVelocity;
	}
	
	public void rotate(int x, int y) {
		double deltaX = x - this.x;
		double deltaY = y - this.y;
		
		this.direction = Math.toDegrees(Math.atan2(deltaY, deltaX));
	}
	
}
