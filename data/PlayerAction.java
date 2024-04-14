package data;

public class PlayerAction extends Event {
	private static final long serialVersionUID = 1L;
	
	private int gameID;
	private String username;
	
	private String type;
	private String action;
	
	private int mouseX, mouseY;
	private int posX, posY;
	
	public PlayerAction(int g, String s, String t, String a) {
		gameID = g;
		username = s;
		type = t;
		action = a;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getUsername() {
		return username;
	}
	
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public void setMousePos(int x, int y) {
		mouseX = x;
		mouseY = y;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
