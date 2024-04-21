package menu_utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlayerHealthDisplay extends JPanel{

	private static final long serialVersionUID = 2733575429394155387L;

	private EightBitLabel nameDisplay;
	private JPanel healthBoxesHolder;

	private String username;
	private int lives;

	private Color color;

	public PlayerHealthDisplay(String s, int l, Color c, double heightRatio, double widthRatio, double sizeRatio) {
		username = s;
		lives = l;
		color = c;
		setLayout(null);
		setPreferredSize(new Dimension(310, 40));
		nameDisplay = new EightBitLabel(username, Font.PLAIN, (float) (25f * sizeRatio));
		nameDisplay.setHorizontalAlignment(SwingConstants.LEFT);
		nameDisplay.setBounds((int) (5 * widthRatio), (int) (0 * heightRatio), (int) (300 * widthRatio), (int) (20 * heightRatio));

		healthBoxesHolder = new JPanel();
		healthBoxesHolder.setLayout(null);
		healthBoxesHolder.setBounds((int) (5 * widthRatio), (int) (25 * heightRatio), (int) (300 * widthRatio), (int) (19 * heightRatio));

		for (int i = 0; i < lives; i++) {
			JPanel liveBox = new JPanel();
			liveBox.setBounds(0 + (i * (int) (3 * widthRatio)) + (i * (int) (27 * widthRatio)), (int) (5 * heightRatio), (int) (27 * widthRatio), (int) (10 * heightRatio));
			liveBox.setBackground(color);
			healthBoxesHolder.add(liveBox);
		}
		add(nameDisplay);
		add(healthBoxesHolder);
	}

	public void loseHealth() {
		if (lives >= 0) {
			healthBoxesHolder.remove(lives -1);
			lives--;
		} else {
			nameDisplay.setText("<html><font color ='black'>" + username + "</font><font color = '#750d0d'> - deceased </font>");
		}
	}

	public void die() {
		nameDisplay.setText("<html><font color ='black'>" + username + "</font><font color = '#750d0d'> - deceased </font>");
	}
}