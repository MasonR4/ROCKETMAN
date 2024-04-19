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
	//private EightBitLabel deaths;
	private EightBitLabel rocketsFired;
	private EightBitLabel blocksDestroyed;
	private EightBitLabel score;
	
	public PlayerEndGameStatsBox(int n, PlayerStatistics p, Color c) {
		setLayout(null);
		setPreferredSize(new Dimension(290, 390));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		color = c;
		
		name = new EightBitLabel("fortnite-balls", Font.PLAIN, 32f);
		name.setText(p.getUsername());
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setForeground(color);
		name.setBounds(50, 10, 250, 20);
	
		number = new EightBitLabel("##", Font.PLAIN, 32f);
		number.setText("#" + n);
		number.setBounds(10, 10, 40, 20);
		if (n == 1) {
			number.setForeground(new Color(240, 180, 17));
		} else if (n == 2) {
			number.setForeground(new Color(166, 166, 166));
		} else if (n == 3) {
			number.setForeground(new Color(145, 62, 2));
		}
		
		scoreLabel = new EightBitLabel("Score: ", Font.PLAIN, 24f);
		scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		scoreLabel.setBounds(20, 50, 100, 20);
		
		score = new EightBitLabel("999999", Font.BOLD, 28f);
		score.setText(Integer.toString(p.getScore()));
		score.setHorizontalAlignment(SwingConstants.RIGHT);
		score.setBounds(200, 50, 80, 20);
		
		elimLabel = new EightBitLabel("Eliminations: ", Font.PLAIN, 24f);
		elimLabel.setHorizontalAlignment(SwingConstants.LEFT);
		elimLabel.setBounds(20, 90, 180, 20);
		
		eliminations = new EightBitLabel("999999", Font.BOLD, 28f);
		eliminations.setText(Integer.toString(p.getStat("eliminations")));
		eliminations.setHorizontalAlignment(SwingConstants.RIGHT);
		eliminations.setBounds(200, 90, 80, 20);
		
		damageLabel = new EightBitLabel("Damage Dealt: ", Font.PLAIN, 24f);
		damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		damageLabel.setBounds(20, 130, 170, 20);
		
		damage = new EightBitLabel("99999", Font.BOLD, 28f);
		damage.setHorizontalAlignment(SwingConstants.RIGHT);
		damage.setText(Integer.toString(p.getStat("damageDealt")));
		damage.setBounds(200, 130, 80, 20);
		
		blocksLabel = new EightBitLabel("Blocks Destroyed: ", Font.PLAIN, 24f);
		blocksLabel.setHorizontalAlignment(SwingConstants.LEFT);
		blocksLabel.setBounds(20, 170, 170, 20);
		
		blocksDestroyed = new EightBitLabel("999999", Font.BOLD, 28f);
		blocksDestroyed.setText(Integer.toString(p.getStat("blocksDestroyed")));
		blocksDestroyed.setHorizontalAlignment(SwingConstants.RIGHT);
		blocksDestroyed.setBounds(200, 170, 80, 20);
		
		rocketsLabel = new EightBitLabel("Rockets Fired: ", Font.PLAIN, 24f);
		rocketsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		rocketsLabel.setBounds(20, 210, 170, 20);
		
		rocketsFired = new EightBitLabel("999999", Font.BOLD, 28f);
		rocketsFired.setText(Integer.toString(p.getStat("rocketsFired")));
		rocketsFired.setHorizontalAlignment(SwingConstants.RIGHT);
		rocketsFired.setBounds(200, 210, 80, 20);
		
		deadLabel = new EightBitLabel("", Font.PLAIN, 24f);
		deadLabel.setBounds(20, 250, 250, 20);
		
//		deathsLabel = new EightBitLabel("Deaths: ", Font.PLAIN, 24f);
//		deathsLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		deathsLabel.setBounds(20, 210, 170, 20);
//		
//		deaths = new EightBitLabel("999999", Font.BOLD, 28f);
//		deaths.setText(Integer.toString(p.getStat("deaths")));
//		deaths.setHorizontalAlignment(SwingConstants.RIGHT);
//		deaths.setBounds(200, 210, 80, 20);
		
		you = new EightBitLabel("", Font.PLAIN, 40f);
		you.setHorizontalAlignment(SwingConstants.CENTER);
		you.setBounds(130, 300, 60, 60);
		
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
		//add(deathsLabel);
		//add(deaths);
		add(you);
	}
	
	public void isDead() {
		deadLabel.setText("<html><font color = '#750d0d'>eliminated</font>");
	}
	
	public void isYou() {
		you.setText("YOU");
	}
}
