package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Server;

public class ServerTest {

    private Server server;

    @Before
    public void setUp() {
        server = new Server();
    }

    @After
    public void tearDown() {
        server = null;
    }

    @Test
    public void testLoadMap() {
        ArrayList<String> mapNames = server.getMapNames();
        assertNotNull(mapNames);
        assertTrue(mapNames.size() > 0);

        String firstMap = mapNames.get(0);
        assertNotNull(server.loadMap(firstMap));
    }

    @Test
    public void testGetConnectedPlayers() {
        assertNotNull(server.getConnectedPlayers());
        assertEquals(0, server.getConnectedPlayers().size());
    }

    @Test
    public void testGetConnectedPlayerCount() {
        assertEquals(0, server.getConnectedPlayerCount());
    }

    @Test
    public void testGetGames() {
        assertNotNull(server.getGames());
        assertEquals(0, server.getGames().size());
    }

    @Test
    public void testGetAllGames() {
        assertNotNull(server.getAllGames());
        assertEquals(0, server.getAllGames().size());
    }
}
