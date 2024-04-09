package game_utilities;

import java.awt.Color;

public class BreakableBlock extends Block {

	public BreakableBlock(int x, int y, int r, int c) {
		super(x, y, r, c);
		// HARDCODED VALUES FOR EACH TYPE OF BLOCK SET THESE MANUALLY
		setBreakable(true);
		setCollideable(true);
		AirBlock next = new AirBlock(x, y, r, c);
		setNext(next);
		setColor(Color.BLACK);
	}

}
