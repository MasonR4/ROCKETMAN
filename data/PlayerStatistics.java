package data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import ocsf.server.ConnectionToClient;

public class PlayerStatistics implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private int score = 0;
	
	private boolean ready = false;
	private boolean isHost = false;
	private boolean leftMatch = false;
	
	private LinkedHashMap<String, Integer> statistics = new LinkedHashMap<String, Integer>();
	
	public PlayerStatistics() {
		statistics.put("wins", 0);
		statistics.put("losses", 0);
		statistics.put("eliminations", 0);
		statistics.put("deaths", 0);
		statistics.put("rocketsFired", 0);
		statistics.put("blocksDestroyed", 0);
		statistics.put("damageDealt", 0);
	}
	
	public void incrementStat(String s) {
		int total = statistics.get(s);
		statistics.put(s, ++total);
	}
	
	public void resetStats() {
		statistics.put("wins", 0);
		statistics.put("losses", 0);
		statistics.put("eliminations", 0);
		statistics.put("deaths", 0);
		statistics.put("rocketsFired", 0);
		statistics.put("blocksDestroyed", 0);
		statistics.put("damageDealt", 0);
	}
	
	public int getStat(String s) {
		return statistics.get(s);
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public boolean isHost() {
		return isHost;
	}
	
	public void setHost() {
		isHost = true;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public boolean leftMatch() {
		return leftMatch;
	}

	public void setLeftMatch(boolean leftMatch) {
		this.leftMatch = leftMatch;
	}
}
