package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;
import menu_utilities.*;

public class MainMenuScreen extends JPanel {
	
	private EightBitLabel title;
	
	private EightBitButton playButton;
	private EightBitButton profileButton;
	private EightBitButton optionsButton;
	private EightBitButton quitButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	
	public MainMenuScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN", Font.BOLD, 222f);
		title.setBounds(375, 45, 850, 150);
		
		playButton = new EightBitButton("Play");
		playButton.setBounds(675, 500, 250, 50);
		
		profileButton = new EightBitButton("Profile");
		profileButton.setBounds(675, 575, 250, 50);
		
		optionsButton = new EightBitButton("Options");
		optionsButton.setBounds(675, 650, 250, 50);
		
		quitButton = new EightBitButton("Quit");
		quitButton.setBounds(675, 725, 250, 50);
		
		add(title);
		add(playButton);
		add(profileButton);
		add(optionsButton);
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
