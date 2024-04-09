package menu_panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game_utilities.Player;
import menu_utilities.EightBitLabel;
import menu_utilities.GameDisplay;

public class GameScreen extends JLayeredPane {
	
	private GameDisplay gamePanel;
	
	private EightBitLabel username;
	
	private JTextArea log;
	private JScrollPane logScrollPane;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	
	
	ActionListener controller;
	
	public GameScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		username = new EightBitLabel("", Font.PLAIN, 25f);
		username.setBounds(10, 200, 200, 50);
		
		gamePanel = new GameDisplay();
		gamePanel.setLayout(null);
		gamePanel.setBounds(340, 0, 900, 900);
		gamePanel.setBorder(BorderFactory.createEtchedBorder());
		
		add(gamePanel, 1);
		add(username);
	}
	
	public GameDisplay getGamePanel() {
		return gamePanel;
	}
	
	
	public void setUsername(String msg) {
		username.setText(msg);
	}

	
	public void setController(ActionListener c) {
		controller = c;
	}
}
