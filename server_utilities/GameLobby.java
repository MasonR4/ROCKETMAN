package server_utilities;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.swing.SwingConstants;

import data.EndGameData;
import data.Event;
import data.GameEvent;
import data.GameLobbyData;
import data.GenericRequest;
import data.MatchSettings;
import data.PlayerAction;
import data.PlayerJoinLeaveData;
import data.PlayerReadyData;
import data.PlayerStatistics;
import data.StartGameData;
import game_utilities.Block;
import game_utilities.DeathMarker;
import game_utilities.Effect;
import game_utilities.Explosion;
import game_utilities.Missile;
import game_utilities.Player;
import game_utilities.RocketLauncher;
import game_utilities.RocketTrail;
import game_utilities.SpawnBlock;
import menu_utilities.EightBitLabel;
import ocsf.server.ConnectionToClient;
import server.Server;

public class GameLobby implements Runnable {
	private final long TARGET_DELTA = 16;

	private Server server;
	private boolean gameStarted = false;
	private boolean gameWon = false;

	private String lobbyName;
	private String hostUsername;

	private int playerCount = 0;
	private int playerCap;
	private int gameID;

	private Random random = new Random();
	
	private String map = "default";
	private int playerLives = 3;
	
	private int missileCounter = 0;
	private int effectCounter = 0;

	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, PlayerStatistics> playerStats = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, ConnectionToClient> playerConnections = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, RocketLauncher> launchers = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Missile> rockets = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, Block> blocks = new ConcurrentHashMap<>();

	public GameLobby(String n, String hn, int mp, int gid, Server s) {
		lobbyName = n;
		hostUsername = hn;
		gameID = gid;
		playerCap = mp;
		server = s;
	}

	public int getGameID() {
		return gameID;
	}

	public String getlobbyName() {
		return lobbyName;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public ArrayList<String> getPlayerUsernames() {
		List<String> temp = playerStats.keySet().stream().collect(Collectors.toList());
		return new ArrayList<>(temp);
	}

	public ArrayList<PlayerJoinLeaveData> getJoinedPlayerInfo() {
		ArrayList<PlayerJoinLeaveData> joinedplayerInfo = new ArrayList<>();
		for (Entry<String, PlayerStatistics> e : playerStats.entrySet()) {
			PlayerJoinLeaveData player = new PlayerJoinLeaveData(e.getKey());
			player.setReady(e.getValue().isReady());
			player.setHost(hostUsername.equals(e.getKey()));
			player.setGameID(gameID);
			joinedplayerInfo.add(player);
		}
		return joinedplayerInfo;
	}

	public boolean isFull() {
		return playerCount == playerCap;
	}

	public boolean isEmpty() {
		return playerCount == 0;
	}

	public boolean isStarted() {
		return gameStarted;
	}
	
	public void broadcastLobbySettings(MatchSettings info) {
		map = info.getMap();
		playerLives = info.getPlayerLives();
		for (Entry<String, ConnectionToClient> e : playerConnections.entrySet()) {
			if (!e.getKey().toString().equals(hostUsername)) {
				try {
					e.getValue().sendToClient(info);
				} catch (IOException blyad) {
					
				}
			}
		}
	}
	
	public void startGame(StartGameData info) {
		blocks.clear();
		rockets.clear();
		launchers.clear();
		players.clear();
		for (PlayerStatistics r : playerStats.values()) {
			r.resetStats();
		}
		missileCounter = 0;
		effectCounter = 0;
		blocks.putAll(server.loadMap(info.getMap())); 
		CopyOnWriteArrayList<SpawnBlock> spawns = new CopyOnWriteArrayList<>();
		for (Block s : blocks.values()) {
			if (s instanceof SpawnBlock) {
				spawns.add((SpawnBlock) s);
			}
		}
		for (Entry<String, PlayerStatistics> e : playerStats.entrySet()) {
			Player newPlayer = new Player(20, random.nextInt(50, 850), random.nextInt(50, 850));
			RocketLauncher newLauncher = new RocketLauncher((int) newPlayer.getCenterX(), (int) newPlayer.getCenterY(), 24, 6);
			newPlayer.setUsername(e.getKey());
			newPlayer.setBlocks(blocks);
			newPlayer.setLives(info.getPlayerLives());
			newPlayer.setColor(new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255)));
			boolean spawned = false;
			while (!spawned) {
				int chosenSpawn = random.nextInt(spawns.size());
				if (spawns.get(chosenSpawn).isOccupied()) {
					spawned = false;
				} else {
					newPlayer.updatePosition((int) spawns.get(chosenSpawn).getCenterX(),
							(int) spawns.get(chosenSpawn).getCenterY());
					spawns.get(chosenSpawn).setOccupied(true);
					spawned = true;
				}
			}
			newLauncher.setOwner(newPlayer.getUsername());
			launchers.put(newPlayer.getUsername(), newLauncher);
			players.put(e.getKey(), newPlayer);
		}
			GenericRequest rq1 = new GenericRequest("GAME_STARTED");
		rq1.setData(players, "PLAYERS");
		rq1.setData(blocks, "MAP");
		updateClients(rq1);
		gameStarted = true;
	}
	
	public void stopGame() {
		gameStarted = false;
		Thread.currentThread().interrupt();
	}

	// TODO fix players leaving matches softlocking the lobby (i cannot think of any good reason why this happens but it does)
	public void removePlayer(ConnectionToClient c, PlayerJoinLeaveData usr) {
		playerCount -= 1;
		if (gameStarted) {
			GameEvent g = new GameEvent();
			EightBitLabel leftMessage = new EightBitLabel(usr.getUsername() + " left the match", Font.PLAIN, 25f);
			g.addEvent("LOG_MESSAGE", leftMessage);
			updateClients(g);
		} else {
			playerStats.remove(usr.getUsername());
			playerConnections.remove(usr.getUsername());
			if (usr.getUsername().equals(hostUsername)) {
				String[] usernames = playerStats.keySet().toArray(new String[0]);
				if (usernames.length > 0) {
					hostUsername = usernames[0];
				}
			}
			if (playerCount <= 0) {
				gameStarted = false;
				server.cancelGame(gameID, true);
			}
		}
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
	}

	public void addPlayer(ConnectionToClient c, PlayerJoinLeaveData usr) {
		PlayerStatistics temp = new PlayerStatistics();
		temp.setUsername(usr.getUsername());
		if (playerCount == 0) {
			hostUsername = usr.getUsername();
			temp.setHost();
		}
		playerCount += 1;
		playerConnections.put(usr.getUsername(), c);
		playerStats.put(usr.getUsername(), temp);
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
		try {
			StartGameData settings = new StartGameData(gameID, map, playerLives, false);
			playerConnections.get(usr.getUsername()).sendToClient(settings);
		} catch (IOException No) {
			
		}
	}

	public void readyPlayer(PlayerReadyData r) {
		String name = r.getUsername();
		playerStats.get(name).setReady(r.isReady());
		updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
	}

	public boolean playersReady() {
		boolean playersReady = true;
		for (PlayerStatistics p : playerStats.values()) {
			if (!p.isReady()) {
				playersReady = false;
			}
		}
		return playersReady;
	}

	public void updatePlayerInfoInLobbyForClients(ArrayList<PlayerJoinLeaveData> a) {
		GenericRequest rq = new GenericRequest("LOBBY_PLAYER_INFO");
		rq.setData(a);
		updateClients(rq);
	}

	public synchronized void updateClients(Object data) {
		for (ConnectionToClient c : playerConnections.values()) {
			try {
				c.sendToClient(data);
			} catch (IOException CLIENT_DOESNT_LIKE_YOU) {
				CLIENT_DOESNT_LIKE_YOU.printStackTrace();
			}
		}
	}
	
	public GameLobbyData generateGameListing() {
		GameLobbyData tempInfo = new GameLobbyData(lobbyName, hostUsername, playerCount, playerCap, gameID);
		tempInfo.setMaps(server.getMapNames());
		return tempInfo;
	}

	public synchronized void handlePlayerAction(PlayerAction a) {
		String usr = a.getUsername();
		String type = a.getType();
		switch (type) {
		case "MOVE":
			players.get(usr).updatePosition(a.getX(), a.getY());
			players.get(usr).setVelocity(a.getAction());
			break;
		case "CANCEL_MOVE":
			players.get(usr).updatePosition(a.getX(), a.getY());
			players.get(usr).cancelVelocity(a.getAction());
			break;
		case "LAUNCHER_ROTATION":
			launchers.get(usr).rotate(a.getMouseX(), a.getMouseY());
			break;
		case "ROCKET_FIRED":
			Missile missile = new Missile((int) launchers.get(usr).getCenterX(), (int) launchers.get(usr).getCenterY(), usr);
			missile.setDirection(a.getMouseX(), a.getMouseY());
			missile.setBlocks(blocks);
			missile.setPlayers(players);
			rockets.put(missileCounter, missile);
			a.setMissileNumber(missileCounter);
			missileCounter++;
			playerStats.get(a.getUsername()).incrementStat("rocketsFired");
			break;
		}
		updateClients(a);
	}

	@Override
	public void run() {
		while (gameStarted) {
			long startTime = System.currentTimeMillis();
			int remainingPlayers = 0;
			
			for (Entry<String, Player> p : players.entrySet()) {
				p.getValue().move();
				launchers.get(p.getKey()).moveLauncher((int) p.getValue().getCenterX(), (int) p.getValue().getCenterY(), p.getValue().getBlockSize());
				if (p.getValue().isAlive()) {
					remainingPlayers++;
				}
			}

			for (Iterator<Map.Entry<Integer, Missile>> it = rockets.entrySet().iterator(); it.hasNext();) {
				Map.Entry<Integer, Missile> entry = it.next();
				Missile r = entry.getValue();				
				r.move();				
				if (!r.isExploded()) {
					int col = r.checkBlockCollision();
					String hit = r.checkPlayerCollision();
					GameEvent g = new GameEvent();
					if (r.checkBoundaryCollision()) {
						g.addEvent("MISSILE_EXPLODES", entry.getKey());
						updateClients(g);
					} else if (col > 0) {
						g.addEvent("MISSILE_EXPLODES", entry.getKey());
						g.addEvent("BLOCK_DESTROYED", col);
						Explosion e = new Explosion(blocks.get(col).x, blocks.get(col).y);
						g.addEvent("ADD_EFFECT", e);
						effectCounter++;
						playerStats.get(r.getOwner()).incrementStat("blocksDestroyed");
						playerStats.get(r.getOwner()).addScore(5);
						blocks.remove(col);
						updateClients(g);
					} else if (!hit.isBlank()) {
						g.addEvent("MISSILE_EXPLODES", entry.getKey());
						Explosion e = new Explosion(players.get(hit).x, players.get(hit).y);
						g.addEvent("ADD_EFFECT", e);
						players.get(hit).takeHit();
						playerStats.get(r.getOwner()).incrementStat("eliminations");
						playerStats.get(r.getOwner()).addScore(50);
						playerStats.get(hit).incrementStat("deaths");
						EightBitLabel msg = new EightBitLabel("<html><font color='" + String.format("#%06X", (players.get(r.getOwner()).getColor().getRGB() & 0xFFFFFF)) + "'>" + r.getOwner() +"</font><font color='black'> exploded </font><font color ='" + String.format("#%06X", (players.get(hit).getColor().getRGB() & 0xFFFFFF)) + "'>" + hit + "</font>", Font.PLAIN, 25f);
						msg.setHorizontalAlignment(SwingConstants.LEFT);
						g.addEvent("LOG_MESSAGE", msg);
						if (!players.get(hit).isAlive()) {
							g.addEvent("PLAYER_ELIMINATED", hit);
							DeathMarker d = new DeathMarker(players.get(hit).x, players.get(hit).y, hit);
							d.setEffectNumber(effectCounter);
							d.setColor(players.get(hit).getColorFromWhenTheyWereNotDeadAsInAlive());
							g.addEvent("ADD_EFFECT", d);
							effectCounter++;
							
							
							EightBitLabel msg2 = new EightBitLabel("<html><font color='" + String.format("#%06X", (players.get(hit).getColor().getRGB() & 0xFFFFFF)) + "'>" + hit +"</font><font color='black'> WAS ELIMINATED BY </font><font color ='" + String.format("#%06X", (players.get(r.getOwner()).getColor().getRGB() & 0xFFFFFF)) + "'>" + r.getOwner() + "</font><font color ='black'>!</font>", Font.PLAIN, 25f);
							msg2.setHorizontalAlignment(SwingConstants.LEFT);
							g.addEvent("LOG_MESSAGE", msg2);
							
						} else {
							g.addEvent("PLAYER_HIT", hit);
						}
						
						
						updateClients(g);
					}
					if (r.isExploded()) {
						it.remove();
					}
				}
			}
			
			if (remainingPlayers == 1) {
				gameWon = true;
				gameStarted = false;
			}
			
			long endTime = System.currentTimeMillis();
			long delta = endTime - startTime;
			long sleepTime = TARGET_DELTA - delta;

			try {
				if (sleepTime <= 0) {
					sleepTime = TARGET_DELTA;
				}
				Thread.sleep(sleepTime);

			} catch (InterruptedException DEAD) {
				Thread.currentThread().interrupt();
				gameStarted = false;
			}
		}
		
		if (gameWon) {
			GameEvent g = new GameEvent();
			g.addEvent("GAME_END", "gg");
			updateClients(g);
			
			for (Entry<String, Player> e : players.entrySet()) {
				if (e.getValue().isAlive()) {
					playerStats.get(e.getKey()).addScore(100);
					playerStats.get(e.getKey()).incrementStat("wins");
				} else {
					playerStats.get(e.getKey()).incrementStat("losses");
				}
			}
			for (Entry<String, PlayerStatistics> e : playerStats.entrySet()) {
				e.getValue().setReady(false);
				server.submitPlayerStatsToDB(e.getKey(), e.getValue());
			}
			
			updatePlayerInfoInLobbyForClients(getJoinedPlayerInfo());
			
			EndGameData gameStats = new EndGameData();
			gameStats.setPlayers(players);
			gameStats.setStats(playerStats);
			updateClients(gameStats);
		}
	}
}
