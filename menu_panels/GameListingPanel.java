package menu_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import data.NewGameData;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

public class GameListingPanel extends JPanel {
	
	private String name;
	private String host;
	
	private int playerCount;
	private int maxPlayers;
	
	private EightBitLabel lobbyName;
	private EightBitLabel playerCountLabel;
	private EightBitLabel hostedBy;
	
	private EightBitButton joinButton;
	
	private static Dimension size = new Dimension(1390, 100);
	
	public GameListingPanel() {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		name = "Lobby Name Goes Here - imagine if this doesn't actually work LOL";
		playerCount = 0;
		maxPlayers = 6;
		host = "test_user";
		
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
	
	public GameListingPanel(NewGameData info) {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		name = info.getName();
		maxPlayers = info.getMaxPlayers();
		host = info.getHostName();
		
		lobbyName = new EightBitLabel(name, Font.PLAIN, 33f);
		lobbyName.setHorizontalAlignment(SwingConstants.LEFT);
		lobbyName.setBounds(20, 25, 800, 40);
		
		playerCountLabel = new EightBitLabel(Integer.toString(playerCount) + "/" + Integer.toString(maxPlayers), Font.PLAIN, 33f);
		playerCountLabel.setBounds(1000, 25, 40, 40);
		
		joinButton = new EightBitButton("Join +");
		joinButton.setBounds(1100, 25, 200, 50);
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
