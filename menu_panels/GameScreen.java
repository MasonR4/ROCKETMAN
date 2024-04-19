package menu_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import menu_utilities.EightBitLabel;
import menu_utilities.GameDisplay;

public class GameScreen extends JLayeredPane {
	
	private GameDisplay gamePanel;
	
	private EightBitLabel username;
	private EightBitLabel randomLabel;
	
	private EightBitLabel keybinds;
	private EightBitLabel mouse;
	private EightBitLabel chatKey;
	
	private JTextField chat;
	
	private JPanel logPanel;
	private JScrollPane logScrollPane;
	
	private JPanel healthPanel;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 930);
	
	ActionListener controller;
	
	MouseListener mc;
	MouseMotionListener mmc;
	
	public GameScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		username = new EightBitLabel("i put the username here", Font.PLAIN, 25f);
		username.setBounds(10, 200, 300, 50);
		
		randomLabel = new EightBitLabel("this is a label", Font.PLAIN, 25f);
		randomLabel.setBounds(10, 250, 300, 50);
		
		gamePanel = new GameDisplay();
		gamePanel.setLayout(null);
		gamePanel.setBounds(340, 0, 900, 900);
		
		logPanel = new JPanel();
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.PAGE_AXIS));
		logScrollPane = new JScrollPane(logPanel);
		logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		logScrollPane.setBounds(1240, 450, 350, 400);
		logScrollPane.setBorder(BorderFactory.createEmptyBorder());
		logScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (!e.getValueIsAdjusting()) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		
		chat = new JTextField(32);
		chat.setFont(username.getFont());
		chat.setText("Press Enter to chat...");
		chat.setForeground(Color.GRAY);
		chat.setBounds(1245, 860, 330, 20);
		
		healthPanel = new JPanel();
		healthPanel.setLayout(new BoxLayout(healthPanel, BoxLayout.Y_AXIS));
		healthPanel.setBounds(1240, 5, 330, 440);
		
		keybinds = new EightBitLabel("WASD - Move", Font.PLAIN, 25f);
		keybinds.setHorizontalAlignment(SwingConstants.LEFT);
		keybinds.setBounds(10, 800, 200, 20);
		
		mouse = new EightBitLabel("Mouse1 - Fire Rocket", Font.PLAIN, 25f);
		mouse.setHorizontalAlignment(SwingConstants.LEFT);
		mouse.setBounds(10, 820, 200, 20);
		
		chatKey = new EightBitLabel("Enter - Open Chat", Font.PLAIN, 25f);
		chatKey.setHorizontalAlignment(SwingConstants.LEFT);
		chatKey.setBounds(10, 840, 200, 20);
		
		add(gamePanel, 1);
		add(username);
		add(randomLabel);
		add(logScrollPane, 2);
		add(chat);
		add(healthPanel);
		add(keybinds);
		add(mouse);
		add(chatKey);
	}
	
	public GameDisplay getGamePanel() {
		return gamePanel;
	}
	
	public JTextField getChat() {
		return chat;
	}
	
	public void setUsername(String msg) {
		username.setText(msg);
	}
	
	public void setRandomLabel(String msg) {
		randomLabel.setText(msg);
	}
	
	public JPanel getHealthPanel() {
		return healthPanel;
	}
	
	public void reset() {
		logPanel.removeAll();
		healthPanel.removeAll();
		revalidate();
		repaint();
	}
	
	public void addLogMessage(String msg) {
		EightBitLabel m = new EightBitLabel(msg, Font.PLAIN, 25f);
		m.setHorizontalAlignment(SwingConstants.LEFT);
		logPanel.add(m);
	}
	
	public void addLogMessage(EightBitLabel msg) {
		logPanel.add(msg);
	}
	
	public void setController(ActionListener c) {
		controller = c;
		addMouseMotionListener((MouseMotionListener) c);
		addMouseListener((MouseListener) c);
	}
}
