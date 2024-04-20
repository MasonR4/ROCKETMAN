package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controller.MenuController;
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
//	private EightBitLabel gameMode;
	private EightBitLabel playerTitleLabel;

	private EightBitButton leaveButton;
	private EightBitButton returnButton;

	//private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);

	private MenuController controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public GameOverScreen(MenuController ac) {
		controller = ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);

		title = new EightBitLabel("GAME END", Font.BOLD, (float) (222f * sizeRatio));
		title.setBounds((int) (375 * widthRatio), (int) (45 * heightRatio), (int) (850 * widthRatio), (int) (150 * heightRatio));

		playerTitleLabel = new EightBitLabel("Player Statistics", Font.PLAIN, (float) (60f * sizeRatio));
		playerTitleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		playerTitleLabel.setBounds((int) (550 * widthRatio), (int) (300 * heightRatio), (int) (500 * widthRatio), (int) (50 * heightRatio));

		statsBox = new JPanel();
		statsBox.setLayout(new GridLayout(2, 4, (int) (5 * sizeRatio), (int) (5 * sizeRatio)));
		statsBox.setBackground(new Color(170, 170, 170));

		statsScrollPane = new JScrollPane(statsBox, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		statsScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		statsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		statsScrollPane.setBorder(BorderFactory.createDashedBorder(Color.BLACK, (float) (8f * sizeRatio), (int) (8 * sizeRatio))); // Scaling the border might require creating a custom border that adjusts with sizeRatio
		statsScrollPane.setBounds((int) (200 * widthRatio), (int) (375 * heightRatio), (int) (1210 * widthRatio), (int) (410 * heightRatio));

		leaveButton = new EightBitButton("Leave Game");
		leaveButton.setActionCommand("LEAVE");
		leaveButton.setBounds((int) (1000 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		returnButton = new EightBitButton("Back to Lobby");
		returnButton.setActionCommand("GO_LOBBY");
		returnButton.setBounds((int) (350 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		add(title);
		add(statsScrollPane);
		add(leaveButton);
		add(returnButton);
		add(playerTitleLabel);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}

	public void setEndGameStats(EndGameData e, String username) {
		int fill = 8;
		int rank = 1;

		for (Entry<String, PlayerStatistics> stats : e.getStats().entrySet()) {
			PlayerEndGameStatsBox statBox = new PlayerEndGameStatsBox(rank, stats.getValue(), e.getPlayers().get(stats.getKey()).getColor(), controller.getHeightRatio(), controller.getWidthRatio(), controller.getSizeRatio());
			if (stats.getKey().equals(username)) {
				statBox.isYou();
			}
			if (stats.getValue().getStat("deaths") >= 1) {
				statBox.isDead();
			}
			SwingUtilities.invokeLater(() -> {
				statsBox.add(statBox);
			});
			rank++;
		}
		for (int i = 0; i < (fill - rank) + 1; i++) {
			JPanel filler = new JPanel();
			filler.setPreferredSize(new Dimension((int) (290 * sizeRatio), (int) (390 * sizeRatio)));
			filler.setOpaque(false);
			SwingUtilities.invokeLater(() -> {
				statsBox.add(filler);
			});
		}
		SwingUtilities.invokeLater(() -> {
			statsBox.revalidate();
			statsBox.repaint();
			statsScrollPane.revalidate();
			statsScrollPane.repaint();
			revalidate();
			repaint();
		});
	}

	public void reset() {
		SwingUtilities.invokeLater(() -> {
			statsBox.removeAll();
			statsScrollPane.repaint();
			statsScrollPane.revalidate();
			revalidate();
			repaint();
		});
	}
}