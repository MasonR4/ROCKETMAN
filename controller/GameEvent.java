package controller;

import java.io.Serializable;

import data.Event;

public class GameEvent extends Event implements Serializable {
	
	private int gameID;
	
	private String type;
	private String action;
	
	// TODO placeholder class for things like map updates, player eliminations, rocket explosions etc.
	
}
