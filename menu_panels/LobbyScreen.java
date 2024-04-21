package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
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
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	private ActionListener controller;
	
	public LobbyScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		lobbyName = new EightBitLabel("Lobby Name", Font.PLAIN, 48f);
		lobbyName.setBounds(90, 25, 350, 50);
		
		hostLabel = new EightBitLabel("Hosted By: ", Font.PLAIN, 32f);
		hostLabel.setBounds(510, 40, 600, 20);
		
		playerCountLabel = new EightBitLabel("Players: 0/0", Font.PLAIN, 32f);
		playerCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		playerCountLabel.setBounds(1000, 40, 400, 20);
		
		readyStatusLabel = new EightBitLabel("", Font.PLAIN, 22f);
		readyStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		readyStatusLabel.setBounds(1200, 815, 250, 20);
		readyStatusLabel.setForeground(Color.RED);
		
		gameInfoPanel = new JPanel();
		gameInfoPanel.setLayout(null);
		gameInfoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		gameInfoPanel.setBounds(1150, 75, 400, 700);
		
		gameInfoTitleLabel = new EightBitLabel("Game Settings", Font.PLAIN, 48f);
		gameInfoTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameInfoTitleLabel.setBounds(50, 10, 275, 30);
		
		mapLabel = new EightBitLabel("Map", Font.PLAIN, 38f);
		mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mapLabel.setBounds(75, 65, 250, 20);
		
		map = new EightBitLabel("default", Font.PLAIN, 32f);
		map.setHorizontalAlignment(SwingConstants.CENTER);
		map.setBounds(75, 105, 250, 20);
		
		mapRight = new EightBitButton(">");
		mapRight.setActionCommand("MAP+");
		mapRight.setBounds(335, 100, 40, 30);
		
		mapLeft = new EightBitButton("<");
		mapLeft.setActionCommand("MAP-");
		mapLeft.setBounds(25, 100, 40, 30);
		
		gameModeLabel = new EightBitLabel("Gamemode", Font.PLAIN, 38f);
		gameModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameModeLabel.setBounds(75, 150, 250, 20);
		
		gameMode = new EightBitLabel("Free For All", Font.PLAIN, 32f);
		gameMode.setHorizontalAlignment(SwingConstants.CENTER);
		gameMode.setBounds(75, 195, 250, 20);
		
		livesLabel = new EightBitLabel("Player Health", Font.PLAIN, 38f);
		livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		livesLabel.setBounds(75, 235, 250, 20);
		
		lives = new EightBitLabel("3", Font.PLAIN, 32f);
		lives.setHorizontalAlignment(SwingConstants.CENTER);
		lives.setBounds(75, 280, 250, 20);
		
		livesRight = new EightBitButton(">");
		livesRight.setActionCommand("LIVES+");
		livesRight.setBounds(335, 275, 40, 30);
		
		livesLeft = new EightBitButton("<");
		livesLeft.setActionCommand("LIVES-");
		livesLeft.setBounds(25, 275, 40, 30);
		
		chatLabel = new EightBitLabel("- Chat -", Font.PLAIN, 32f);
		chatLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chatLabel.setBounds(5, 320, 390, 20);
		
		chatArea = new JTextArea(5, 30);
		chatArea.setEditable(false);
		chatArea.setBounds(5, 350, 390, 325);
		chatArea.setFont(readyStatusLabel.getFont());
		chatArea.setOpaque(false);
		
		chatScrollPane = new JScrollPane(chatArea);
		chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		chatScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		chatScrollPane.setBounds(5, 350, 390, 325);
		chatScrollPane.setBorder(BorderFactory.createEmptyBorder());
		chatScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (!e.getValueIsAdjusting()) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		
		chat = new JTextField(32);
		chat.setFont(lives.getFont());
		chat.setBounds(5, 675, 390, 20);
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
		playersPanel.setBounds(90, 75, 1000, 700);
		
		playerScrollPane = new JScrollPane(playersPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		playerScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		playerScrollPane.setBounds(90, 75, 1000, 700);
		
		readyButton = new EightBitButton("Ready");
		readyButton.setBounds(90, 800, 250, 50);
		
		leaveButton = new EightBitButton("Leave");
		leaveButton.setBounds(910, 800, 250, 50);
		
		startGameButton = new EightBitButton("Start Game");
		startGameButton.setBounds(365, 800, 250, 50);
		
		add(lobbyName);
		add(hostLabel);
		add(gameInfoPanel);
		add(playerCountLabel);
		add(readyStatusLabel);
		add(playerScrollPane);
		add(readyButton);
		add(leaveButton);
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
	
	public void setController(ActionListener ac) {
		controller = ac;
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}

	public boolean hasHostControls() {
		return hostControls;
	}

	public void setHostControls(boolean hostControls) {
		this.hostControls = hostControls;
	}
	
}
