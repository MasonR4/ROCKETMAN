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
//	private EightBitLabel timeLabel;
//	private EightBitLabel time;
	private EightBitLabel livesLabel;
	private EightBitLabel lives;
//	private EightBitLabel reloadSpeedLabel;
//	private EightBitLabel reloadSpeed;
	private EightBitLabel gameModeLabel;
	private EightBitLabel gameMode;
	
	private EightBitButton readyButton;
	private EightBitButton startGameButton;
	private EightBitButton leaveButton;
	
	private EightBitButton mapRight;
	private EightBitButton mapLeft;
//	private EightBitButton timeRight;
//	private EightBitButton timeLeft;
	private EightBitButton livesRight;
	private EightBitButton livesLeft;
//	private EightBitButton reloadRight;
//	private EightBitButton reloadLeft;
	
	private JPanel gameInfoPanel;
	private JPanel playersPanel;
	private JScrollPane playerScrollPane;
	
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
		
//		timeRight = new EightBitButton(">");
//		timeRight.setActionCommand("TIME+");
//		timeRight.setBounds(335, 190, 40, 30);
//		
//		timeLeft = new EightBitButton("<");
//		timeLeft.setActionCommand("TIME-");
//		timeLeft.setBounds(25, 190, 40, 30);
		
		livesLabel = new EightBitLabel("Player Health", Font.PLAIN, 38f);
		livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		livesLabel.setBounds(75, 235, 250, 20);
		
		lives = new EightBitLabel("3", Font.PLAIN, 32f);
		lives.setBounds(75, 280, 250, 20);
		
		livesRight = new EightBitButton(">");
		livesRight.setActionCommand("LIVES+");
		livesRight.setBounds(335, 275, 40, 30);
		
		livesLeft = new EightBitButton("<");
		livesLeft.setActionCommand("LIVES-");
		livesLeft.setBounds(25, 275, 40, 30);
		
//		reloadSpeedLabel = new EightBitLabel("Reload Speed", Font.PLAIN, 38f);
//		reloadSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		reloadSpeedLabel.setBounds(75, 325, 250, 20);
//		
//		reloadSpeed = new EightBitLabel("Default", Font.PLAIN, 32f);
//		reloadSpeed.setHorizontalAlignment(SwingConstants.CENTER);
//		reloadSpeed.setBounds(75, 370, 250, 20);
//		
//		reloadRight = new EightBitButton(">");
//		reloadRight.setActionCommand("RELOAD+");
//		reloadRight.setBounds(335, 365, 40, 30);
//		
//		reloadLeft = new EightBitButton("<");
//		reloadLeft.setActionCommand("RELOAD-");
//		reloadLeft.setBounds(25, 365, 40, 30);
		
		gameInfoPanel.add(gameInfoTitleLabel);
		gameInfoPanel.add(mapLabel);
		gameInfoPanel.add(map);
		gameInfoPanel.add(gameModeLabel);
		gameInfoPanel.add(gameMode);
		gameInfoPanel.add(livesLabel);
		gameInfoPanel.add(lives);
		//gameInfoPanel.add(reloadSpeedLabel);
		//gameInfoPanel.add(reloadSpeed);
		
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
		//timeRight.addActionListener(controller);
		//timeLeft.addActionListener(controller);
		livesRight.addActionListener(controller);
		livesLeft.addActionListener(controller);
		//reloadRight.addActionListener(controller);
		//reloadLeft.addActionListener(controller);
		gameInfoPanel.add(mapRight);
		gameInfoPanel.add(mapLeft);
		//gameInfoPanel.add(timeRight);
		//gameInfoPanel.add(timeLeft);
		gameInfoPanel.add(livesRight);
		gameInfoPanel.add(livesLeft);
		//gameInfoPanel.add(reloadRight);
		//gameInfoPanel.add(reloadLeft);
		add(startGameButton);
		revalidate();
		repaint();
	}
	
	public void disableHostControls() {
		remove(startGameButton);
		gameInfoPanel.remove(mapRight);
		gameInfoPanel.remove(mapLeft);
		//gameInfoPanel.remove(timeRight);
		//gameInfoPanel.remove(timeLeft);
		gameInfoPanel.remove(livesRight);
		gameInfoPanel.remove(livesLeft);
		//gameInfoPanel.remove(reloadRight);
		//gameInfoPanel.remove(reloadLeft);
		revalidate();
		repaint();		
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
	
//	public EightBitLabel getTimeLabel() {
//		return time;
//	}
//	
//	public EightBitLabel getReloadLabel() {
//		return reloadSpeed;
//	}
	
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
