package controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import client.Client;
import client.ClientUI;

public abstract class MenuController implements ActionListener {

	protected Client client;
	protected ClientUI clientUI;
	protected JPanel clientPanel;

	protected CardLayout cl;

	public MenuController(Client c, JPanel p, ClientUI cui) {
		client = c;
		clientPanel = p;
		clientUI = cui;

		cl = (CardLayout) clientPanel.getLayout();
	}

	public void showThis() {
		cl.show(clientPanel, "SPLASH");
	}

	public double getHeightRatio() {
		return clientUI.getHeightRatio();
	}

	public double getWidthRatio() {
		return clientUI.getWidthRatio();
	}

	public double getSizeRatio() {
		return clientUI.getSizeRatio();
	}

	public Dimension getActualSize() {
		return clientUI.getActualSize();
	}
}
