package menu_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import game_utilities.Player;
import menu_utilities.EightBitLabel;
import menu_utilities.GameDisplay;

public class GameScreen extends JLayeredPane {
	
	private GameDisplay gamePanel;
	
	private EightBitLabel username;
	private EightBitLabel randomLabel;
	
	private JPanel logPanel;
	private JScrollPane logScrollPane;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	ActionListener controller;
	
	MouseListener mc;
	MouseMotionListener mmc;
	
	public GameScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		username = new EightBitLabel("i put the username here", Font.PLAIN, 25f);
		username.setBounds(10, 200, 200, 50);
		
		randomLabel = new EightBitLabel("this is a label", Font.PLAIN, 25f);
		randomLabel.setBounds(10, 220, 200, 50);
		
		gamePanel = new GameDisplay();
		gamePanel.setLayout(null);
		gamePanel.setBounds(340, 0, 900, 900);
		
		logPanel = new JPanel();
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.PAGE_AXIS));
		logPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		logScrollPane = new JScrollPane(logPanel);
		logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		logScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		logScrollPane.setBounds(1240, 450, 360, 400);
		logScrollPane.setBorder(BorderFactory.createEmptyBorder());
		logScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (!e.getValueIsAdjusting()) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		
		add(gamePanel, 1);
		add(username);
		add(randomLabel);
		add(logScrollPane);
	}
	
	public GameDisplay getGamePanel() {
		return gamePanel;
	}
	
	public void setUsername(String msg) {
		username.setText(msg);
	}
	
	public void setRandomLabel(String msg) {
		randomLabel.setText(msg);
	}
	
	public void reset() {
		logPanel.removeAll();
		repaint();
		revalidate();
	}
	
	public void addLogMessage(String msg, Color color) {
		EightBitLabel m = new EightBitLabel(msg, Font.BOLD, 25f);
		m.setForeground(color);
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
