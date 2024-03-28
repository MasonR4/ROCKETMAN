package data;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStateData implements Serializable {
	
	private ArrayList<PlayerActionData> playerActions = new ArrayList<PlayerActionData>();
	
	public GameStateData() {}

	public ArrayList<PlayerActionData> getPlayerActions() {
		return playerActions;
	}

	public void setPlayerActions(ArrayList<PlayerActionData> playerActions) {
		this.playerActions = playerActions;
	}
}
