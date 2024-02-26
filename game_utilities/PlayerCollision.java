package game_utilities;

import java.io.Serializable;

public class PlayerCollision implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String direction;
	
	private boolean colliding;
	
	public PlayerCollision (String d, boolean c) {
		direction = d;
		colliding = c;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}
	
}
