package game_utilities;

public class AirBlock extends Block {

	public AirBlock(int x, int y, int r, int c) {
		super(x, y, r, c);
		
		setBreakable(false);
		setCollideable(false);
	}

}
