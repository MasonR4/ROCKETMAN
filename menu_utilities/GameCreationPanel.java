package menu_utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;

public class GameCreationPanel extends JPanel {
	
	private EightBitLabel title;
	private EightBitLabel lobbyNameLabel;
	private EightBitLabel maxPlayersLabel;
	private EightBitLabel errorLabel;
	
	private JTextField lobbyName;
	private JTextField maxPlayers;
	
	private EightBitButton confirmButton;
	private EightBitButton cancelButton;
	
	private ActionListener controller;
	
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();
	
	public GameCreationPanel() {
		setSize(800, 600);
		setLayout(null);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		title = new EightBitLabel("Create New Game", Font.PLAIN, 48f);
		title.setHorizontalAlignment(SwingConstants.LEFT);
		title.setBounds(20, 20, 600, 20);
		
		lobbyNameLabel = new EightBitLabel("Lobby Name", Font.PLAIN, 35f);
		lobbyNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lobbyNameLabel.setBounds(20, 120, 600, 20);
		
		maxPlayersLabel = new EightBitLabel("Max Players", Font.PLAIN, 35f);
		maxPlayersLabel.setHorizontalAlignment(SwingConstants.LEFT);
		maxPlayersLabel.setBounds(20, 190, 600, 20);
		
		maxPlayers = new JTextField(10);
		((AbstractDocument) maxPlayers.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());
		maxPlayers.setBounds(20, 220, 50, 20);
		
		lobbyName = new JTextField(50);
		lobbyName.setBounds(20, 150, 300, 20);
		
		errorLabel = new EightBitLabel("", Font.PLAIN, 22f);
		errorLabel.setBounds(100, 270, 600, 20);
		
		confirmButton = new EightBitButton("Confirm");
		confirmButton.setBounds(20, 530, 250, 50);
		
		cancelButton = new EightBitButton("Cancel");
		cancelButton.setBounds(290, 530, 250, 50);
		
		add(title);
		add(lobbyNameLabel);
		add(lobbyName);
		add(maxPlayersLabel);
		add(maxPlayers);
		add(errorLabel);
		add(confirmButton);
		add(cancelButton);
	}
	
	public String getLobbyName() {
		return lobbyName.getText();
	}
	
	public String getMaxPlayers() {
		return maxPlayers.getText();
	}

	public void setError(String msg) {
		errorLabel.setText(msg);
		errorLabel.setForeground(Color.RED);
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
