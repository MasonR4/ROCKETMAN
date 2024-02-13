package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import menu_utilities.*;

public class CreateAccountScreen extends JPanel {
	
	private EightBitLabel title;
	
	private EightBitLabel usernameLabel;
	private EightBitLabel passwordLabel;
	private EightBitLabel confirmPasswordLabel;
	private EightBitLabel errorLabel;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	
	private EightBitButton createAccountButton;
	private EightBitButton backButton;
	
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	
	public CreateAccountScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN", Font.BOLD, 222f);
		title.setBounds(375, 45, 850, 150);
		
		backButton = new EightBitButton("Back");
		backButton.setBounds(675, 650, 250, 50);	
	
		createAccountButton = new EightBitButton("Create Account");
		createAccountButton.setBounds(675, 575, 250, 50);
		
		usernameLabel = new EightBitLabel("Username: ", Font.PLAIN, 18f);
		usernameLabel.setBounds(665, 450, 70, 20);
		
		passwordLabel = new EightBitLabel("Password: ", Font.PLAIN, 18f);
		passwordLabel.setBounds(665, 475, 70, 20);
		
		confirmPasswordLabel = new EightBitLabel("Confirm: ", Font.PLAIN, 18f);
		confirmPasswordLabel.setBounds(665, 500, 70, 20);
		
		errorLabel = new EightBitLabel("", Font.PLAIN, 18f);
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(550, 525, 500, 20);
		
		usernameField = new JTextField(50);
		usernameField.setBounds(735, 450, 200, 20);
		((AbstractDocument) usernameField.getDocument()).setDocumentFilter(TEXT_FILTERS.getUsernameFilter());
		
		passwordField = new JPasswordField(50);
		passwordField.setBounds(735, 475, 200, 20);
		((AbstractDocument) passwordField.getDocument()).setDocumentFilter(TEXT_FILTERS.getPasswordFilter());
		
		confirmPasswordField = new JPasswordField(50);
		confirmPasswordField.setBounds(735, 500, 200, 20);
		((AbstractDocument) confirmPasswordField.getDocument()).setDocumentFilter(TEXT_FILTERS.getPasswordFilter());
		
		add(title);
		add(createAccountButton);
		add(backButton);
		add(usernameLabel);
		add(passwordLabel);
		add(errorLabel);
		add(confirmPasswordLabel);
		add(usernameField);
		add(passwordField);
		add(confirmPasswordField);
	}
	
	public String getUsername() {
		return usernameField.getText();
	}
	
	public char[] getPassword() {
		return passwordField.getPassword();
	}
	
	public char[] getConfirmPassword() {
		return confirmPasswordField.getPassword();
	}
	
	public void setError(String msg) {
		errorLabel.setText(msg);
	}
	
	public void clearFields() {
		usernameField.setText("");
		passwordField.setText("");
		confirmPasswordField.setText("");
	}
	public void setController(ActionListener ac) {
		controller = ac;
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}
}
