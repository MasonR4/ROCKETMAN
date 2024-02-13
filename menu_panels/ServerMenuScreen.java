package menu_panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import menu_utilities.TextFieldFilters;

public class ServerMenuScreen extends JPanel {
	
	private JPanel gameScreen;
	private JPanel commandPanel;
	private JPanel controlPanel;
	private JPanel detailsPanel;
	
	private JScrollPane logPanel;
	private JTextArea serverLog;
	
	private JTextField commandField;
	private JTextField serverName;
	private JTextField serverPort;
	
	private JLabel statusLabel;
	private JLabel statusUpdate;
	private JLabel serverNameLabel;
	private JLabel serverPortLabel;
	
	private JButton submitCommandButton;
	private JButton startButton;
	private JButton stopButton;
	
	private static final TextFieldFilters TEXT_FILTERS = new TextFieldFilters();
	
	public ServerMenuScreen() {
		setLayout(new BorderLayout());
		
		gameScreen = new JPanel();
        gameScreen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(gameScreen, BorderLayout.CENTER);
        
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
        
        serverPort = new JTextField();
        ((AbstractDocument) serverPort.getDocument()).setDocumentFilter(TEXT_FILTERS.getNumeralOnlyFilter());
        serverPort.setBounds(100, 45, 50, 20);
        
        
        
        statusLabel = new JLabel("Status: ");
        statusLabel.setBounds(10, 80, 90, 20);
        
        statusUpdate = new JLabel("NOT RUNNING");
        statusUpdate.setForeground(Color.RED);
        statusUpdate.setBounds(55, 80, 100, 20);

        startButton = new JButton("Start");
        startButton.setBounds(10, 115, 100, 30);
        
        stopButton = new JButton("Stop");
        stopButton.setBounds(120, 115, 100, 30);
        
        detailsPanel.add(serverNameLabel);
        detailsPanel.add(serverPortLabel);
        
        detailsPanel.add(serverName);
        detailsPanel.add(serverPort);
        detailsPanel.add(serverPort);

        detailsPanel.add(startButton);
        detailsPanel.add(stopButton);
        
        detailsPanel.add(statusLabel);
        detailsPanel.add(statusUpdate);

     
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
	
	public void setStatus(String msg, Color c) {
		statusUpdate.setText(msg);
		statusUpdate.setForeground(c);
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
	
	public void setController(ActionListener ac) {
		for (Component c : this.getComponents()) {
			if (c instanceof JButton) {
				((JButton) c).addActionListener(ac);
			}
		}
	}
	
}
