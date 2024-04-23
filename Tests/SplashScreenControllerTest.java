package Tests;

import static org.junit.Assert.assertEquals;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import client.Client;
import client.ClientUI;
import controller.SplashScreenController;

// Integration test simulating the user clicking on the various buttons present on the game's Main Menu
public class SplashScreenControllerTest {

    private SplashScreenController splashScreenController;
    private JPanel clientPanel;
    private Client client;
    private ClientUI clientUI;
    private CardLayout cardLayout;
    private String currentCardName; 
    
    @Before
    public void setUp() {
        clientPanel = new JPanel();
        cardLayout = new CardLayout();
        clientPanel.setLayout(cardLayout);
        
        client = new Client();
        clientUI = new ClientUI();
        
        // Add the clientPanel to the clientUI
        clientUI.add(clientPanel);

        splashScreenController = new SplashScreenController(client, clientPanel, clientUI);
    }

    @Test
    public void testActionPerformedLogin() {
        JButton loginButton = new JButton("Login");
        clientPanel.add(loginButton, "LOGIN");
        cardLayout.show(clientPanel, "LOGIN");
        currentCardName = "LOGIN"; 

        loginButton.doClick();

        assertEquals("LOGIN", currentCardName);
    }

    @Test
    public void testActionPerformedCreateAccount() {
        JButton createAccountButton = new JButton("Create Account");
        clientPanel.add(createAccountButton, "CREATE_ACCOUNT");
        cardLayout.show(clientPanel, "CREATE_ACCOUNT");
        currentCardName = "CREATE_ACCOUNT"; 

        createAccountButton.doClick();

        assertEquals("CREATE_ACCOUNT", currentCardName);
    }

    @Test
    public void testActionPerformedDisconnect() {
        JButton disconnectButton = new JButton("Disconnect");
        clientPanel.add(disconnectButton, "SERVER_CONNECTION");
        cardLayout.show(clientPanel, "SERVER_CONNECTION");
        currentCardName = "SERVER_CONNECTION"; 
        
        disconnectButton.doClick();
        
        assertEquals("SERVER_CONNECTION", currentCardName);
    }
}
