package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JPanel;

import client.Client;
import client.ClientUI;
import data.GenericRequest;

public class MainMenuScreenController extends MenuController {

	public MainMenuScreenController(Client c, JPanel p, ClientUI ui) {
		super(c, p, ui);
	}

	@Override
	public double getHeightRatio() {
		return clientUI.getHeightRatio();
	}

	@Override
	public double getWidthRatio() {
		return clientUI.getWidthRatio();
	}

	@Override
	public double getSizeRatio() {
		return clientUI.getSizeRatio();
	}

	@Override
	public Dimension getActualSize() {
		return clientUI.getActualSize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch(action) {
		case "Play":
			try {
				GenericRequest rq = new GenericRequest("REQUEST_GAMES_INFO");
				client.sendToServer(rq);
			} catch (IOException why) {
				why.printStackTrace();
			}
			cl.show(clientPanel, "FIND_GAME");
			break;

		case "Profile":
			GenericRequest rq = new GenericRequest("GET_STATISTICS");
			rq.setData(client.getUsername());
			try {
				client.sendToServer(rq);
			} catch (IOException NO_STATS_FOR_YOU) {
				NO_STATS_FOR_YOU.printStackTrace();
			}
			break;

		case "Options":
			break;

		case "Quit":
			clientUI.closingProcedure();
			break;
		}

	}

}
