package game_utilities;

import java.awt.Color;
import java.awt.Graphics;

public class SpawnBlock extends Block {

	public SpawnBlock(int x, int y, int r, int c) {
		super(x, y, r, c);
		collideable = false;
		breakable = false;
		color = Color.WHITE;
	}
	
	
}
