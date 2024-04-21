package menu_panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import controller.MenuController;
import menu_utilities.EightBitButton;
import menu_utilities.EightBitLabel;

public class MainMenuScreen extends JPanel {

	private static final long serialVersionUID = -120559750408550653L;

	private EightBitLabel title;

	private EightBitButton playButton;
	private EightBitButton profileButton;
	private EightBitButton quitButton;

	//private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);

	private MenuController controller;

	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	public MainMenuScreen(MenuController ac) {
		controller = ac;
		actualSize =  controller.getActualSize();
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize(actualSize);
		setLayout(null);

		title = new EightBitLabel("ROCKETMAN", Font.BOLD, (float) (222f * sizeRatio));
		title.setBounds((int) (375 * widthRatio), (int) (45 * heightRatio), (int) (850 * widthRatio), (int) (150 * heightRatio));

		playButton = new EightBitButton("Play");
		playButton.setBounds((int) (675 * widthRatio), (int) (500 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		profileButton = new EightBitButton("Profile");
		profileButton.setBounds((int) (675 * widthRatio), (int) (575 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		quitButton = new EightBitButton("Quit");
		quitButton.setBounds((int) (675 * widthRatio), (int) (650 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		add(title);
		add(playButton);
		add(profileButton);
		add(quitButton);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}
}
