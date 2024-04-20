package game_utilities;

import java.awt.Color;

public class SpawnBlock extends Block {
	private static final long serialVersionUID = -2450686365167559601L;

	private boolean occupied;

	public SpawnBlock(int x, int y, int r, int c) {
		super(x, y, r, c);
		collideable = false;
		breakable = false;
		color = Color.WHITE;
		opacity = 0f;
		setOccupied(false);
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
}
