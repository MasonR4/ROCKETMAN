package game_utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Effect extends Rectangle {
	private static final long serialVersionUID = -7599985014110776973L;
	protected boolean animated;

	protected Color color;

	protected int frames;
	protected int frameCounter = 0;

	protected float opacity = 1f;

	protected int effectNumber;

	protected int height;
	protected int width;
	
	protected double heightRatio = 1;
	protected double widthRatio = 1;
	protected double sizeRatio = 1;

	public Effect(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void animate() {}
	public void draw(Graphics g) {}

	public void setEffectNumber(int i) {
		effectNumber = i;
	}
	
	public void setScale(double hr, double wr, double sr) {
		heightRatio = hr;
		widthRatio = wr;
		sizeRatio = sr;
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

	public int getFrameCount() {
		return frameCounter;
	}

	public float getOpacity() {
		return opacity;
	}
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
}
