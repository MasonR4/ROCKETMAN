package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import menu_utilities.*;

public class FindGameScreen extends JLayeredPane {
	
	private EightBitLabel title;
	private EightBitLabel info;
	private EightBitLabel noGames;
	
	private EightBitButton newGameButton;
	private EightBitButton joinGameButton;
	private EightBitButton backButton;
	
	private JPanel gamesPanel;
	private JScrollPane gameScrollPane;
	
	private GameCreationPanel newGamePanel;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	private ActionListener controller;
	
	public FindGameScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("Join or Create Game", Font.PLAIN, 48f);
		title.setBounds(90, 25, 350, 50);
		
		info = new EightBitLabel("", Font.PLAIN, 25f);
		info.setBounds(875, 815, 600, 20);
		
		noGames = new EightBitLabel("No Games Found!", Font.PLAIN, 32f);
		noGames.setPreferredSize(new Dimension(300, 20));
		
		gamesPanel = new JPanel();
		gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.PAGE_AXIS));
		gamesPanel.setBounds(90, 75, 1400, 700);		
		
		gameScrollPane = new JScrollPane(gamesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gameScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		gameScrollPane.setBounds(90, 75, 1400, 700);
		
		newGamePanel = new GameCreationPanel();
		newGamePanel.setVisible(false);
		newGamePanel.setBounds(400, 125, 800, 600);
		
		newGameButton = new EightBitButton("New Game");
		newGameButton.setBounds(90, 800, 250, 50);
		
		joinGameButton = new EightBitButton("Join Game");
		joinGameButton.setBounds(365, 800, 250, 50);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(635, 800, 250, 50);
		
		add(title, 1);
		add(info, 1);
		add(newGamePanel, 0);
		add(gameScrollPane, 1);
		add(newGameButton, 1);
		add(joinGameButton, 1);
		add(backButton, 1);
	}
	
	public void showGameCreationPanel() {
		newGamePanel.setVisible(true);
	}
	
	public GameCreationPanel getGameCreationPanel() {
		return newGamePanel;
	}
	
	public void setController(ActionListener ac) {
		controller = ac;
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
		newGamePanel.setController(ac);
	}
}
