package game_utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.Serializable;

public abstract class Block extends Rectangle implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String type;
	private static final int BLOCK_SIZE = 30;
	private static final Dimension BLOCK_DIMENSION = new Dimension(BLOCK_SIZE, BLOCK_SIZE);

	protected boolean collideable;
	protected boolean breakable;

	protected Block next;

	protected int xPos;
	protected int yPos;

	protected int row;
	protected int col;
	
	protected double scale;
	
	protected int number;

	protected Color color;
	protected float opacity;

	public Block(int x, int y, int r, int c) {
		xPos = x;
		yPos = y;
		row = r;
		col = c;
		// SET TO THE POSITION THE BLOCK IN THE GRID IN NUMERICAL ORDER FROM LEFT TO RIGHT
		number = (r * 30) + c;
		setSize(BLOCK_DIMENSION);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Block getNext() {
		return next;
	}

	public void setNext(Block next) {
		this.next = next;
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

	public float getOpacity() {
		return opacity;
	}

	public int getBlockSize() {
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

	public int getBlockNumber() {
		return number;
	}
}
