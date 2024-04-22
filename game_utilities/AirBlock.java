package game_utilities;

public class AirBlock extends Block {

	private static final long serialVersionUID = -9179445666156918085L;

	public AirBlock(int x, int y, int r, int c) {
		super(x, y, r, c);

		setBreakable(false);
		setCollideable(false);
	}

}
