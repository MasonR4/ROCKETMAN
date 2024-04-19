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

import controller.MenuController;
import menu_utilities.*;

public class CreateAccountScreen extends JPanel {
	private static final long serialVersionUID = 1616294608624308351L;

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

	private MenuController controller;
	
	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;
	
	public CreateAccountScreen(ActionListener ac) {
		controller = (MenuController) ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN", Font.BOLD, (float) (222f * sizeRatio));
		title.setBounds((int) (375 * widthRatio), (int) (45 * heightRatio), (int) (850 * widthRatio), (int) (150 * heightRatio));

		backButton = new EightBitButton("Back");
		backButton.setBounds((int) (675 * widthRatio), (int) (650 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		createAccountButton = new EightBitButton("Create Account");
		createAccountButton.setBounds((int) (675 * widthRatio), (int) (575 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		usernameLabel = new EightBitLabel("Username: ", Font.PLAIN, (float) (18f * sizeRatio));
		usernameLabel.setBounds((int) (665 * widthRatio), (int) (450 * heightRatio), (int) (70 * widthRatio), (int) (20 * heightRatio));

		passwordLabel = new EightBitLabel("Password: ", Font.PLAIN, (float) (18f * sizeRatio));
		passwordLabel.setBounds((int) (665 * widthRatio), (int) (475 * heightRatio), (int) (70 * widthRatio), (int) (20 * heightRatio));

		confirmPasswordLabel = new EightBitLabel("Confirm: ", Font.PLAIN, (float) (18f * sizeRatio));
		confirmPasswordLabel.setBounds((int) (665 * widthRatio), (int) (500 * heightRatio), (int) (70 * widthRatio), (int) (20 * heightRatio));

		errorLabel = new EightBitLabel("", Font.PLAIN, (float) (18f * sizeRatio));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds((int) (550 * widthRatio), (int) (525 * heightRatio), (int) (500 * widthRatio), (int) (20 * heightRatio));

		usernameField = new JTextField(50);
		usernameField.setBounds((int) (735 * widthRatio), (int) (450 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));
		((AbstractDocument) usernameField.getDocument()).setDocumentFilter(TEXT_FILTERS.getUsernameFilter());

		passwordField = new JPasswordField(50);
		passwordField.setBounds((int) (735 * widthRatio), (int) (475 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));
		((AbstractDocument) passwordField.getDocument()).setDocumentFilter(TEXT_FILTERS.getPasswordFilter());

		confirmPasswordField = new JPasswordField(50);
		confirmPasswordField.setBounds((int) (735 * widthRatio), (int) (500 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));
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
		//controller = ac;
		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}
}
