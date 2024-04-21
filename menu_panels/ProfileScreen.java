package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;


public class ProfileScreen extends JPanel {
	
	private static final long serialVersionUID = 1282319019099873811L;
	private EightBitLabel title;
	private EightBitLabel winsLabel;
	private EightBitLabel wins;
	private EightBitLabel elimLabel;
	private EightBitLabel elims;
	private EightBitLabel damageLabel;
	private EightBitLabel damage;
	private EightBitLabel deathsLabel;
	private EightBitLabel deaths;
	private EightBitLabel lossesLabel;
	private EightBitLabel losses;
	private EightBitLabel rocketsFiredLabel;
	private EightBitLabel rocketsFired;
	private EightBitLabel blocksDestroyedLabel;
	private EightBitLabel blocksDestroyed;
	private EightBitLabel info;
	
	private EightBitLabel winLossLabel;
	private EightBitLabel winLoss;
	
	private EightBitLabel KDLabel;
	private EightBitLabel theOnlyStatThatMatters;
	
	private EightBitLabel accuracyLabel;
	private EightBitLabel accuracy;
	
	private EightBitLabel totalScore;
	
	DecimalFormat DF = new DecimalFormat("#.##");
	
	private EightBitButton backButton;
	private EightBitButton logoutButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	
	public ProfileScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN PROFILE", Font.BOLD, 125f);
		title.setBounds(150, 10, 1300, 75);
		
		info = new EightBitLabel("ROCKETER TAG: xXyour-mom69Xx", Font.PLAIN, 75f);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(450, 100, 800, 50);
		
		winsLabel = new EightBitLabel ("Wins:", Font.PLAIN, 50f);
		winsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		winsLabel.setBounds(150, 500, 150, 50);
		
		lossesLabel = new EightBitLabel ("Losses:", Font.PLAIN, 50f);
		lossesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lossesLabel.setBounds(150, 550, 150, 50);
		
		wins = new EightBitLabel("9999", Font.BOLD, 50f);
		wins.setBounds(300, 500, 150, 50);
		
	    losses = new EightBitLabel("9999", Font.BOLD, 50f);
	    losses.setBounds(300, 550, 150, 50);
		
	    winLossLabel = new EightBitLabel("W/L", Font.PLAIN, 50f);
	    winLossLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    winLossLabel.setBounds(150, 400, 300, 50);
		
	    winLoss = new EightBitLabel("100.00%", Font.BOLD, 75f);
	    winLoss.setHorizontalAlignment(SwingConstants.CENTER);
	    winLoss.setBounds(150, 350, 300, 50);
	    
		elimLabel = new EightBitLabel ("Eliminations:", Font.PLAIN, 50f);
		elimLabel.setHorizontalAlignment(SwingConstants.LEFT);
		elimLabel.setBounds(600, 500, 250, 50);
		
		deathsLabel = new EightBitLabel("Deaths:", Font.PLAIN, 50f);
		deathsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		deathsLabel.setBounds(600, 550, 250, 50);		
		
	    elims = new EightBitLabel("9999", Font.BOLD, 50f);
	    elims.setBounds(850, 500, 150, 50);
		
		deaths = new EightBitLabel("9999", Font.BOLD, 50f);
		deaths.setBounds(850, 550, 150, 50);
	    
		KDLabel = new EightBitLabel("KD", Font.PLAIN, 50f);
		KDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		KDLabel.setBounds(600, 400, 400, 50);
		
		totalScore = new EightBitLabel("Total Score: 999999", Font.PLAIN, 50f);
		totalScore.setHorizontalAlignment(SwingConstants.CENTER);
		totalScore.setBounds(600, 175, 400, 50);
		
		theOnlyStatThatMatters = new EightBitLabel("69.42", Font.BOLD, 75f);
		theOnlyStatThatMatters.setHorizontalAlignment(SwingConstants.CENTER);
		theOnlyStatThatMatters.setBounds(600, 350, 400, 50);
		
		rocketsFiredLabel = new EightBitLabel ("Rockets Fired:", Font.PLAIN, 50f);
		rocketsFiredLabel.setHorizontalAlignment(SwingConstants.LEFT);
		rocketsFiredLabel.setBounds(1150, 500, 275, 50);
		
		blocksDestroyedLabel = new EightBitLabel ("Blocks Broken:", Font.PLAIN, 50f);
		blocksDestroyedLabel.setHorizontalAlignment(SwingConstants.LEFT);
		blocksDestroyedLabel.setBounds(1150, 550, 275, 50);
	    
		damageLabel = new EightBitLabel("Damage Dealt:", Font.PLAIN, 50f);
		damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		damageLabel.setBounds(1150, 600, 275, 50);
		
		damage = new EightBitLabel("9999", Font.BOLD, 50f);
		damage.setBounds(1375, 600, 250, 50);
		
	    rocketsFired = new EightBitLabel("9999", Font.BOLD, 50f);
	    rocketsFired.setBounds(1375, 500, 250, 50);
	    
	    blocksDestroyed = new EightBitLabel("9999", Font.BOLD, 50f);
	    blocksDestroyed.setBounds(1375, 550, 250, 50);
	    
	    accuracyLabel = new EightBitLabel("Accuracy", Font.PLAIN, 50f);
	    accuracyLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    accuracyLabel.setBounds(1150, 400, 400, 50);
	    
	    accuracy = new EightBitLabel("100.00%", Font.BOLD, 75f);
	    accuracy.setHorizontalAlignment(SwingConstants.CENTER);
	    accuracy.setBounds(1150, 350, 400, 50);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(675, 700, 250, 50);
		
		logoutButton = new EightBitButton("Logout");
		logoutButton.setBounds(675, 775, 250, 50);
		
		
		add(winLossLabel);
		add(winLoss);
		
		add(deathsLabel);
		add(deaths);
		
		add(KDLabel);
		add(theOnlyStatThatMatters);
		
		add(damageLabel);
		add(damage);
		
		add(accuracyLabel);
		add(accuracy);
		
		add(totalScore);
		
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
	
	public void setInfoLabels(int[] stats, String usr) {
		System.out.println("setting stats");
		info.setText("ROCKETER TAG: " + usr);
		wins.setText(Integer.toString(stats[0]));
		losses.setText(Integer.toString(stats[1]));
		double wl = ((double) stats[0] / ((double) stats[1] + (double) stats[0])) * 100;
		winLoss.setText(DF.format(wl) + "%");
		elims.setText(Integer.toString(stats[2]));
		deaths.setText(Integer.toString(stats[3]));
		double kd = ((double) stats[2] / (double) stats[3]);
		theOnlyStatThatMatters.setText(DF.format(kd));
		rocketsFired.setText(Integer.toString(stats[4]));
		blocksDestroyed.setText(Integer.toString(stats[5]));
		damage.setText(Integer.toString(stats[6]));
		double acc = (((double) stats[5] + (double) stats[6]) / (double) stats[4]) * 100;
		accuracy.setText(DF.format(acc) + "%");
		totalScore.setText("Total Score: " + stats[7]);
		System.out.println("stats set");
	}
}
