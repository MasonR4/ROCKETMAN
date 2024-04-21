package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import controller.MenuController;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

public class LobbyScreen extends JPanel {

	private String hostUsername;
	private boolean hostControls = false;
	private int playerCount;
	private int maxPlayers;

	private EightBitLabel lobbyName;
	private EightBitLabel hostLabel;
	private EightBitLabel playerCountLabel;
	private EightBitLabel readyStatusLabel;

	private EightBitLabel gameInfoTitleLabel;
	private EightBitLabel mapLabel;
	private EightBitLabel map;
	private EightBitLabel livesLabel;
	private EightBitLabel lives;
	private EightBitLabel gameModeLabel;
	private EightBitLabel gameMode;

	private EightBitButton readyButton;
	private EightBitButton startGameButton;
	private EightBitButton leaveButton;

	private EightBitButton mapRight;
	private EightBitButton mapLeft;
	private EightBitButton livesRight;
	private EightBitButton livesLeft;

	private JPanel gameInfoPanel;
	private JPanel playersPanel;
	private JScrollPane playerScrollPane;

	private EightBitLabel chatLabel;
	private JScrollPane chatScrollPane;
	private JTextField chat;
	private JTextArea chatArea;

	private static final long serialVersionUID = 1L;
	//private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);

	private MenuController controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public LobbyScreen(MenuController ac) {
		controller = ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);

		lobbyName = new EightBitLabel("Lobby Name", Font.PLAIN, (float) (48f * sizeRatio));
		lobbyName.setBounds((int) (90 * widthRatio), (int) (25 * heightRatio), (int) (350 * widthRatio), (int) (50 * heightRatio));

		hostLabel = new EightBitLabel("Hosted By: ", Font.PLAIN, (float) (32f * sizeRatio));
		hostLabel.setBounds((int) (510 * widthRatio), (int) (40 * heightRatio), (int) (600 * widthRatio), (int) (20 * heightRatio));

		playerCountLabel = new EightBitLabel("Players: 0/0", Font.PLAIN, (float) (32f * sizeRatio));
		playerCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		playerCountLabel.setBounds((int) (1000 * widthRatio), (int) (40 * heightRatio), (int) (400 * widthRatio), (int) (20 * heightRatio));

		readyStatusLabel = new EightBitLabel("", Font.PLAIN, (float) (22f * sizeRatio));
		readyStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		readyStatusLabel.setBounds((int) (1200 * widthRatio), (int) (815 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));
		readyStatusLabel.setForeground(Color.RED);

		gameInfoPanel = new JPanel();
		gameInfoPanel.setLayout(null);
		gameInfoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		gameInfoPanel.setBounds((int) (1150 * widthRatio), (int) (75 * heightRatio), (int) (400 * widthRatio), (int) (700 * heightRatio));

		gameInfoTitleLabel = new EightBitLabel("Game Settings", Font.PLAIN, (float) (48f * sizeRatio));
		gameInfoTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameInfoTitleLabel.setBounds((int) (50 * widthRatio), (int) (10 * heightRatio), (int) (275 * widthRatio), (int) (30 * heightRatio));

		mapLabel = new EightBitLabel("Map", Font.PLAIN, (float) (38f * sizeRatio));
		mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mapLabel.setBounds((int) (75 * widthRatio), (int) (65 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		map = new EightBitLabel("default", Font.PLAIN, (float) (32f * sizeRatio));
		map.setHorizontalAlignment(SwingConstants.CENTER);
		map.setBounds((int) (75 * widthRatio), (int) (105 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		mapRight = new EightBitButton(">");
		mapRight.setActionCommand("MAP+");
		mapRight.setBounds((int) (335 * widthRatio), (int) (100 * heightRatio), (int) (40 * widthRatio), (int) (30 * heightRatio));

		mapLeft = new EightBitButton("<");
		mapLeft.setActionCommand("MAP-");
		mapLeft.setBounds((int) (25 * widthRatio), (int) (100 * heightRatio), (int) (40 * widthRatio), (int) (30 * heightRatio));

		gameModeLabel = new EightBitLabel("Gamemode", Font.PLAIN, (float) (38f * sizeRatio));
		gameModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameModeLabel.setBounds((int) (75 * widthRatio), (int) (150 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		gameMode = new EightBitLabel("Free For All", Font.PLAIN, (float) (32f * sizeRatio));
		gameMode.setHorizontalAlignment(SwingConstants.CENTER);
		gameMode.setBounds((int) (75 * widthRatio), (int) (195 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		livesLabel = new EightBitLabel("Player Health", Font.PLAIN, (float) (38f * sizeRatio));
		livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		livesLabel.setBounds((int) (75 * widthRatio), (int) (235 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		lives = new EightBitLabel("3", Font.PLAIN, (float) (32f * sizeRatio));
		lives.setHorizontalAlignment(SwingConstants.CENTER);
		lives.setBounds((int) (75 * widthRatio), (int) (280 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		livesRight = new EightBitButton(">");
		livesRight.setActionCommand("LIVES+");
		livesRight.setBounds((int) (335 * widthRatio), (int) (275 * heightRatio), (int) (40 * widthRatio), (int) (30 * heightRatio));

		livesLeft = new EightBitButton("<");
		livesLeft.setActionCommand("LIVES-");
		livesLeft.setBounds((int) (25 * widthRatio), (int) (275 * heightRatio), (int) (40 * widthRatio), (int) (30 * heightRatio));

		chatLabel = new EightBitLabel("- Chat -", Font.PLAIN, (float) (32f * sizeRatio));
		chatLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chatLabel.setBounds((int) (5 * widthRatio), (int) (320 * heightRatio), (int) (390 * widthRatio), (int) (20 * heightRatio));

		chatArea = new JTextArea(5, 30);
		chatArea.setEditable(false);
		chatArea.setBounds((int) (5 * widthRatio), (int) (350 * heightRatio), (int) (390 * widthRatio), (int) (325 * heightRatio));
		chatArea.setFont(readyStatusLabel.getFont());
		chatArea.setOpaque(false);

		chatScrollPane = new JScrollPane(chatArea);
		chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		chatScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		chatScrollPane.setBounds((int) (5 * widthRatio), (int) (350 * heightRatio), (int) (390 * widthRatio), (int) (325 * heightRatio));
		chatScrollPane.setBorder(BorderFactory.createEmptyBorder());
		chatScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		        e.getAdjustable().setValue(e.getAdjustable().getMaximum());
		    }
		});

		chat = new JTextField(32);
		chat.setFont(lives.getFont());
		chat.setBounds((int) (5 * widthRatio), (int) (675 * heightRatio), (int) (390 * widthRatio), (int) (20 * heightRatio));
		chat.setForeground(Color.GRAY);
		chat.setText("Press Enter to chat...");

		gameInfoPanel.add(chat);
		gameInfoPanel.add(chatLabel);
		gameInfoPanel.add(chatScrollPane);
		gameInfoPanel.add(gameInfoTitleLabel);
		gameInfoPanel.add(mapLabel);
		gameInfoPanel.add(map);
		gameInfoPanel.add(gameModeLabel);
		gameInfoPanel.add(gameMode);
		gameInfoPanel.add(livesLabel);
		gameInfoPanel.add(lives);

		playersPanel = new JPanel();
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.PAGE_AXIS));
		playersPanel.setBounds((int) (90 * widthRatio), (int) (75 * heightRatio), (int) (1000 * widthRatio), (int) (700 * heightRatio));

		playerScrollPane = new JScrollPane(playersPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playerScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		playerScrollPane.setBounds((int) (90 * widthRatio), (int) (75 * heightRatio), (int) (1000 * widthRatio), (int) (700 * heightRatio));

		readyButton = new EightBitButton("Ready");
		readyButton.setBounds((int) (90 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		leaveButton = new EightBitButton("Leave");
		leaveButton.setBounds((int) (910 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		startGameButton = new EightBitButton("Start Game");
		startGameButton.setBounds((int) (365 * widthRatio), (int) (800 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		add(lobbyName);
		add(hostLabel);
		add(gameInfoPanel);
		add(playerCountLabel);
		add(readyStatusLabel);
		add(playerScrollPane);
		add(readyButton);
		add(leaveButton);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}

	public void enableHostControls() {
		startGameButton.addActionListener(controller);
		mapRight.addActionListener(controller);
		mapLeft.addActionListener(controller);
		livesRight.addActionListener(controller);
		livesLeft.addActionListener(controller);
		gameInfoPanel.add(mapRight);
		gameInfoPanel.add(mapLeft);
		gameInfoPanel.add(livesRight);
		gameInfoPanel.add(livesLeft);
		add(startGameButton);
		revalidate();
		repaint();
	}

	public void disableHostControls() {
		remove(startGameButton);
		startGameButton.removeActionListener(controller);
		mapRight.removeActionListener(controller);
		mapLeft.removeActionListener(controller);
		livesRight.removeActionListener(controller);
		livesLeft.removeActionListener(controller);
		for (Component c : gameInfoPanel.getComponents()) {
			if (c instanceof EightBitButton) {
				gameInfoPanel.remove(c);
			}
		}
		gameInfoPanel.revalidate();
		gameInfoPanel.repaint();
	}

	public void unreadyReadyButton() {
		readyButton.setText("Not Ready");
		readyButton.repaint();
	}

	public void readyReadyButton() {
		readyButton.setText("Ready");
		readyButton.repaint();
	}

	public void setReadyLabel(String msg) {
		readyStatusLabel.setText(msg);
		revalidate();
		repaint();
	}

	public JPanel getPlayerPanel() {
		return playersPanel;
	}

	public EightBitLabel getMapLabel() {
		return map;
	}

	public EightBitLabel getLivesLabel() {
		return lives;
	}

	public JTextField getChat() {
		return chat;
	}

	public void chatIsThisReal(String msg) {
		chatArea.append(msg + "\n");
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
		chatScrollPane.revalidate();
	}

	public void setLobbyInfo(String h, int p, int mp) {
		hostUsername = h;
		playerCount = p;
		maxPlayers = mp;
	}

	public void setDynamicLobbyInfo(String h, int p) {
		hostUsername = h;
		playerCount = p;
	}

	public void updateLobbyInfo() {
		hostLabel.setText("Host: " + hostUsername);
		playerCountLabel.setText(Integer.toString(playerCount) + "/" + Integer.toString(maxPlayers));
		hostLabel.repaint();
		playerCountLabel.repaint();
	}

	public String getHostUsername() {
		return hostUsername;
	}

	public boolean hasHostControls() {
		return hostControls;
	}

	public void setHostControls(boolean hostControls) {
		this.hostControls = hostControls;
	}

}
