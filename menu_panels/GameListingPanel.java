package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import data.GameLobbyData;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

public class GameListingPanel extends JPanel {
	
	private String name;
	private String host;
	
	private int playerCount;
	private int maxPlayers;
	private int gameID;
	
	private EightBitLabel lobbyName;
	private EightBitLabel playerCountLabel;
	private EightBitLabel hostedBy;
	
	private EightBitButton joinButton;
	
	private static Dimension size = new Dimension(1398, 100);
	private ActionListener controller;
	
	public GameListingPanel(String n, String hn, int mp, int gid) {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		name = n;
		playerCount = 0; // TODO how do we find this IDK
		maxPlayers = mp;
		gameID = gid;
		host = hn;
		
		lobbyName = new EightBitLabel(name, Font.PLAIN, 33f);
		lobbyName.setHorizontalAlignment(SwingConstants.LEFT);
		lobbyName.setBounds(20, 25, 800, 40);
		
		hostedBy = new EightBitLabel("Host: " + host, Font.PLAIN, 33f);
		hostedBy.setHorizontalAlignment(SwingConstants.LEFT);
		hostedBy.setBounds(830, 25, 200, 40);
		
		playerCountLabel = new EightBitLabel(Integer.toString(playerCount) + "/" + Integer.toString(maxPlayers), Font.PLAIN, 33f);
		playerCountLabel.setBounds(1030, 25, 40, 40);
		
		joinButton = new EightBitButton("Join +");
		joinButton.setBounds(1130, 25, 200, 50);
		
		add(lobbyName);
		add(hostedBy);
		add(playerCountLabel);
		add(joinButton);
	}
	
	public GameListingPanel(GameLobbyData info) {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		name = info.getName();
		maxPlayers = info.getMaxPlayers();
		host = info.getHostName();
		gameID = info.getGameID();
		
		lobbyName = new EightBitLabel(name, Font.PLAIN, 33f);
		lobbyName.setHorizontalAlignment(SwingConstants.LEFT);
		lobbyName.setBounds(20, 25, 800, 40);
		
		hostedBy = new EightBitLabel("Host: " + host, Font.PLAIN, 33f);
		hostedBy.setHorizontalAlignment(SwingConstants.LEFT);
		hostedBy.setBounds(830, 25, 200, 40);
		
		playerCountLabel = new EightBitLabel(Integer.toString(playerCount) + "/" + Integer.toString(maxPlayers), Font.PLAIN, 33f);
		playerCountLabel.setBounds(1030, 25, 40, 40);
		
		joinButton = new EightBitButton("Join +");
		joinButton.setBounds(1130, 25, 200, 50);
		
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