package menu_utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import data.PlayerStatistics;

public class PlayerEndGameStatsBox extends JPanel {

	private static final long serialVersionUID = -3226863745406471890L;

	private EightBitLabel name;
	private EightBitLabel number;

	private EightBitLabel you;

	private Color color;

	private EightBitLabel elimLabel;
	private EightBitLabel damageLabel;
	private EightBitLabel deadLabel;
	private EightBitLabel rocketsLabel;
	private EightBitLabel blocksLabel;
	private EightBitLabel scoreLabel;

	private EightBitLabel eliminations;
	private EightBitLabel damage;
	private EightBitLabel rocketsFired;
	private EightBitLabel blocksDestroyed;
	private EightBitLabel score;

	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public PlayerEndGameStatsBox(int n, PlayerStatistics p, Color c, double hr, double wr, double sr) {
		heightRatio = hr;
		widthRatio = wr;
		sizeRatio = sr;
		setPreferredSize(new Dimension((int) (290 * heightRatio), (int) (390 * widthRatio)));
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) (2 * sizeRatio)));
		color = c;

		name = new EightBitLabel("fortnite-balls", Font.PLAIN, (float) (32f * sizeRatio));
		name.setText(p.getUsername());
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setForeground(color);
		name.setBounds((int) (50 * widthRatio), (int) (10 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		number = new EightBitLabel("##", Font.PLAIN, (float) (32f * sizeRatio));
		number.setText("#" + n);
		number.setBounds((int) (10 * widthRatio), (int) (10 * heightRatio), (int) (40 * widthRatio), (int) (20 * heightRatio));
		if (n == 1) {
		    number.setForeground(new Color(240, 180, 17));
		} else if (n == 2) {
		    number.setForeground(new Color(166, 166, 166));
		} else if (n == 3) {
		    number.setForeground(new Color(145, 62, 2));
		}

		scoreLabel = new EightBitLabel("Score: ", Font.PLAIN, (float) (24f * sizeRatio));
		scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		scoreLabel.setBounds((int) (20 * widthRatio), (int) (50 * heightRatio), (int) (100 * widthRatio), (int) (20 * heightRatio));

		score = new EightBitLabel("999999", Font.BOLD, (float) (28f * sizeRatio));
		score.setText(Integer.toString(p.getScore()));
		score.setHorizontalAlignment(SwingConstants.RIGHT);
		score.setBounds((int) (200 * widthRatio), (int) (50 * heightRatio), (int) (80 * widthRatio), (int) (20 * heightRatio));

		elimLabel = new EightBitLabel("Eliminations: ", Font.PLAIN, (float) (24f * sizeRatio));
		elimLabel.setHorizontalAlignment(SwingConstants.LEFT);
		elimLabel.setBounds((int) (20 * widthRatio), (int) (90 * heightRatio), (int) (180 * widthRatio), (int) (20 * heightRatio));

		eliminations = new EightBitLabel("999999", Font.BOLD, (float) (28f * sizeRatio));
		eliminations.setText(Integer.toString(p.getStat("eliminations")));
		eliminations.setHorizontalAlignment(SwingConstants.RIGHT);
		eliminations.setBounds((int) (200 * widthRatio), (int) (90 * heightRatio), (int) (80 * widthRatio), (int) (20 * heightRatio));

		damageLabel = new EightBitLabel("Damage Dealt: ", Font.PLAIN, (float) (24f * sizeRatio));
		damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		damageLabel.setBounds((int) (20 * widthRatio), (int) (130 * heightRatio), (int) (170 * widthRatio), (int) (20 * heightRatio));

		damage = new EightBitLabel("99999", Font.BOLD, (float) (28f * sizeRatio));
		damage.setHorizontalAlignment(SwingConstants.RIGHT);
		damage.setText(Integer.toString(p.getStat("damageDealt")));
		damage.setBounds((int) (200 * widthRatio), (int) (130 * heightRatio), (int) (80 * widthRatio), (int) (20 * heightRatio));

		blocksLabel = new EightBitLabel("Blocks Destroyed: ", Font.PLAIN, (float) (24f * sizeRatio));
		blocksLabel.setHorizontalAlignment(SwingConstants.LEFT);
		blocksLabel.setBounds((int) (20 * widthRatio), (int) (170 * heightRatio), (int) (170 * widthRatio), (int) (20 * heightRatio));

		blocksDestroyed = new EightBitLabel("999999", Font.BOLD, (float) (28f * sizeRatio));
		blocksDestroyed.setText(Integer.toString(p.getStat("blocksDestroyed")));
		blocksDestroyed.setHorizontalAlignment(SwingConstants.RIGHT);
		blocksDestroyed.setBounds((int) (200 * widthRatio), (int) (170 * heightRatio), (int) (80 * widthRatio), (int) (20 * heightRatio));

		rocketsLabel = new EightBitLabel("Rockets Fired: ", Font.PLAIN, (float) (24f * sizeRatio));
		rocketsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		rocketsLabel.setBounds((int) (20 * widthRatio), (int) (210 * heightRatio), (int) (170 * widthRatio), (int) (20 * heightRatio));

		rocketsFired = new EightBitLabel("999999", Font.BOLD, (float) (28f * sizeRatio));
		rocketsFired.setText(Integer.toString(p.getStat("rocketsFired")));
		rocketsFired.setHorizontalAlignment(SwingConstants.RIGHT);
		rocketsFired.setBounds((int) (200 * widthRatio), (int) (210 * heightRatio), (int) (80 * widthRatio), (int) (20 * heightRatio));

		deadLabel = new EightBitLabel("", Font.PLAIN, (float) (24f * sizeRatio));
		deadLabel.setBounds((int) (20 * widthRatio), (int) (250 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		you = new EightBitLabel("", Font.PLAIN, (float) (40f * sizeRatio));
		you.setHorizontalAlignment(SwingConstants.CENTER);
		you.setBounds((int) (130 * widthRatio), (int) (300 * heightRatio), (int) (60 * widthRatio), (int) (60 * heightRatio));

		add(name);
		add(number);
		add(scoreLabel);
		add(score);
		add(elimLabel);
		add(eliminations);
		add(damageLabel);
		add(damage);
		add(blocksLabel);
		add(blocksDestroyed);
		add(rocketsLabel);
		add(rocketsFired);
		add(deadLabel);
		add(you);
	}

	public void isDead() {
		deadLabel.setText("<html><font color = '#750d0d'>eliminated</font>");
	}

	public void isYou() {
		you.setText("YOU");
	}
}
