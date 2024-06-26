package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.Database;

public class DatabaseTest {
    private Database database;
    private Connection conn;

    @Before
    public void setUp() {
        database = new Database();
        conn = database.getConnection(); // Accessing the connection directly
    }

    @After
    public void tearDown() {
        database.closeConnection();
    }

    @Test
    public void testDatabaseConnection() {
        assertNotNull(conn);
    }

    @Test
    public void testVerifyAccount() {
        assertTrue(database.verifyAccount("mason", "yourmom!"));
        assertFalse(database.verifyAccount("mason", "wrongpassword"));
    }

    @Test
    public void testCreateNewAccount() {
        assertTrue(database.createNewAccount("testuser", "testpassword"));
        assertTrue(database.verifyAccount("testuser", "testpassword"));
    }

    @Test
    public void testGetStatistics() {
        int[] statistics = database.getStatistics("noble");
        assertEquals(5, statistics[0]); // Wins
        assertEquals(1, statistics[1]); // Losses
        assertEquals(98, statistics[2]); // Eliminations
        assertEquals(1, statistics[3]); // Deaths
        assertEquals(100, statistics[4]); // Rockets Fired
        assertEquals(500, statistics[5]); // Blocks Destroyed
        assertEquals(0, statistics[6]); // Damage Dealt
        assertEquals(0, statistics[7]); // Total Score
    }
}
