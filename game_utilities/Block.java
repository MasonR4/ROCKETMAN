package game_utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.Serializable;

public abstract class Block extends Rectangle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String type;
	private static final Dimension BLOCK_SIZE = new Dimension(20, 20);
	
	private boolean collideable;
	private boolean breakable;
	
	private Class<? extends Block> next;
	
	private int xPos;
	private int yPos;
	
	private int row;
	private int col;
	
	private Color color;
	
	// TODO add sprites 
	// private ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
	
	public Block(int x, int y, int r, int c) {
		xPos = x;
		yPos = y;
		row = r;
		col = c;
		setSize(BLOCK_SIZE);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Class<? extends Block> getNext() {
		return next;
	}

	public void setNext(Block next) {
		this.next = next.getClass();
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Color getColor() {
		return color;
	}
	
	public Dimension getBlockSize() {
		return BLOCK_SIZE;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isCollideable() {
		return collideable;
	}

	public void setCollideable(boolean collideable) {
		this.collideable = collideable;
	}

	public boolean isBreakable() {
		return breakable;
	}

	public void setBreakable(boolean breakable) {
		this.breakable = breakable;
	}	
}
