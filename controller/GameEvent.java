package controller;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class GameEvent implements Serializable {
	private ConcurrentHashMap<String, Object> events = new ConcurrentHashMap<>();
	
	public void addEvent(String s, Object o) {
		events.put(s, o);
	}
	
	public ConcurrentHashMap<String, Object> getEvents() {
		return events;			
	}
}
