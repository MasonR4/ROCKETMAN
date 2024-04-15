package data;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class GameEvent extends Event implements Serializable {
	private static final long serialVersionUID = -4757487624862843897L;
	private ConcurrentHashMap<String, Object> events = new ConcurrentHashMap<>();
	
	public void addEvent(String s, Object o) {
		events.put(s, o);
	}
	
	public ConcurrentHashMap<String, Object> getEvents() {
		return events;			
	}
}
