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
import menu_utilities.PlayerListingPanel;

public class LobbyScreen extends JPanel {
	
	
	private EightBitLabel lobbyName;
	private EightBitLabel hostLabel;
	private EightBitLabel playerCountLabel;
	private EightBitLabel readyStatusLabel;
	
	private EightBitButton readyButton;
	private EightBitButton startGameButton;
	private EightBitButton leaveButton;
	
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
		
		readyStatusLabel = new EightBitLabel("", Font.PLAIN, 32f);
		readyStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		readyStatusLabel.setForeground(Color.RED);
		
		gameInfoPanel = new JPanel();
		gameInfoPanel.setLayout(null);
		gameInfoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		gameInfoPanel.setBounds(1150, 75, 400, 700);
		
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
		startGameButton.setBounds(365, 800, 250, 40);
		
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
		
	}
	
	public JPanel getPlayerPanel() {
		return playersPanel;
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
