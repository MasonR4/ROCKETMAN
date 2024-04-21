package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import data.PlayerStatistics;

public class Database {
	// Database connection
	private Connection conn;

	public Database() {
		// Initialize the database connection in the constructor
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("server/db.properties"));
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String password = p.getProperty("password");
			conn = DriverManager.getConnection(url, user, password);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
        return conn;
    }

	// Method for verifying a username and password.
	public boolean verifyAccount(String username, String password) {
		String query = "SELECT username FROM userData WHERE username = ? AND password = AES_ENCRYPT(?, 'key')";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, username);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
	            return rs.next(); // returns true if a record is found, meaning the login is successful
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method for creating a new account.
	public boolean createNewAccount(String username, String password) {
	    // First, check if the account already exists to avoid duplicate entries
	    if (verifyAccount(username, password)) {
	        return false; // Account already exists
	    }

	    // If the account does not exist, create a new one
	    // Ensure the password is encrypted before being inserted
	    String insertDML = "INSERT INTO userData(username, password) VALUES (?, AES_ENCRYPT(?, 'key'))";
	    try (PreparedStatement ps = conn.prepareStatement(insertDML)) {
	        ps.setString(1, username);
	        ps.setString(2, password);
	        int affectedRows = ps.executeUpdate();
	        return affectedRows > 0 && putStatsForNewPlayers(username);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }

	}

	private boolean putStatsForNewPlayers(String username) {
	    String insertStats = "INSERT INTO statistics VALUES('" + username + "', 0, 0, 0, 0, 0, 0, 0, 0)";
	    try (PreparedStatement ps = conn.prepareStatement(insertStats)) {
	    	int affectedRows = ps.executeUpdate();
	    	return affectedRows > 0;
	    } catch (SQLException gg) {
	    	gg.printStackTrace();
	    	return false;
	    }
	}

	// Close the database connection when it's no longer needed
	public void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int[] getStatistics(String username) {
        int[] statistics = new int[8]; // Array to store statistics
        String query = "SELECT * FROM statistics WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Retrieve statistics from the result set
                    statistics[0] = rs.getInt("wins");
                    statistics[1] = rs.getInt("losses");
                    statistics[2] = rs.getInt("eliminations");
                    statistics[3] = rs.getInt("deaths");
                    statistics[4] = rs.getInt("rocketsFired");
                    statistics[5] = rs.getInt("blocksDestroyed");
                    statistics[6] = rs.getInt("damageDealt");
                    statistics[7] = rs.getInt("totalScore");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("db succes");
        return statistics;
    }

	public double[] getAverages (int[] stats) {
		double[] averages = new double[2];

		// W/L Avg
		averages[0] = stats[0]/stats[1];

		// KDA
		averages[1] = stats[2]/stats[3];

		return averages;
	}

	public boolean insertPlayerStatistics(PlayerStatistics playerStats) {
	    // SQL query to insert player statistics
	    String updateQuery = "UPDATE statistics SET wins = wins + ?, losses = losses + ?, eliminations = eliminations + ?, deaths = deaths + ?, rocketsFired = rocketsFired + ?, blocksDestroyed = blocksDestroyed + ?, damageDealt = damageDealt + ?, totalScore = totalScore + ? WHERE username = ?";
		try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
		    ps.setInt(1, playerStats.getStat("wins"));
		    ps.setInt(2, playerStats.getStat("losses"));
		    ps.setInt(3, playerStats.getStat("eliminations"));
		    ps.setInt(4, playerStats.getStat("deaths"));
		    ps.setInt(5, playerStats.getStat("rocketsFired"));
		    ps.setInt(6, playerStats.getStat("blocksDestroyed"));
		    ps.setInt(7, playerStats.getStat("damageDealt"));
		    ps.setInt(8, playerStats.getScore());
		    ps.setString(9, playerStats.getUsername());

		    // Execute the update query
		    int rowsAffected = ps.executeUpdate();

		    // Return true if the update was successful
		    return rowsAffected > 0;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false;
		}

	}

}
