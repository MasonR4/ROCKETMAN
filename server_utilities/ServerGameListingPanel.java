package server_utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import data.GameLobbyData;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

@SuppressWarnings("serial")
public class ServerGameListingPanel extends JPanel {
	
	private String name;
	private String host;
	
	private int playerCount;
	private int maxPlayers;
	private int gameID;
	
	private EightBitLabel lobbyName;
	private EightBitLabel playerCountLabel;
	private EightBitLabel hostedBy;
	
	private JButton joinButton;
	
	private static Dimension size = new Dimension(712, 100);
	private ActionListener controller;
		
	public ServerGameListingPanel(GameLobbyData info) {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		name = info.getName();
		maxPlayers = info.getMaxPlayers();
		host = info.getHostName();
		gameID = info.getGameID();
		playerCount = info.getPlayerCount();
		
		lobbyName = new EightBitLabel(name, Font.PLAIN, 20f);
		lobbyName.setHorizontalAlignment(SwingConstants.LEFT);
		lobbyName.setBounds(10, 25, 300, 40);
		
		hostedBy = new EightBitLabel("Host: " + host, Font.PLAIN, 20f);
		hostedBy.setHorizontalAlignment(SwingConstants.LEFT);
		hostedBy.setBounds(325, 25, 150, 40);
		
		playerCountLabel = new EightBitLabel(Integer.toString(playerCount) + "/" + Integer.toString(maxPlayers), Font.PLAIN, 20f);
		playerCountLabel.setBounds(450, 25, 40, 40);
		
		joinButton = new JButton("Spectate");
		// TODO spectate match?!?!?!?! 
		// just draw the packets from each player on the screen before broadcasting them lul
		joinButton.setToolTipText("maybe add this later");
		joinButton.setEnabled(false);
		joinButton.setBounds(550, 25, 150, 50);
		
		add(lobbyName);
		add(hostedBy);
		add(playerCountLabel);
		add(joinButton);
	}
	
	public void setController(ActionListener ac) {
		controller = ac;
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}
	
	public int getGameID() {
		return gameID;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	@Override
	public Dimension getMinimumSize() {
	    return size;
	}

	@Override
	public Dimension getMaximumSize() {
	    return size;
	}
}
