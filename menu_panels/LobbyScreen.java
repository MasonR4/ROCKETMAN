package menu_panels;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

public class LobbyScreen extends JPanel {
	
	private int gameID;
	
	private EightBitLabel congrats;
	
	private ActionListener controller;
	
	public LobbyScreen() {
		setSize(1600, 900);
		congrats = new EightBitLabel("congrats", Font.PLAIN, 50f);
		add(congrats);
	}
	
	public void setController(ActionListener ac) {
		controller = ac;
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}
	
}
