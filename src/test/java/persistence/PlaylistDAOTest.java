package persistence;

import entity.Playlist;
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
    private User user = userDAO.read(userDAO.getAdminId());
    private Playlist playlist;
    private int originalCount;

    @Before
    public void setup() throws Exception {
        originalCount = dao.getAll().size();
        playlist = new Playlist("Sinester", user);
        playlist.setPlaylist_id(dao.create(playlist));
   }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;
        assertEquals("Added one, but found ", 1, dao.getAll().size() - originalCount);

        for (entity.Playlist playlist : dao.getAll()) {
            String thisName = playlist.getName();
            if (thisName.equalsIgnoreCase("Sinester")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGetAllMine() throws Exception {
        boolean found = false;
        Playlist tempList = new Playlist("Cookoo", user);
        dao.create(tempList);

        for (entity.Playlist playlist : dao.getAllMine(user.getId())) {
            String thisName = playlist.getName();
            if (thisName.equalsIgnoreCase("Cookoo")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
        dao.delete(tempList);
    }

    @Test
    public void testGet() throws Exception {
        assertEquals("Names don't match", "Sinester", dao.read(playlist.getPlaylist_id()).getName());
    }

    @Test
    public void testModifyPlaylistName() throws Exception {
        playlist.setName("Bubbles");
        dao.update(playlist);
        playlist = dao.read(playlist.getPlaylist_id());
        assertEquals("Playlistname not modified", "Bubbles", playlist.getName());
        dao.remove(playlist.getPlaylist_id(), playlist.getOwner().getId());
        playlist.setPlaylist_id(dao.create(playlist));
    }

    @After
    public void cleanup() throws Exception {
        dao.remove(playlist.getPlaylist_id(), playlist.getOwner().getId());
        assertEquals("Added and removed one, but found ", 0, dao.getAll().size() - originalCount);

    }
}