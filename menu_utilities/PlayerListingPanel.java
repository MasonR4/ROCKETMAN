package menu_utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PlayerListingPanel extends JPanel {

	private String username;
	private boolean ready = false;

	private EightBitLabel usernameLabel;
	private EightBitLabel hostLabel;

	private EightBitLabel readyLabel;

	private static Dimension size = new Dimension(996, 100);

	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public PlayerListingPanel(String s, double hr, double wr, double sr) {
		size = new Dimension((int) (996 * wr), (int) (100 * hr));
		heightRatio = hr;
		widthRatio = wr;
		sizeRatio = sr;
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		username = s;

		usernameLabel = new EightBitLabel(username, Font.PLAIN, (float) (33f * sizeRatio));
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setBounds((int) (40 * widthRatio), (int) (25 * heightRatio), (int) (500 * widthRatio), (int) (40 * heightRatio));

		hostLabel = new EightBitLabel("", Font.PLAIN, (float) (33f * sizeRatio));
		hostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		hostLabel.setBounds((int) (400 * widthRatio), (int) (25 * heightRatio), (int) (150 * widthRatio), (int) (40 * heightRatio));

		readyLabel = new EightBitLabel("Not Ready", Font.PLAIN, (float) (28f * sizeRatio));
		readyLabel.setForeground(Color.RED);
		readyLabel.setBounds((int) (700 * widthRatio), (int) (25 * heightRatio), (int) (100 * widthRatio), (int) (40 * heightRatio));

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
