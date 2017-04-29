package persistence;

import entity.Playlist;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import entity.User;

import static org.junit.Assert.*;

/**
 * Database accessor test
 * Created by peter on 2/13/2017.
 */
public class PlaylistDAOTest {

    final private PlaylistDAO dao = new PlaylistDAO();
    final private UserDAO userDAO = new UserDAO();
    private User user = userDAO.getAll().get(0);
    private Playlist playlist;
    private int numberOfPlaylists;
    private int originalNumberOfPlaylists;
    private int newPlaylistID;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setup() throws Exception {
        originalNumberOfPlaylists = dao.getAll().size();
        playlist = new Playlist("Sinester", user);
        newPlaylistID = dao.create(playlist);
        numberOfPlaylists = dao.getAll().size();
        assertEquals("Added one, but found ", 1, numberOfPlaylists - originalNumberOfPlaylists);
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (entity.Playlist playlist : dao.getAll()) {
            String thisName = playlist.getName();
            if (thisName.equalsIgnoreCase("Sinester")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        assertEquals("Names don't match", "Sinester", dao.read(newPlaylistID).getName());
    }

    @Test
    public void testModifyPlaylistName() throws Exception {
        playlist.setName("Bubbles");
        dao.update(playlist);
        playlist = dao.read(newPlaylistID);
        assertEquals("Playlistname not modified", "Bubbles", playlist.getName());
    }

    @After
    public void cleanup() throws Exception {
        dao.delete(playlist);
        numberOfPlaylists = dao.getAll().size();
        assertEquals("Added and removed one, but found ", 0, numberOfPlaylists - originalNumberOfPlaylists);

    }
}