package menu_utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlayerListingPanel extends JPanel {
	
	private static final long serialVersionUID = -695506757652655320L;
	private String username;
	private boolean ready = false;
	
	private EightBitLabel usernameLabel;
	private EightBitLabel hostLabel;

	private EightBitLabel readyLabel;
	
	private static Dimension size = new Dimension(996, 100);
	
	public PlayerListingPanel(String s) {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		username = s;
		
		usernameLabel = new EightBitLabel(username, Font.PLAIN, 33f);
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setBounds(40, 25, 500, 40);
		
		hostLabel = new EightBitLabel("", Font.PLAIN, 33f);
		hostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		hostLabel.setBounds(400, 25, 150, 40);
		
		readyLabel = new EightBitLabel("Not Ready", Font.PLAIN, 28f);
		readyLabel.setForeground(Color.RED);
		readyLabel.setBounds(700, 25, 100, 40);
		
		add(usernameLabel);
		add(hostLabel);
		add(readyLabel);
	}
	
	public void setHost(String who) {
		hostLabel.setText(who);
	}
	
	public void ready() {
		ready = true;
		readyLabel.setText("Ready");
		readyLabel.setForeground(Color.GREEN);
		readyLabel.repaint();
	}
	
	public void unready() {
		ready = false;
		readyLabel.setText("Not Ready");
		readyLabel.setForeground(Color.RED);
		readyLabel.repaint();
	}
	
	public boolean isReady() {
		return ready;
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
