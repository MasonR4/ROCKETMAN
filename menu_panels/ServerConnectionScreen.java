package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

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
		
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	
	private ActionListener controller;
	
	public ServerConnectionScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN", Font.BOLD, 222f);
		title.setBounds(375, 45, 850, 150);
		
		errorLabel = new EightBitLabel("", Font.PLAIN, 18f);
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(550, 525, 500, 20);
		
		quitButton = new EightBitButton("Quit");
		quitButton.setBounds(675, 650, 250, 50);
		
		connectButton = new EightBitButton("Connect");
		connectButton.setBounds(675, 575, 250, 50);
		
		serverAddressField = new JTextField(50);
		serverAddressField.setBounds(760, 450, 200, 20);
		((AbstractDocument) serverAddressField.getDocument()).setDocumentFilter(TEXT_FILTERS.getServerAddressFilter());
		
		serverPortField = new JTextField(15);
		serverPortField.setBounds(760, 475, 200, 20);
		((AbstractDocument) serverPortField.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());
		
		serverAddressLabel = new EightBitLabel("Server Address: ", Font.PLAIN, 18f);
		serverAddressLabel.setBounds(640, 450, 120, 20);
		
		serverPortLabel = new EightBitLabel("Server Port: ", Font.PLAIN, 18f);
		serverPortLabel.setBounds(640, 475, 120, 20);
		
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
		controller = ac;
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
