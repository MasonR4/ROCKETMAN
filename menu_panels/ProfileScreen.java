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
	private EightBitLabel winsLabel;
	private EightBitLabel elimLabel;
	private EightBitLabel lossesLabel;
	private EightBitLabel rocketsFiredLabel;
	private EightBitLabel powerupsLabel;
	private EightBitLabel info;
	
	private EightBitButton backButton;
	private EightBitButton logoutButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	
	public ProfileScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN PROFILE", Font.BOLD, 222f);
		title.setBounds(375, 45, 550, 150);
		
		info = new EightBitLabel("ROCKETER TAG: ", Font.PLAIN, 75f);
		info.setBounds(450, 175, 800, 50);
		
		winsLabel = new EightBitLabel ("Wins", Font.PLAIN, 50f);
		winsLabel.setBounds(75, 400, 300, 150);
		
		lossesLabel = new EightBitLabel ("Losses", Font.PLAIN, 50f);
		lossesLabel.setBounds(275, 400, 300, 150);
		
		elimLabel = new EightBitLabel ("Elimintions", Font.PLAIN, 50f);
		elimLabel.setBounds(500, 400, 300, 150);
		
		rocketsFiredLabel = new EightBitLabel ("Rockets Fired", Font.PLAIN, 50f);
		rocketsFiredLabel.setBounds(800, 400, 300, 150);
		
		powerupsLabel = new EightBitLabel ("Power Ups Collected", Font.PLAIN, 50f);
		powerupsLabel.setBounds(1100, 400, 500, 150);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(675, 500, 250, 50);
		
		logoutButton = new EightBitButton("Logout");
		logoutButton.setBounds(675, 575, 250, 50);
		
		add(title);
		add(info);
		add(winsLabel);
		add(lossesLabel);
		add(elimLabel);
		add(rocketsFiredLabel);
		add(powerupsLabel);
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
	public void setInfoLabels(String usr) {
		info.setText("ROCKETER TAG: " + usr);
	}
}
