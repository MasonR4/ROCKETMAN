package menu_panels;

import java.awt.Font;
import javax.swing.JPanel;
import menu_utilities.EightBitLabel;

public class LobbyScreen extends JPanel {
	
	private EightBitLabel congrats;
	
	public LobbyScreen() {
		setSize(1600, 900);
		congrats = new EightBitLabel("congrats", Font.PLAIN, 50f);
		add(congrats);
	}
}
