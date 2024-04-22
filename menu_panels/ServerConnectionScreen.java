package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import controller.ServerConnectionScreenController;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;
import menu_utilities.TextFieldFilters;

public class ServerConnectionScreen extends JPanel {

	private static final long serialVersionUID = -4138189611600111207L;

	private EightBitLabel title;

	private EightBitButton connectButton;
	private EightBitButton quitButton;

	private EightBitLabel serverAddressLabel;
	private EightBitLabel serverPortLabel;
	private EightBitLabel errorLabel;

	private JTextField serverAddressField;
	private JTextField serverPortField;

	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();

	private ActionListener controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public ServerConnectionScreen(ActionListener ac) {
		controller = ac;
		actualSize = ((ServerConnectionScreenController) controller).getActualSize();
		heightRatio = ((ServerConnectionScreenController) controller).getHeightRatio();
		widthRatio = ((ServerConnectionScreenController) controller).getWidthRatio();
		sizeRatio = ((ServerConnectionScreenController) controller).getSizeRatio();

		setSize(actualSize);
		setLayout(null);

		title = new EightBitLabel("ROCKETMAN", Font.BOLD, (float) (222f * sizeRatio));
		title.setBounds((int) (375 * widthRatio), (int) (45 * heightRatio), (int) (850 * widthRatio), (int) (150 * heightRatio));

		errorLabel = new EightBitLabel("", Font.PLAIN, (float) (18f * sizeRatio));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds((int) (550 * widthRatio), (int) (525 * heightRatio), (int) (500 * widthRatio), (int) (20 * heightRatio));

		quitButton = new EightBitButton("Quit");
		quitButton.setBounds((int) (675 * widthRatio), (int) (650 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		connectButton = new EightBitButton("Connect");
		connectButton.setBounds((int) (675 * widthRatio), (int) (575 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		serverAddressField = new JTextField(50);
		serverAddressField.setBounds((int) (760 * widthRatio), (int) (450 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));
		((AbstractDocument) serverAddressField.getDocument()).setDocumentFilter(TEXT_FILTERS.getServerAddressFilter());

		serverPortField = new JTextField(15);
		serverPortField.setBounds((int) (760 * widthRatio), (int) (475 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));
		((AbstractDocument) serverPortField.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());

		serverAddressLabel = new EightBitLabel("Server Address: ", Font.PLAIN, (float) (18f * sizeRatio));
		serverAddressLabel.setBounds((int) (640 * widthRatio), (int) (450 * heightRatio), (int) (120 * widthRatio), (int) (20 * heightRatio));

		serverPortLabel = new EightBitLabel("Server Port: ", Font.PLAIN, (float) (18f * sizeRatio));
		serverPortLabel.setBounds((int) (640 * widthRatio), (int) (475 * heightRatio), (int) (120 * widthRatio), (int) (20 * heightRatio));

		add(title);
		add(errorLabel);
		add(quitButton);
		add(connectButton);
		add(serverAddressField);
		add(serverAddressLabel);
		add(serverPortField);
		add(serverPortLabel);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}

	public void setError(String msg) {
		errorLabel.setText(msg);
	}

	// allows a default address to be set from some external class probably client ui reading in the config man idk
	public void setDefaultConnectionInfo(String addr, String port) {
		serverAddressField.setText(addr);
		serverPortField.setText(port);
	}

	public String getAddress() {
		return serverAddressField.getText();
	}

	public String getPort() {
		return serverPortField.getText();
	}
}
