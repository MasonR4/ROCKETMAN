package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Effect extends Rectangle {
	private static final long serialVersionUID = -7599985014110776973L;
	protected boolean animated;
	protected int frames;
	protected Color color;
	
	protected float opacity = 1f;
	
	protected int effectNumber;
	
	public Effect() {}
	public void animate() {}
	public void draw(Graphics g) {}
	
	public void setEffectNumber(int i) {
		effectNumber = i;
	}
	
	public int getEffectNumber() {
		return effectNumber;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isAnimated() {
		return animated;
	}
	
	public int getFrames() {
		return frames;
	}
	
	public float getOpacity() {
		return opacity;
	}
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
}
