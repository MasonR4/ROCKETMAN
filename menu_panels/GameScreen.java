package menu_panels;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameScreen extends JLayeredPane {
	
	private JPanel gamePanel;
	
	private JTextArea log;
	private JScrollPane logScrollPane;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	ActionListener controller;
	
	public GameScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBounds(340, 0, 900, 900);
		gamePanel.setBorder(BorderFactory.createEtchedBorder());
		
		add(gamePanel);
	}
	
	public JPanel getGamePanel() {
		return gamePanel;
	}
		
	public void setController(ActionListener c) {
		controller = c;
	}
}
