package menu_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import controller.MenuController;
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

	//private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();

	private MenuController controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public LoginScreen(MenuController ac) {
		controller = ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);

		title = new EightBitLabel("ROCKETMAN", Font.BOLD, (float) (222f * sizeRatio));
		title.setBounds((int) (375 * widthRatio), (int) (45 * heightRatio), (int) (850 * widthRatio), (int) (150 * heightRatio));

		errorLabel = new EightBitLabel("", Font.PLAIN, (float) (18f * sizeRatio));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds((int) (675 * widthRatio), (int) (525 * heightRatio), (int) (250 * widthRatio), (int) (20 * heightRatio));

		backButton = new EightBitButton("Back");
		backButton.setBounds((int) (675 * widthRatio), (int) (650 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		loginButton = new EightBitButton("Login");
		loginButton.setBounds((int) (675 * widthRatio), (int) (575 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		usernameField = new JTextField(50);
		usernameField.setBounds((int) (735 * widthRatio), (int) (450 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));
		((AbstractDocument) usernameField.getDocument()).setDocumentFilter(TEXT_FILTERS.getUsernameFilter());

		passwordField = new JPasswordField(50);
		passwordField.setBounds((int) (735 * widthRatio), (int) (475 * heightRatio), (int) (200 * widthRatio), (int) (20 * heightRatio));
		((AbstractDocument) passwordField.getDocument()).setDocumentFilter(TEXT_FILTERS.getPasswordFilter());

		usernameLabel = new EightBitLabel("Username: ", Font.PLAIN, (float) (18f * sizeRatio));
		usernameLabel.setBounds((int) (665 * widthRatio), (int) (450 * heightRatio), (int) (70 * widthRatio), (int) (20 * heightRatio));

		passwordLabel = new EightBitLabel("Password: ", Font.PLAIN, (float) (18f * sizeRatio));
		passwordLabel.setBounds((int) (665 * widthRatio), (int) (475 * heightRatio), (int) (70 * widthRatio), (int) (20 * heightRatio));

		add(title);
		add(loginButton);
		add(backButton);
		add(passwordField);
		add(usernameField);
		add(passwordLabel);
		add(usernameLabel);
		add(errorLabel);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}

	public void setController() {
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
