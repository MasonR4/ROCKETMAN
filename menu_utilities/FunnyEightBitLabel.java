package menu_utilities;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

public class FunnyEightBitLabel extends JLabel {

	private double textAngle;

	public FunnyEightBitLabel(String s, int fontType, float fontSize, int angle) {
		super(s);
		setFont(new EightBitFont());
		this.setHorizontalTextPosition(CENTER);
		setCustomFontSize(fontType, fontSize);

		textAngle = Math.toRadians(angle);
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

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
        // Apply rendering hints for smooth text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Get the current font metrics to calculate the correct x and y positions
        // to make sure the rotated text is properly centered
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

        // Apply rotation
        AffineTransform original = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(textAngle, x + fm.stringWidth(getText()) / 2.0, y - fm.getAscent() / 2.0); // Rotate around the text center
        g2d.setTransform(at);

        // Draw the text
        super.paintComponent(g2d);

        // Reset to the original transform so other components aren't affected
        g2d.setTransform(original);
        g2d.dispose();
	}

}
