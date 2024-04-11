package data;

import java.io.Serializable;
import java.util.HashMap;

public class PlayerPositionsData implements Serializable {
	
	static final long serialVersionUID = 3508034168541165336L;
	HashMap<String, int[]> playerPositions = new HashMap<>();
		
	public PlayerPositionsData() {}
	
	public void addPlayerPos(String usr, int x, int y) {
		playerPositions.put(usr, new int[] {x, y});
	}
	
	public HashMap<String, int[]> getPlayerPositions() {
		return playerPositions;
	}
}
