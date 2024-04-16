package menu_utilities;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class EightBitFont extends Font {
	
	private static Font eightBitFont = null;
	
	static {
		try {
			eightBitFont = Font.createFont(Font.TRUETYPE_FONT, new File("8bit.ttf")).deriveFont(24f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(eightBitFont);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	protected EightBitFont() {
		super(eightBitFont);
	}

}
