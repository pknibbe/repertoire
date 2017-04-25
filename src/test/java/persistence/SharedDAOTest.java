package persistence;

import static org.junit.Assert.*;
import entity.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Database accessor test
 * Created by peter on 3/28/2017.
 */
@SuppressWarnings("CanBeFinal")
public class SharedDAOTest {

    final private SharedDAO dao = new SharedDAO();
    final private PlaylistDAO playlistDAO = new PlaylistDAO();
    final private UserDAO userDAO = new UserDAO();
    private User user = userDAO.getAll().get(0);
    private Playlist playlist;
    private int newSharedID;
    private int newPlaylistID;
    private int originalNumberOfShareds;
    private int numberOfShareds;

    @Before
    public void setup() throws Exception {
        originalNumberOfShareds = dao.getAll().size();
        playlist = new Playlist("Sinester", user);
        newPlaylistID = playlistDAO.add(playlist);

        Shared shared = new Shared(playlist, user);
        newSharedID = dao.add(shared);
        numberOfShareds = dao.getAll().size();
        assertEquals("Added one, but found ", 1, numberOfShareds - originalNumberOfShareds);
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (Shared pain : dao.getAll()) {
            User thisUser = pain.getRecipient();
            Playlist thisPlaylist = pain.getPlaylist();
            if ((thisUser.getId() == user.getId()) && (thisPlaylist.getPlaylist_id() == playlist.getPlaylist_id())) {
                found = true;
            }
        }
        assertTrue("The expected record was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        Shared thisShared = dao.get(newSharedID);
        assertEquals("Wrong user ", user.getId(), thisShared.getRecipient().getId());
        assertEquals("Wrong playlist ", playlist.getPlaylist_id(), thisShared.getPlaylist().getPlaylist_id());
    }

    @Test
    public void testShare() {}

    @Test
    public void testFind() {}

    @Test
    public void testGetAllByID() {}

    @Test
    public void testSharing() {}

    @Test
    public void testNotSharing() {}

    @Test
    public void testIsShared() {}

    @After
    public void cleanup() throws Exception {
        dao.remove(newSharedID);
        playlistDAO.remove(newPlaylistID);
        numberOfShareds = dao.getAll().size();
        assertEquals("Added and removed one, but found ", 0, numberOfShareds - originalNumberOfShareds);
    }
}