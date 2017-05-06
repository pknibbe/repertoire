package persistence;

import static org.junit.Assert.*;
import entity.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.List;

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
    Shared shared;

    @Before
    public void setup() throws Exception {
        originalNumberOfShareds = dao.getAll().size();
        playlist = new Playlist("Sinester", userDAO.getAll().get(1));
        newPlaylistID = playlistDAO.create(playlist);

        newSharedID = dao.share(newPlaylistID, user.getId());
        shared = dao.read(newSharedID);
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
        Shared thisShared = dao.read(newSharedID);
        assertEquals("Wrong user ", user.getId(), thisShared.getRecipient().getId());
        assertEquals("Wrong playlist ", playlist.getPlaylist_id(), thisShared.getPlaylist().getPlaylist_id());
    }

    @Test
    public void testFind() {
        assertEquals(newSharedID, dao.find(newPlaylistID, user.getId()));
    }

    @Test
    public void testGetReceivedPlaylists() {

        List<Playlist> thisList = dao.getReceivedPlaylists(user.getId());
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (Playlist pList : thisList) {
            if (pList.getPlaylist_id() == newPlaylistID) {
                foundFirst = true;
            } else if (pList.getPlaylist_id() == -5) {
                foundSecond = true;
            }
        }
        assertTrue(foundFirst);
        assertFalse(foundSecond);
    }

    @Test
    public void testSharing() {
        boolean found = false;
        for (User thisUser : dao.sharing(newPlaylistID)) {
            if (thisUser.getId() == user.getId()) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testNotSharing() {
        boolean found = false;
        User not = new User("notnotnot", "notnotnot", "notnotnot", "administrator");
        userDAO.create(not);
        for (User thisUser : dao.notSharing(newPlaylistID)) {
            if (thisUser.getId() == not.getId()) found = true;
        }
        assertTrue(found);
        userDAO.delete(not);
    }

    @Test
    public void testIsShared() {
        assertTrue(dao.isShared(newPlaylistID));
        assertFalse(dao.isShared(-12));
    }

    @After
    public void cleanup() throws Exception {
        dao.remove(user.getId(), newPlaylistID);
        playlistDAO.delete(playlist);
        numberOfShareds = dao.getAll().size();
        assertEquals("Added and removed one, but found ", 0, numberOfShareds - originalNumberOfShareds);
    }
}