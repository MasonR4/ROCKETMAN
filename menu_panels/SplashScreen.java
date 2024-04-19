package menu_panels;

import javax.swing.*;

import controller.SplashScreenController;
import menu_utilities.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

public class SplashScreen extends JPanel {
	
	private EightBitLabel title;
	
	private EightBitButton loginButton;
	private EightBitButton createAccountButton;
	private EightBitButton quitButton;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private ActionListener controller;
	
	private Dimension actualSize;
	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;
	
	public SplashScreen() {
		actualSize = ((SplashScreenController) controller).getActualSize();
		heightRatio = ((SplashScreenController) controller).getHeightRatio();
		widthRatio = ((SplashScreenController) controller).getWidthRatio();
		sizeRatio = ((SplashScreenController) controller).getSizeRatio();
		
		setSize(actualSize);
		setLayout(null);
		
		title = new EightBitLabel("ROCKETMAN", Font.BOLD, (float) (222f * sizeRatio));
		title.setBounds((int) (375 * widthRatio), (int) (45 * heightRatio), (int) (850 * widthRatio), (int) (150 * widthRatio));
		
		loginButton = new EightBitButton("Login");
		loginButton.setBounds(675, 500, 250, 50);
		
		createAccountButton = new EightBitButton("Create Account");
		createAccountButton.setBounds(675, 575, 250, 50);
		
		quitButton = new EightBitButton("Disconnect");
		quitButton.setBounds(675, 650, 250, 50);
		
		add(title);
		add(loginButton);
		add(createAccountButton);
		add(quitButton);
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
