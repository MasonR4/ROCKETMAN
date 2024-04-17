package game_utilities;

import java.awt.Color;

@SuppressWarnings("serial")
public class BreakableBlock extends Block {


    public BreakableBlock(int x, int y, int r, int c) {
        super(x, y, r, c);
        setBreakable(true);
        setCollideable(true);
        AirBlock next = new AirBlock(x, y, r, c);
        setNext(next);
        setColor(Color.BLACK);

        opacity = 1f;
    }

}