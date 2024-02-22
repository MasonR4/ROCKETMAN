package server_utilities;

import java.util.ArrayList;
import data.NewGameData;
import data.PlayerData;

public class GameLobby {
	
	private String lobbyName;
	private String hostUsername;
	
	private int playerCount;
	private int playerCap;
	private int gameID;
	
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	
	// TODO here is where we store stuff like player objects, the grid
	// other game relevant stuff
	
	// TODO also all of the game logic that needs to be handled server side goes in here too
	
	public GameLobby(String n, String hn, int mp, int gid) {
		lobbyName = n;
		hostUsername = hn;
		gameID = gid;
		playerCap = mp;
	}
	
	public NewGameData generateGameListing() {
		NewGameData tempInfo = new NewGameData(lobbyName, hostUsername, playerCap, gameID);
		return tempInfo;
	}
}
