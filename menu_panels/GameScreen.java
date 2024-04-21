package menu_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import controller.MenuController;
import menu_utilities.EightBitLabel;
import menu_utilities.GameDisplay;

public class GameScreen extends JLayeredPane {

	private GameDisplay gamePanel;

	private EightBitLabel username;
	private EightBitLabel randomLabel;

	private EightBitLabel keybinds;
	private EightBitLabel mouse;
	private EightBitLabel chatKey;

	private JTextField chat;

	private JPanel logPanel;
	private JScrollPane logScrollPane;

	private JPanel healthPanel;

	private static final long serialVersionUID = 1L;

	MenuController controller;

	MouseListener mc;
	MouseMotionListener mmc;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public GameScreen(MenuController ac) {
		controller = ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);

		username = new EightBitLabel("i put the username here", Font.PLAIN, (float) (25f * sizeRatio));
		username.setBounds((int) (10 * widthRatio), (int) (200 * heightRatio), (int) (300 * widthRatio), (int) (50 * heightRatio));

		randomLabel = new EightBitLabel("this is a label", Font.PLAIN, (float) (25f * sizeRatio));
		randomLabel.setBounds((int) (10 * widthRatio), (int) (250 * heightRatio), (int) (300 * widthRatio), (int) (50 * heightRatio));

		gamePanel = new GameDisplay(heightRatio, widthRatio, sizeRatio);
		gamePanel.setLayout(null);
		gamePanel.setBounds((int) (340 * widthRatio), 0, (int) (900 * widthRatio), (int) (900 * heightRatio));

		logPanel = new JPanel();
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.PAGE_AXIS));
		logScrollPane = new JScrollPane(logPanel);
		logScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		logScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		logScrollPane.setBounds((int) (1240 * widthRatio), (int) (450 * heightRatio), (int) (350 * widthRatio), (int) (400 * heightRatio));
		logScrollPane.setBorder(BorderFactory.createEmptyBorder());
		logScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		        e.getAdjustable().setValue(e.getAdjustable().getMaximum());
		    }
		});

		chat = new JTextField(32);
		chat.setFont(username.getFont());
		chat.setText("Press Enter to chat...");
		chat.setForeground(Color.GRAY);
		chat.setBounds((int) (1245 * widthRatio), (int) (860 * heightRatio), (int) (330 * widthRatio), (int) (20 * heightRatio));

		healthPanel = new JPanel();
		healthPanel.setLayout(new BoxLayout(healthPanel, BoxLayout.Y_AXIS));
		healthPanel.setBounds((int) (1240 * widthRatio), (int) (5 * heightRatio), (int) (330 * widthRatio), (int) (440 * heightRatio));

		keybinds = new EightBitLabel("WASD - Move", Font.PLAIN, (float) (25f * sizeRatio));
		keybinds.setHorizontalAlignment(SwingConstants.LEFT);
		keybinds.setBounds((int) (10 * widthRatio), (int) (800 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));

		mouse = new EightBitLabel("Mouse1 - Fire Rocket", Font.PLAIN, (float) (25f * sizeRatio));
		mouse.setHorizontalAlignment(SwingConstants.LEFT);
		mouse.setBounds((int) (10 * widthRatio), (int) (820 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));

		chatKey = new EightBitLabel("Enter - Open Chat", Font.PLAIN, (float) (25f * sizeRatio));
		chatKey.setHorizontalAlignment(SwingConstants.LEFT);
		chatKey.setBounds((int) (10 * widthRatio), (int) (840 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));

		add(gamePanel, 1);
		add(username);
		add(randomLabel);
		add(logScrollPane, 2);
		add(chat);
		add(healthPanel);
		add(keybinds);
		add(mouse);
		add(chatKey);
	}

	public GameDisplay getGamePanel() {
		return gamePanel;
	}

	public JTextField getChat() {
		return chat;
	}

	public void setUsername(String msg) {
		username.setText(msg);
	}

	public void setRandomLabel(String msg) {
		randomLabel.setText(msg);
	}

	public JPanel getHealthPanel() {
		return healthPanel;
	}

	public void reset() {
		logPanel.removeAll();
		healthPanel.removeAll();
		revalidate();
		repaint();
	}

	public void addLogMessage(String msg) {
		EightBitLabel m = new EightBitLabel(msg, Font.PLAIN, (float) (25f * sizeRatio));
		m.setHorizontalAlignment(SwingConstants.LEFT);
		logPanel.add(m);
	}

	public void addLogMessage(EightBitLabel msg) {
		logPanel.add(msg);
	}

	public void setController(MenuController c) {
		addMouseMotionListener((MouseMotionListener) c);
		addMouseListener((MouseListener) c);
	}
}
