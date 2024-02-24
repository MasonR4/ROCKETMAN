package menu_panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;

import data.GameLobbyData;
import menu_utilities.TextFieldFilters;
import server_utilities.ServerGameListingPanel;

public class ServerMenuScreen extends JPanel {
	
	private JPanel gameScreen;
	private JPanel commandPanel;
	private JPanel controlPanel;
	private JPanel detailsPanel;
	
	private JScrollPane logPanel;
	private JScrollPane gameScrollPane;
	private JTextArea serverLog;
	
	private JTextField commandField;
	private JTextField serverName;
	private JTextField serverPort;
	private JTextField timeoutField;
	
	private JLabel statusLabel;
	private JLabel statusUpdate;
	private JLabel serverNameLabel;
	private JLabel serverPortLabel;
	private JLabel serverTimeoutLabel;
	
	private JButton submitCommandButton;
	private JButton startButton;
	private JButton stopButton;
	private JButton quitButton;
	
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();
	
	public ServerMenuScreen() {
		setLayout(new BorderLayout());
		
		gameScreen = new JPanel();
		gameScreen.setLayout(new BoxLayout(gameScreen, BoxLayout.PAGE_AXIS));
        
        gameScrollPane = new JScrollPane(gameScreen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gameScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(gameScrollPane, BorderLayout.CENTER);
        
        commandPanel = new JPanel();
        commandPanel.setLayout(null);
        commandPanel.setBorder(BorderFactory.createTitledBorder("Command"));
        commandPanel.setPreferredSize(new Dimension(900, 50));
        
        commandField = new JTextField(20);
        commandField.setBounds(10, 20, 950, 20);
        
        submitCommandButton = new JButton("Submit");
        submitCommandButton.setBounds(975, 20, 100, 20);
        
        commandPanel.add(commandField);
        commandPanel.add(submitCommandButton);
        
        add(commandPanel, BorderLayout.SOUTH);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
 
        serverLog = new JTextArea(5, 30);
        serverLog.setEditable(false); 
        logPanel = new JScrollPane(serverLog);
        logPanel.setBorder(BorderFactory.createTitledBorder("Server Log"));

        detailsPanel = new JPanel(null);
        detailsPanel.setPreferredSize(new Dimension(200, 300));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Server Info"));

        serverNameLabel = new JLabel("Server Name:");
        serverNameLabel.setBounds(10, 20, 90, 20);
        
        serverName = new JTextField();
        ((AbstractDocument) serverName.getDocument()).setDocumentFilter(TEXT_FILTERS.getPasswordFilter());
        serverName.setBounds(100, 20, 200, 20);
        
        serverPortLabel = new JLabel("Server Port: ");
        serverPortLabel.setBounds(10, 45, 90, 20);
        
        serverPort = new JTextField(6);
        ((AbstractDocument) serverPort.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());
        serverPort.setBounds(100, 45, 50, 20);
        
        timeoutField = new JTextField(5);
        ((AbstractDocument) timeoutField.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());
        timeoutField.setBounds(100, 70, 50, 20);
        
        serverTimeoutLabel = new JLabel("Timeout: ");
        serverTimeoutLabel.setBounds(10, 70, 200, 20);
        
        statusLabel = new JLabel("Status: ");
        statusLabel.setBounds(10, 100, 90, 20);
        
        statusUpdate = new JLabel("NOT RUNNING");
        statusUpdate.setForeground(Color.RED);
        statusUpdate.setBounds(55, 100, 100, 20);

        startButton = new JButton("Start");
        startButton.setBounds(10, 135, 100, 30);
        
        stopButton = new JButton("Stop");
        stopButton.setBounds(120, 135, 100, 30);
        
        quitButton = new JButton("Quit");
        quitButton.setBounds(230, 135, 100, 30);
        
        detailsPanel.add(serverNameLabel);
        detailsPanel.add(serverPortLabel);
        
        detailsPanel.add(serverName);
        detailsPanel.add(serverPort);
        detailsPanel.add(serverPort);

        detailsPanel.add(startButton);
        detailsPanel.add(stopButton);
        detailsPanel.add(quitButton);
        
        detailsPanel.add(statusLabel);
        detailsPanel.add(statusUpdate);

        detailsPanel.add(timeoutField);
        detailsPanel.add(serverTimeoutLabel);
        
        controlPanel.add(logPanel);
        controlPanel.add(detailsPanel);


        add(controlPanel, BorderLayout.EAST);
	}
	
	public JTextArea getServerLog() {
		return serverLog;
	}
	
	public JLabel getServerStatusLabel() {
		return statusUpdate;
	}
	
	public JPanel getGamesPanel() {
		return gameScreen;
	}
	
	public void setStatus(String msg, Color c) {
		statusUpdate.setText(msg);
		statusUpdate.setForeground(c);
	}
	
	public void setDefaultInfo(String name, String port, String timeout) {
		serverName.setText(name);
		serverPort.setText(port);
		timeoutField.setText(timeout);
	}
	
	public String[] getCommand() {
		return commandField.getText().split(" ");
	}
	
	public String getServerName() {
		return serverName.getText();
	}
	
	public int getPort() {
		return Integer.parseInt(serverPort.getText());
	}
	
	public int getTimeout() {
		return Integer.parseInt(timeoutField.getText());
	}
	
	public void enableQuitButton(boolean toggle) {
		if (toggle == true) {
			quitButton.setToolTipText("Quit");
			quitButton.setEnabled(toggle);
		} else {
			quitButton.setToolTipText("Server must be stopped before quitting");
			quitButton.setEnabled(toggle);
		}
	}
	
	public void setController(ActionListener ac) {
		startButton.addActionListener(ac);
		stopButton.addActionListener(ac);
		submitCommandButton.addActionListener(ac);
		quitButton.addActionListener(ac);
	}
	
}
