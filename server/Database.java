package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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

	// Method for verifying a username and password.
	public boolean verifyAccount(String username, String password) {
		String query = "SELECT * FROM userData WHERE username = ? AND password = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			return rs.next(); // return true if the account exists
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
		String insertDML = "INSERT INTO userData(username, password) VALUES (?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(insertDML)) {
			ps.setString(1, username);
			ps.setString(2, password);
			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
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
}
