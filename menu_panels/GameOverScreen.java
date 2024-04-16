package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import data.EndGameData;
import data.PlayerStatistics;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;
import menu_utilities.PlayerEndGameStatsBox;

public class GameOverScreen extends JPanel {
	
	private static final long serialVersionUID = -3090211630056380263L;
	
	private JPanel statsBox;
	private JScrollPane statsScrollPane;
	
	private EightBitLabel title;
	private EightBitLabel gameMode;
	private EightBitLabel playerTitleLabel;
	
	private EightBitButton leaveButton;
	private EightBitButton returnButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	private ActionListener controller;
	
	public GameOverScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("GAME END", Font.BOLD, 222f);
		title.setBounds(375, 45, 850, 150);
		
		playerTitleLabel = new EightBitLabel("Players", Font.PLAIN, 60f);
		playerTitleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		playerTitleLabel.setBounds(700, 325, 200, 50);
		
		statsBox = new JPanel();
		statsBox.setLayout(new GridLayout(2, 4, 5, 5));
		statsBox.setBackground(new Color(170, 170, 170));
		
		statsScrollPane = new JScrollPane(statsBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		statsScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		statsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		statsScrollPane.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 8f, 8));
		statsScrollPane.setBounds(200, 375, 1210, 410);
		
		leaveButton = new EightBitButton("Leave Game");
		leaveButton.setActionCommand("LEAVE");
		leaveButton.setBounds(1000, 800, 250, 50);
		
		returnButton = new EightBitButton("Back to Lobby");
		returnButton.setActionCommand("GO_LOBBY");
		returnButton.setBounds(350, 800, 250, 50);
		
		add(title);
		add(statsScrollPane);
		add(leaveButton);
		add(returnButton);
		add(playerTitleLabel);
	}
	
	public void setEndGameStats(EndGameData e) {
		int fill = 8;
		int rank = 1;
		for (Entry<String, PlayerStatistics> stats : e.getStats().entrySet()) {
			PlayerEndGameStatsBox statBox = new PlayerEndGameStatsBox(rank, stats.getValue(), e.getPlayers().get(stats.getKey()).getColor());
			statsBox.add(statBox);
			rank++;
		}
		for (int i = 0; i < (fill - rank) + 1; i++) {
			JPanel filler = new JPanel();
			filler.setPreferredSize(new Dimension(290, 390));
			filler.setOpaque(false);
			statsBox.add(filler);
		}
		repaint();
		revalidate();
	}
	
	public void reset() {
		statsBox.removeAll();
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