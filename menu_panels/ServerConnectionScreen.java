package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import controller.ServerConnectionScreenController;
import controller.SplashScreenController;
import menu_utilities.*;

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
	
	// ACTUALLY CONNECT TO THE THING
	private String serverAddress;
	private String serverPort;
		
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
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
		
		//setSize(DEFAULT_SIZE);
		setSize(actualSize);
		setLayout(null);
		
//		title = new EightBitLabel("ROCKETMAN", Font.BOLD, 222f);
//		title.setBounds(375, 45, 850, 150);
//		
//		errorLabel = new EightBitLabel("", Font.PLAIN, 18f);
//		errorLabel.setForeground(Color.RED);
//		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		errorLabel.setBounds(550, 525, 500, 20);
//		
//		quitButton = new EightBitButton("Quit");
//		quitButton.setBounds(675, 650, 250, 50);
//		
//		connectButton = new EightBitButton("Connect");
//		connectButton.setBounds(675, 575, 250, 50);
//		
//		serverAddressField = new JTextField(50);
//		serverAddressField.setBounds(760, 450, 200, 20);
//		((AbstractDocument) serverAddressField.getDocument()).setDocumentFilter(TEXT_FILTERS.getServerAddressFilter());
//		
//		serverPortField = new JTextField(15);
//		serverPortField.setBounds(760, 475, 200, 20);
//		((AbstractDocument) serverPortField.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());
//		
//		serverAddressLabel = new EightBitLabel("Server Address: ", Font.PLAIN, 18f);
//		serverAddressLabel.setBounds(640, 450, 120, 20);
//		
//		serverPortLabel = new EightBitLabel("Server Port: ", Font.PLAIN, 18f);
//		serverPortLabel.setBounds(640, 475, 120, 20);
		
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
	}
	
	public void setController(ActionListener ac) {
		//controller = ac;
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
		serverAddress = addr;
		serverPort = port;
		
		serverAddressField.setText(addr);
		serverPortField.setText(port);
	}
	
	// possibly dont need this function
	public void setConnectionInfo() {
		serverAddress = serverAddressField.getText();
		serverPort = serverPortField.getText();
	}
	
	public String getAddress() {
		return serverAddressField.getText();
	}
	
	public String getPort() {
		return serverPortField.getText();
	}
}
