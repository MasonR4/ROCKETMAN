package menu_utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class EightBitButton extends JButton {
	
	private static final long serialVersionUID = -5627843840653151735L;

	private EightBitFont font = new EightBitFont();

	public EightBitButton(String s) {
		super(s);
		setUI(new EightBitButtonUI());
		setFont(font);
	}

	class EightBitButtonUI extends BasicButtonUI {
		   @Override
		   public void installUI (JComponent c) {
		       super.installUI(c);
		       AbstractButton button = (AbstractButton) c;
		       button.setOpaque(false);
		       button.setBorder(new EmptyBorder(5, 15, 5, 15));
		   }
		   @Override
		   public void paint (Graphics g, JComponent c) {
		       AbstractButton b = (AbstractButton) c;
		       paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
		       super.paint(g, c);
		   }
		   private void paintBackground (Graphics g, JComponent c, int yOffset) {
		        Dimension size = c.getSize();
		        Graphics2D g2 = (Graphics2D) g;
		        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g.setColor(Color.BLACK);
		        g.fillRect(0, yOffset, size.width, size.height - yOffset);
		        g.setColor(c.getBackground());
		        g.fillRect(0, yOffset, size.width, size.height + yOffset - 5);

		        // Add a light border to the top, left, and right parts of the button
		        g.setColor(Color.BLACK);
		        g.drawLine(0, yOffset, size.width, yOffset); // Top
		        g.drawLine(0, yOffset, 0, size.height); // Left
		        g.drawLine(size.width - 1, yOffset, size.width - 1, size.height); // Right
		    }

		   @Override
		   public void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
		       AbstractButton b = (AbstractButton) c;
		       ButtonModel model = b.getModel();
		       FontMetrics fm = g.getFontMetrics();

		       /* Draw the Text */
		       if(model.isPressed()) {
		           g.setColor(b.getForeground());
		           g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset() + 2); // Add 2 to y position when button is pressed
		       } else {
		           g.setColor(b.getForeground());
		           g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());
		       }
		   }
		}

}
