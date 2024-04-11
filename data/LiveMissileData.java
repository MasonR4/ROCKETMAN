package data;

import java.io.Serializable;
import java.util.ArrayList;

import game_utilities.Missile;

public class LiveMissileData implements Serializable {
	private ArrayList<Missile> rockets = new ArrayList<>();
	
	public LiveMissileData(ArrayList<Missile> r) {
		rockets = r;
	}
	
	public ArrayList<Missile> getMissileData() {
		return rockets;
	}
}
