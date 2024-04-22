package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.MenuController;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;
import menu_utilities.GameCreationPanel;

public class FindGameScreen extends JLayeredPane {

	private EightBitLabel title;
	private EightBitLabel info;
	private EightBitLabel serverInfo;
	private EightBitLabel errorLabel;

	private EightBitButton newGameButton;
	private EightBitButton joinGameButton;
	private EightBitButton backButton;
	private EightBitButton refreshButton;

	private JPanel gamesPanel;
	private JScrollPane gameScrollPane;

	private GameCreationPanel newGamePanel;

	private static final long serialVersionUID = 1L;

	private MenuController controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public FindGameScreen(MenuController ac) {
		controller = ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);

		title = new EightBitLabel("Join or Create Game", Font.PLAIN, (float) (48f * sizeRatio));
		title.setBounds((int) (90 * widthRatio), (int) (25 * heightRatio), (int) (350 * widthRatio), (int) (50 * heightRatio));

		info = new EightBitLabel("Logged in as: ", Font.PLAIN, (float) (32f * sizeRatio));
		info.setBounds((int) (510 * widthRatio), (int) (40 * heightRatio), (int) (600 * widthRatio), (int) (20 * heightRatio));

		serverInfo = new EightBitLabel("Connected to: ", Font.PLAIN, (float) (32f * sizeRatio));
		serverInfo.setBounds((int) (1000 * widthRatio), (int) (40 * heightRatio), (int) (400 * widthRatio), (int) (20 * heightRatio));

		errorLabel = new EightBitLabel("", Font.PLAIN, (float) (18f * sizeRatio));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.LEFT);
		errorLabel.setBounds((int) (1175 * widthRatio), (int) (800 * heightRatio), (int) (400 * widthRatio), (int) (50 * heightRatio));

		gamesPanel = new JPanel();
		gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.PAGE_AXIS));
		gamesPanel.setBounds((int) (90 * widthRatio), (int) (75 * heightRatio), (int) (1400 * widthRatio), (int) (700 * heightRatio));

		gameScrollPane = new JScrollPane(gamesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		gameScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		gameScrollPane.setBounds((int) (90 * widthRatio), (int) (75 * heightRatio), (int) (1400 * widthRatio), (int) (700 * heightRatio));

		newGamePanel = new GameCreationPanel(controller);
		newGamePanel.setVisible(false);
		newGamePanel.setBounds((int) (400 * widthRatio), (int) (125 * heightRatio), (int) (800 * widthRatio), (int) (600 * heightRatio));

		newGameButton = new EightBitButton("New Game");
		newGameButton.setBounds((int) (90 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		joinGameButton = new EightBitButton("Join Game");
		joinGameButton.setBounds((int) (365 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		refreshButton = new EightBitButton("Refresh");
		refreshButton.setBounds((int) (640 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		backButton = new EightBitButton("Back");
		backButton.setBounds((int) (910 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		add(title, 1);
		add(info, 1);
		add(serverInfo, 1);
		add(newGamePanel, 0);
		add(gameScrollPane, 1);
		add(newGameButton, 1);
		add(joinGameButton, 1);
		add(refreshButton, 1);
		add(backButton, 1);
		add(errorLabel, 1);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}

	public void showGameCreationPanel() {
		newGamePanel.setVisible(true);
	}

	public GameCreationPanel getGameCreationPanel() {
		return newGamePanel;
	}

	public JPanel getGamesPanel() {
		return gamesPanel;
	}

	public void setError(String msg) {
		errorLabel.setText(msg);
	}

	public void setInfoLabels(String s, String usr) {
		serverInfo.setText("Connected to: " + s);
		info.setText("Logged in as: " + usr);
	}

	public void setController(ActionListener ac) {
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
		//newGamePanel.setController(ac);
	}
}
