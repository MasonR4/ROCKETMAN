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
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;
import menu_utilities.TextFieldFilters;

public class LoginScreen extends JPanel {
	
	private static final long serialVersionUID = -4914689214145321285L;

	private EightBitLabel title;
	
	private EightBitLabel usernameLabel;
	private EightBitLabel passwordLabel;
	private EightBitLabel errorLabel;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private EightBitButton loginButton;
	private EightBitButton backButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();
	
	private ActionListener controller;
	
	public LoginScreen() {
		setSize(DEFAULT_SIZE);
		setLayout(null);
	
		title = new EightBitLabel("ROCKETMAN", Font.BOLD, 222f);
		title.setBounds(375, 45, 850, 150);
	
		errorLabel = new EightBitLabel("", Font.PLAIN, 18f);
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(675, 525, 250, 20);
	
		backButton = new EightBitButton("Back");
		backButton.setBounds(675, 650, 250, 50);	
	
		loginButton = new EightBitButton("Login");
		loginButton.setBounds(675, 575, 250, 50);
		
		usernameField = new JTextField(50);
		usernameField.setBounds(735, 450, 200, 20);
		((AbstractDocument) usernameField.getDocument()).setDocumentFilter(TEXT_FILTERS.getUsernameFilter());
		
		passwordField = new JPasswordField(50);
		passwordField.setBounds(735, 475, 200, 20);
		((AbstractDocument) passwordField.getDocument()).setDocumentFilter(TEXT_FILTERS.getPasswordFilter());
		
		usernameLabel = new EightBitLabel("Username: ", Font.PLAIN, 18f);
		usernameLabel.setBounds(665, 450, 70, 20);
		
		passwordLabel = new EightBitLabel("Password: ", Font.PLAIN, 18f);
		passwordLabel.setBounds(665, 475, 70, 20);
		
		add(title);
		add(loginButton);
		add(backButton);
		add(passwordField);
		add(usernameField);
		add(passwordLabel);	
		add(usernameLabel);
		add(errorLabel);
	}
	
	public void setController(ActionListener ac) {
		controller = ac;
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}
	
	public void setDefaultUsername(String usr) {
		usernameField.setText(usr);
	}
	
	public String getUsername() {
		return usernameField.getText();
	}
	
	public void clearFields() {
		passwordField.setText("");
	}
	
	public char[] getPassword() {
		return passwordField.getPassword();
	}
	
	public void setError(String msg) {
		errorLabel.setText(msg);
	}
	
}
