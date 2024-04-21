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
	
	public PlayerHealthDisplay(String s, int l, Color c) {
		username = s;
		lives = l;
		color = c;
		setLayout(null);
		setPreferredSize(new Dimension(310, 40));
		nameDisplay = new EightBitLabel(username, Font.PLAIN, 25f);
		nameDisplay.setHorizontalAlignment(SwingConstants.LEFT);
		nameDisplay.setBounds(5, 0, 300, 20);
		
		healthBoxesHolder = new JPanel();
		healthBoxesHolder.setLayout(null);
		healthBoxesHolder.setBounds(5, 25, 300, 19);
		
		for (int i = 0; i < lives; i++) {
			JPanel liveBox = new JPanel();
			liveBox.setBounds(0 + (i * 3) + (i * 27), 5, 27, 10);
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
