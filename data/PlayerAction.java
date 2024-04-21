package data;

public class PlayerAction extends Event {
	private static final long serialVersionUID = 1L;

	private int gameID;
	private String username;

	private String type;
	private String action;

	private int mouseX, mouseY;
	private int endX, endY;
	private int posX, posY;

	private int missileNumber;
	private int priority = 10;

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

	public void setLauncherEnd(int x, int y) {
		endX = x;
		endY = y;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
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

	public int getMissileNumber() {
		return missileNumber;
	}

	public void setMissileNumber(int missileNumber) {
		this.missileNumber = missileNumber;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
