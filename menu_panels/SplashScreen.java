package menu_panels;

import javax.swing.*;

import menu_utilities.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

public class SplashScreen extends JPanel {
	
	private static final long serialVersionUID = 9108348648139582288L;

	private EightBitLabel title;
	
	private EightBitButton loginButton;
	private EightBitButton createAccountButton;
	private EightBitButton quitButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	
	public SplashScreen() {
		
		setSize(DEFAULT_SIZE);
		setLayout(null);
		//setOpaque(false);
		
		title = new EightBitLabel("ROCKETMAN", Font.BOLD, 222f);
		title.setBounds(375, 45, 850, 150);
		
		loginButton = new EightBitButton("Login");
		loginButton.setBounds(675, 500, 250, 50);
		
		createAccountButton = new EightBitButton("Create Account");
		createAccountButton.setBounds(675, 575, 250, 50);
		
		quitButton = new EightBitButton("Disconnect");
		quitButton.setBounds(675, 650, 250, 50);
		
		add(title);
		add(loginButton);
		add(createAccountButton);
		add(quitButton);
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
