package data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import game_utilities.Player;

@SuppressWarnings("serial")
public class EndGameData implements Serializable {
	
	private String winner;
	
	private ConcurrentHashMap<String, PlayerStatistics> playerStats = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
	private LinkedHashMap<String, PlayerStatistics> playerRankings = new LinkedHashMap<>();
	
	public EndGameData() {}
	
	public void setPlayers(ConcurrentHashMap<String, Player> p) {
		players.putAll(p);
	}
	
	public ConcurrentHashMap<String, Player> getPlayers() {
		return players;
	}
	
	public void setStats(ConcurrentHashMap<String, PlayerStatistics> s) {
		playerStats.putAll(s);
		playerRankings = playerStats.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore())))
				.collect(Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue,
					(e1, e2) -> e1,
					LinkedHashMap::new
				));
	}
	
	public LinkedHashMap<String, PlayerStatistics> getStats() {
		return playerRankings;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
}
