package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;
import server.Database;


public class ProfileScreen extends JPanel {
	
	private EightBitLabel title;
	private EightBitLabel winsLabel;
	private EightBitLabel wins;
	private EightBitLabel elimLabel;
	private EightBitLabel elims;
	private EightBitLabel lossesLabel;
	private EightBitLabel losses;
	private EightBitLabel rocketsFiredLabel;
	private EightBitLabel rocketsFired;
	private EightBitLabel blocksDestroyedLabel;
	private EightBitLabel blocksDestroyed;
	private EightBitLabel info;
	
	private EightBitButton backButton;
	private EightBitButton logoutButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	//private Database database;
	
	public ProfileScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		//database = db; // Set the database object
		
		title = new EightBitLabel("ROCKETMAN PROFILE", Font.BOLD, 125f);
		title.setBounds(150, 45, 1500, 150);
		
		info = new EightBitLabel("ROCKETER TAG: ", Font.PLAIN, 75f);
		info.setBounds(450, 175, 800, 50);
		
		winsLabel = new EightBitLabel ("Wins", Font.PLAIN, 50f);
		winsLabel.setBounds(75, 400, 300, 150);
		
		lossesLabel = new EightBitLabel ("Losses", Font.PLAIN, 50f);
		lossesLabel.setBounds(275, 400, 300, 150);
		
		elimLabel = new EightBitLabel ("Elimination", Font.PLAIN, 50f);
		elimLabel.setBounds(500, 400, 300, 150);
		
		rocketsFiredLabel = new EightBitLabel ("Rockets Fired", Font.PLAIN, 50f);
		rocketsFiredLabel.setBounds(800, 400, 300, 150);
		
		blocksDestroyedLabel = new EightBitLabel ("Blocks Destroyed", Font.PLAIN, 50f);
		blocksDestroyedLabel.setBounds(1100, 400, 500, 150);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(675, 700, 250, 50);
		
		logoutButton = new EightBitButton("Logout");
		logoutButton.setBounds(675, 775, 250, 50);
		
		wins = new EightBitLabel("", Font.BOLD, 50f);
		wins.setBounds(80, 360, 300, 150);
		
	    losses = new EightBitLabel("", Font.BOLD, 50f);
	    losses.setBounds(275, 360, 300, 150);
	    
	    elims = new EightBitLabel("", Font.BOLD, 50f);
	    elims.setBounds(500, 350, 300, 150);
	    
	    rocketsFired = new EightBitLabel("", Font.BOLD, 50f);
	    rocketsFired.setBounds(800, 350, 300, 150);
	    
	    blocksDestroyed = new EightBitLabel("", Font.BOLD, 50f);
	    blocksDestroyed.setBounds(1200, 350, 300, 150);
	    
		title = new EightBitLabel("ROCKETMAN PROFILE", Font.BOLD, 175f);
		title.setBounds(95, 45, 1500, 150);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(675, 650, 250, 50);
		
		logoutButton = new EightBitButton("Logout");
		logoutButton.setBounds(675, 725, 250, 50);

		
		add(title);
		add(info);
		add(winsLabel);
		add(wins);
		add(lossesLabel);
		add(losses);
		add(elimLabel);
		add(elims);
		add(rocketsFiredLabel);
		add(rocketsFired);
		add(blocksDestroyedLabel);
		add(blocksDestroyed);
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
	
	public void setInfoLabels(int[] statistics, String usr) {
		info.setText("ROCKETER TAG: " +usr);
		
		wins.setText(""+statistics[0]);
		
	    losses.setText("" + statistics[1]);
	        
	    elims.setText("" + statistics[2]);
	       
	    rocketsFired.setText("" + statistics[3]);
	       
	    blocksDestroyed.setText("" + statistics[5]);
	}
}
