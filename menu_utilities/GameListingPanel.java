package menu_utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.MenuController;
import data.GameLobbyData;

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

	private static final Dimension DEFAULT_SIZE = new Dimension(1396, 100);
	private Dimension size;

	private MenuController controller;

	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public GameListingPanel(GameLobbyData info, MenuController ac) {
		controller = ac;
		size = new Dimension((int) (DEFAULT_SIZE.getWidth() * controller.getWidthRatio()), (int) (DEFAULT_SIZE.getHeight() * controller.getHeightRatio()));
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		name = info.getName();
		maxPlayers = info.getMaxPlayers();
		host = info.getHostName();
		gameID = info.getGameID();
		playerCount = info.getPlayerCount();

		lobbyName = new EightBitLabel(name, Font.PLAIN, (float) (33f * sizeRatio));
		lobbyName.setHorizontalAlignment(SwingConstants.LEFT);
		lobbyName.setBounds((int) (20 * widthRatio), (int) (25 * heightRatio), (int) (800 * widthRatio), (int) (40 * heightRatio));

		hostedBy = new EightBitLabel("Host: " + host, Font.PLAIN, (float) (33f * sizeRatio));
		hostedBy.setHorizontalAlignment(SwingConstants.LEFT);
		hostedBy.setBounds((int) (830 * widthRatio), (int) (25 * heightRatio), (int) (200 * widthRatio), (int) (40 * heightRatio));

		playerCountLabel = new EightBitLabel(Integer.toString(playerCount) + "/" + Integer.toString(maxPlayers), Font.PLAIN, (float) (33f * sizeRatio));
		playerCountLabel.setBounds((int) (1030 * widthRatio), (int) (25 * heightRatio), (int) (40 * widthRatio), (int) (40 * heightRatio));

		joinButton = new EightBitButton("Join +");
		joinButton.setBounds((int) (1130 * widthRatio), (int) (25 * heightRatio), (int) (200 * widthRatio), (int) (50 * heightRatio));

		add(lobbyName);
		add(hostedBy);
		add(playerCountLabel);
		add(joinButton);
		
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
