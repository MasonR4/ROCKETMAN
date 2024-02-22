package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

public class LobbyScreen extends JPanel {
	
	
	private EightBitLabel lobbyName;
	private EightBitLabel hostLabel;
	private EightBitLabel playerCountLabel;
	private EightBitLabel readyStatusLabel;
	
	private EightBitButton readyButton;
	private EightBitButton startGameButton;
	
	JPanel playersPanel;
	JScrollPane playerScrollPane;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	private ActionListener controller;
	
	public LobbyScreen() {
		setSize(DEFAULT_SIZE);
		
		lobbyName = new EightBitLabel("Lobby Name", Font.PLAIN, 48f);
		lobbyName.setBounds(90, 25, 350, 50);
		
		hostLabel = new EightBitLabel("Hosted By: ", Font.PLAIN, 32f);
		hostLabel.setBounds(510, 40, 600, 20);
		
		playerCountLabel = new EightBitLabel("Players: 0/0", Font.PLAIN, 32f);
		playerCountLabel.setBounds(1000, 40, 400, 20);
		
		readyStatusLabel = new EightBitLabel("", Font.PLAIN, 32f);
		readyStatusLabel.setForeground(Color.RED);
		
	}
	
	public void enableReadyButton() {
		add(readyButton);
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
