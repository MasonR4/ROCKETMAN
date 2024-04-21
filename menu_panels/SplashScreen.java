package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controller.MenuController;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

public class SplashScreen extends JPanel {

	private EightBitLabel title;

	private EightBitButton loginButton;
	private EightBitButton createAccountButton;
	private EightBitButton quitButton;

	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private MenuController controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public SplashScreen(ActionListener ac) {
		controller = (MenuController) ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();

		setSize(actualSize);
		setLayout(null);

		title = new EightBitLabel("ROCKETMAN", Font.BOLD, (float) (222f * sizeRatio));
		title.setBounds((int) (375 * widthRatio), (int) (45 * heightRatio), (int) (850 * widthRatio), (int) (150 * widthRatio));

		loginButton = new EightBitButton("Login");
		loginButton.setBounds((int) (675 * widthRatio), (int) (500 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		createAccountButton = new EightBitButton("Create Account");
		createAccountButton.setBounds((int) (675 * widthRatio), (int) (575 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		quitButton = new EightBitButton("Disconnect");
		quitButton.setBounds((int) (675 * widthRatio), (int) (650 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		add(title);
		add(loginButton);
		add(createAccountButton);
		add(quitButton);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}
}
