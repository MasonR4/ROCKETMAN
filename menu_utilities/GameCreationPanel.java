package menu_utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;

import controller.MenuController;

public class GameCreationPanel extends JPanel {

	private static final long serialVersionUID = -5905677617387143464L;
  
	private EightBitLabel title;
	private EightBitLabel lobbyNameLabel;
	private EightBitLabel maxPlayersLabel;
	private EightBitLabel errorLabel;

	private JTextField lobbyName;
	private JTextField maxPlayers;

	private EightBitButton confirmButton;
	private EightBitButton cancelButton;

	private MenuController controller;

	private double heightRatio;
	private double widthRatio;
	private double sizeRatio;

	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();

	public GameCreationPanel(MenuController ac) {
		controller = ac;
		heightRatio = controller.getHeightRatio();
		widthRatio = controller.getWidthRatio();
		sizeRatio = controller.getSizeRatio();
		setSize((int) (800 * widthRatio),(int) (600 * heightRatio));
		setLayout(null);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		title = new EightBitLabel("Create New Game", Font.PLAIN, (float) (48f * sizeRatio));
		title.setHorizontalAlignment(SwingConstants.LEFT);
		title.setBounds((int) (20 * widthRatio), (int) (20 * heightRatio), (int) (600 * widthRatio), (int) (20 * heightRatio));

		lobbyNameLabel = new EightBitLabel("Lobby Name", Font.PLAIN, (float) (35f * sizeRatio));
		lobbyNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lobbyNameLabel.setBounds((int) (20 * widthRatio), (int) (120 * heightRatio), (int) (600 * widthRatio), (int) (20 * heightRatio));

		maxPlayersLabel = new EightBitLabel("Max Players", Font.PLAIN, (float) (35f * sizeRatio));
		maxPlayersLabel.setHorizontalAlignment(SwingConstants.LEFT);
		maxPlayersLabel.setBounds((int) (20 * widthRatio), (int) (190 * heightRatio), (int) (600 * widthRatio), (int) (20 * heightRatio));

		maxPlayers = new JTextField(10);
		((AbstractDocument) maxPlayers.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());
		maxPlayers.setBounds((int) (20 * widthRatio), (int) (220 * heightRatio), (int) (300 * widthRatio), (int) (20 * heightRatio));

		lobbyName = new JTextField(50);
		lobbyName.setBounds((int) (20 * widthRatio), (int) (150 * heightRatio), (int) (600 * widthRatio), (int) (20 * heightRatio));

		errorLabel = new EightBitLabel("", Font.PLAIN, (float) (22f * sizeRatio));
		errorLabel.setBounds((int) (100 * widthRatio), (int) (270 * heightRatio), (int) (600 * widthRatio), (int) (20 * heightRatio));

		confirmButton = new EightBitButton("Confirm");
		confirmButton.setBounds((int) (20 * widthRatio), (int) (530 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		cancelButton = new EightBitButton("Cancel");
		cancelButton.setBounds((int) (290 * widthRatio), (int) (530 * heightRatio), (int) (250 * widthRatio), (int) (50 * heightRatio));

		add(title);
		add(lobbyNameLabel);
		add(lobbyName);
		add(maxPlayersLabel);
		add(maxPlayers);
		add(errorLabel);
		add(confirmButton);
		add(cancelButton);

		for (Component c : this.getComponents()) {
			if (c instanceof EightBitButton) {
				((EightBitButton) c).addActionListener(controller);
			}
		}
	}

	public String getLobbyName() {
		return lobbyName.getText();
	}

	public String getMaxPlayers() {
		return maxPlayers.getText();
	}

	public void setFieldDefaults(String usr) {
		lobbyName.setText(usr + "'s game");
		maxPlayers.setText(Integer.toString(4));
	}

	public void setError(String msg) {
		errorLabel.setText(msg);
		errorLabel.setForeground(Color.RED);
	}
}
