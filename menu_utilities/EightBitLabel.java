package menu_utilities;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EightBitLabel extends JLabel {

	private static final long serialVersionUID = 4862656518955498653L;

	public EightBitLabel(String s, int fontType, float fontSize) {
		super(s);
		setFont(new EightBitFont());
		setCustomFontSize(fontType, fontSize);
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	public void setCustomFontSize(int type, float size) {
	    Font tempFont = getFont();
	    if (tempFont != null) {
	        Font customSizeFont = tempFont.deriveFont(type, size);
	        setFont(customSizeFont);
	        revalidate();
	        repaint(); // Ensure the label is repainted after the update
	    } else {
	        System.out.println("The current font is null for " + this.getText()); // Check if the font was null
	    }
	}
}

