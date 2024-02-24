package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import menu_utilities.*;

public class FindGameScreen extends JLayeredPane {
	
	private EightBitLabel title;
	private EightBitLabel info;
	private EightBitLabel serverInfo;
	private EightBitLabel errorLabel;
	
	private EightBitButton newGameButton;
	private EightBitButton joinGameButton;
	private EightBitButton backButton;
	private EightBitButton refreshButton;
	
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
		
		info = new EightBitLabel("Logged in as: ", Font.PLAIN, 32f);
		info.setBounds(510, 40, 600, 20);
		
		serverInfo = new EightBitLabel("Connected to: ", Font.PLAIN, 32f);
		serverInfo.setBounds(1000, 40, 400, 20);
		
		errorLabel = new EightBitLabel("", Font.PLAIN, 18f);
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.LEFT);
		errorLabel.setBounds(1175, 800, 400, 50);
		
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
		
		refreshButton = new EightBitButton("Refresh");
		refreshButton.setBounds(640, 800, 250, 50);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(910, 800, 250, 50);
		
		add(title, 1);
		add(info, 1);
		add(serverInfo, 1);
		add(newGamePanel, 0);
		add(gameScrollPane, 1);
		add(newGameButton, 1);
		add(joinGameButton, 1);
		add(refreshButton, 1);
		add(backButton, 1);
		add(errorLabel, 1);
	}
	
	public void showGameCreationPanel() {
		newGamePanel.setVisible(true);
	}
	
	public GameCreationPanel getGameCreationPanel() {
		return newGamePanel;
	}
	
	public JPanel getGamesPanel() {
		return gamesPanel;
	}
	
	public void setError(String msg) {
		errorLabel.setText(msg);
	}
	
	public void setInfoLabels(String s, String usr) {
		serverInfo.setText("Connected to: " + s);
		info.setText("Logged in as: " + usr);
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
