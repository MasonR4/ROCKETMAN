package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import menu_utilities.EightBitButton;

public class GameOverScreen extends JPanel {
	
	private static final long serialVersionUID = -3090211630056380263L;
	
	
	
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	private ActionListener controller;
	
	public GameOverScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		
		
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