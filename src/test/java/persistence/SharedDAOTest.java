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
    final private GroupDAO groupDAO = new GroupDAO();
    final private UserDAO userDAO = new UserDAO();
    final private List<User> users = userDAO.getAll();
    private User user = userDAO.getAll().get(0);
    private Playlist playlist;
    private int newSharedID;
    private int originalNumberOfShareds;
    private Shared shared;

    @Before
    public void setup() throws Exception {
        originalNumberOfShareds = dao.getAll().size();
        playlist = new Playlist("Sinester", userDAO.getAll().get(1));
        playlist.setPlaylist_id(playlistDAO.create(playlist));

        newSharedID = dao.share(playlist.getPlaylist_id(), user.getId());
        shared = dao.read(newSharedID);
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        assertEquals("Added one, but found ", 1, dao.getAll().size() - originalNumberOfShareds);
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
        assertEquals(newSharedID, dao.find(playlist.getPlaylist_id(), user.getId()));
    }

    @Test
    public void testGetReceivedPlaylists() {

        List<Playlist> thisList = dao.getReceivedPlaylists(user.getId());
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (Playlist pList : thisList) {
            if (pList.getPlaylist_id() == playlist.getPlaylist_id()) {
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
        for (User thisUser : dao.sharing(playlist.getPlaylist_id())) {
            if (thisUser.getId() == user.getId()) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testNotSharing() throws Exception{
        boolean found = false;
        Group group2 = new Group("test");
        group2.setId(groupDAO.create(group2));
        User not = new User("notnotnot", "notnotnot", "notnotnot", "administrator", group2);
        userDAO.create(not);
        for (User thisUser : dao.notSharing(playlist.getPlaylist_id())) {
            if (thisUser.getId() == not.getId()) found = true;
        }
        assertTrue(found);
        userDAO.delete(not);
        groupDAO.delete(group2);
    }

    @Test
    public void testIsShared() throws Exception{
        assertTrue(dao.isShared(playlist.getPlaylist_id()));
        assertFalse(dao.isShared(-12));
    }

    @Test
    public void testSCD() throws Exception {
        Shared tempShare;
        Playlist tempList;
        tempList = new Playlist("testTest", users.get(2));
        tempList.setPlaylist_id(playlistDAO.create(tempList));
        tempShare = new Shared(tempList, users.get(1));
        tempShare.setId(dao.create(tempShare));
        dao.read(tempShare.getId());
        dao.delete(tempShare);
        playlistDAO.delete(tempList);
    }

    @After
    public void cleanup() throws Exception {
        dao.remove(user.getId(), playlist.getPlaylist_id());
        playlistDAO.delete(playlist);
        assertEquals("Added and removed one, but found ", 0, dao.getAll().size() - originalNumberOfShareds);
    }
}