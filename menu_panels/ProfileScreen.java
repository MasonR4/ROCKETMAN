package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.MenuController;
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

	private MenuController controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public ProfileScreen(MenuController ac) {
		controller = ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);

		title = new EightBitLabel("ROCKETMAN PROFILE", Font.BOLD, (float) (125f * sizeRatio));
		title.setBounds((int) (150 * widthRatio), (int) (10 * heightRatio), (int) (1300 * widthRatio), (int) (75 * heightRatio));

		info = new EightBitLabel("ROCKETER TAG: xXyour-mom69Xx", Font.PLAIN, (float) (75f * sizeRatio));
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds((int) (450 * widthRatio), (int) (100 * heightRatio), (int) (800 * widthRatio), (int) (50 * heightRatio));

		winsLabel = new EightBitLabel("Wins:", Font.PLAIN, (float) (50f * sizeRatio));
		winsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		winsLabel.setBounds((int) (150 * widthRatio), (int) (500 * heightRatio), (int) (150 * widthRatio), (int) (50 * heightRatio));

		lossesLabel = new EightBitLabel("Losses:", Font.PLAIN, (float) (50f * sizeRatio));
		lossesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lossesLabel.setBounds((int) (150 * widthRatio), (int) (550 * heightRatio), (int) (150 * widthRatio), (int) (50 * heightRatio));

		wins = new EightBitLabel("9999", Font.BOLD, (float) (50f * sizeRatio));
		wins.setBounds((int) (300 * widthRatio), (int) (500 * heightRatio), (int) (150 * widthRatio), (int) (50 * heightRatio));

		losses = new EightBitLabel("9999", Font.BOLD, (float) (50f * sizeRatio));
		losses.setBounds((int) (300 * widthRatio), (int) (550 * heightRatio), (int) (150 * widthRatio), (int) (50 * heightRatio));

		winLossLabel = new EightBitLabel("W/L", Font.PLAIN, (float) (50f * sizeRatio));
		winLossLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winLossLabel.setBounds((int) (150 * widthRatio), (int) (400 * heightRatio), (int) (300 * widthRatio), (int) (50 * heightRatio));

		winLoss = new EightBitLabel("100.00%", Font.BOLD, (float) (75f * sizeRatio));
		winLoss.setHorizontalAlignment(SwingConstants.CENTER);
		winLoss.setBounds((int) (150 * widthRatio), (int) (350 * heightRatio), (int) (300 * widthRatio), (int) (50 * heightRatio));

		elimLabel = new EightBitLabel("Eliminations:", Font.PLAIN, (float) (50f * sizeRatio));
		elimLabel.setHorizontalAlignment(SwingConstants.LEFT);
		elimLabel.setBounds((int) (600 * widthRatio), (int) (500 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		deathsLabel = new EightBitLabel("Deaths:", Font.PLAIN, (float) (50f * sizeRatio));
		deathsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		deathsLabel.setBounds((int) (600 * widthRatio), (int) (550 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		elims = new EightBitLabel("9999", Font.BOLD, (float) (50f * sizeRatio));
		elims.setBounds((int) (850 * widthRatio), (int) (500 * heightRatio), (int) (150 * widthRatio), (int) (50 * heightRatio));

		deaths = new EightBitLabel("9999", Font.BOLD, (float) (50f * sizeRatio));
		deaths.setBounds((int) (850 * widthRatio), (int) (550 * heightRatio), (int) (150 * widthRatio), (int) (50 * heightRatio));

		KDLabel = new EightBitLabel("KD", Font.PLAIN, (float) (50f * sizeRatio));
		KDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		KDLabel.setBounds((int) (600 * widthRatio), (int) (400 * heightRatio), (int) (400 * widthRatio), (int) (50 * heightRatio));

		totalScore = new EightBitLabel("Total Score: 999999", Font.PLAIN, (float) (50f * sizeRatio));
		totalScore.setHorizontalAlignment(SwingConstants.CENTER);
		totalScore.setBounds((int) (600 * widthRatio), (int) (175 * heightRatio), (int) (400 * widthRatio), (int) (50 * heightRatio));

		theOnlyStatThatMatters = new EightBitLabel("69.42", Font.BOLD, (float) (75f * sizeRatio));
		theOnlyStatThatMatters.setHorizontalAlignment(SwingConstants.CENTER);
		theOnlyStatThatMatters.setBounds((int) (600 * widthRatio), (int) (350 * heightRatio), (int) (400 * widthRatio), (int) (50 * heightRatio));

		rocketsFiredLabel = new EightBitLabel("Rockets Fired:", Font.PLAIN, (float) (50f * sizeRatio));
		rocketsFiredLabel.setHorizontalAlignment(SwingConstants.LEFT);
		rocketsFiredLabel.setBounds((int) (1150 * widthRatio), (int) (500 * heightRatio), (int) (275 * widthRatio), (int) (50 * heightRatio));

		blocksDestroyedLabel = new EightBitLabel("Blocks Broken:", Font.PLAIN, (float) (50f * sizeRatio));
		blocksDestroyedLabel.setHorizontalAlignment(SwingConstants.LEFT);
		blocksDestroyedLabel.setBounds((int) (1150 * widthRatio), (int) (550 * heightRatio), (int) (275 * widthRatio), (int) (50 * heightRatio));

		damageLabel = new EightBitLabel("Damage Dealt:", Font.PLAIN, (float) (50f * sizeRatio));
		damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		damageLabel.setBounds((int) (1150 * widthRatio), (int) (600 * heightRatio), (int) (275 * widthRatio), (int) (50 * heightRatio));

		damage = new EightBitLabel("9999", Font.BOLD, (float) (50f * sizeRatio));
		damage.setHorizontalAlignment(SwingConstants.RIGHT);
		damage.setBounds((int) (1375 * widthRatio), (int) (600 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		rocketsFired = new EightBitLabel("9999", Font.BOLD, (float) (50f * sizeRatio));
		rocketsFired.setHorizontalAlignment(SwingConstants.RIGHT);
		rocketsFired.setBounds((int) (1375 * widthRatio), (int) (500 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		blocksDestroyed = new EightBitLabel("9999", Font.BOLD, (float) (50f * sizeRatio));
		blocksDestroyed.setHorizontalAlignment(SwingConstants.RIGHT);
		blocksDestroyed.setBounds((int) (1375 * widthRatio), (int) (550 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		accuracyLabel = new EightBitLabel("Accuracy", Font.PLAIN, (float) (50f * sizeRatio));
		accuracyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		accuracyLabel.setBounds((int) (1150 * widthRatio), (int) (400 * heightRatio), (int) (400 * widthRatio), (int) (50 * heightRatio));

		accuracy = new EightBitLabel("100.00%", Font.BOLD, (float) (75f * sizeRatio));
		accuracy.setHorizontalAlignment(SwingConstants.CENTER);
		accuracy.setBounds((int) (1150 * widthRatio), (int) (350 * heightRatio), (int) (400 * widthRatio), (int) (50 * heightRatio));

		backButton = new EightBitButton("Back");
		backButton.setBounds((int) (675 * widthRatio), (int) (700 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		logoutButton = new EightBitButton("Logout");
		logoutButton.setBounds((int) (675 * widthRatio), (int) (775 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

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
		double wl = (stats[0] / ((double) stats[1] + (double) stats[0])) * 100;
		winLoss.setText(DF.format(wl) + "%");
		elims.setText(Integer.toString(stats[2]));
		deaths.setText(Integer.toString(stats[3]));
		double kd = ((double) stats[2] / (double) stats[3]);
		theOnlyStatThatMatters.setText(DF.format(kd));
		rocketsFired.setText(Integer.toString(stats[4]));
		blocksDestroyed.setText(Integer.toString(stats[5]));
		damage.setText(Integer.toString(stats[6]));
		double acc = (((double) stats[5] + (double) stats[6]) / stats[4]) * 100;
		accuracy.setText(DF.format(acc) + "%");
		totalScore.setText("Total Score: " + stats[7]);
		System.out.println("stats set");
	}
}
