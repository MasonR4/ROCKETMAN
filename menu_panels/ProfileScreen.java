package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;


public class ProfileScreen extends JPanel {
	
	private EightBitLabel title;
	
	private EightBitButton backButton;
	private EightBitButton logoutButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	
	public ProfileScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN PROFILE", Font.BOLD, 222f);
		title.setBounds(375, 45, 550, 150);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(675, 500, 250, 50);
		
		logoutButton = new EightBitButton("Logout");
		logoutButton.setBounds(675, 575, 250, 50);
		
		add(title);
		add(backButton);
		add(logoutButton);
		
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
