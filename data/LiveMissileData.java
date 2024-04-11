package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import game_utilities.Missile;

public class LiveMissileData implements Serializable {
	private CopyOnWriteArrayList<Missile> rockets = new CopyOnWriteArrayList<>();
	
	public LiveMissileData(CopyOnWriteArrayList<Missile> r) {
		rockets = r;
	}
	
	public CopyOnWriteArrayList<Missile> getMissileData() {
		return rockets;
	}
}
