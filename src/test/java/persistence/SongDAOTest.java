package persistence;

import entity.Song;
import entity.Playlist;
import entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests the SongDAO methods
 * Created by peter on 2/13/2017.
 */
public class SongDAOTest {
    
    final private SongDAO dao = new SongDAO();
    final private PlaylistDAO playlistDAO = new PlaylistDAO();
    final private UserDAO userDAO = new UserDAO();
    private User user = userDAO.read(userDAO.getAdminId());
    private Song song;
    private int originalNumberOfSongs;
    private Playlist playlist;

    @Before
    public void setup() throws Exception {
        originalNumberOfSongs = dao.getAll().size();
        playlist = new Playlist("Sinester", user);
        playlist.setPlaylist_id(playlistDAO.create(playlist));

        song = new Song("ZXGK It's Magic", "Great tune", playlist);
        song.setId(dao.create(song));
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        assertEquals("Added one, but found ", 1, dao.getAll().size() - originalNumberOfSongs);
        for (Song song : dao.getAll()) {
            String thisName = song.getLocation();
            if (thisName.equalsIgnoreCase("ZXGK It's Magic")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGetAllThese() throws Exception {
        List<Song> songs = dao.getAllThese(playlist.getPlaylist_id());
        boolean found = false;
        for (Song thisSong : songs) {
            if (thisSong.getLocation().equalsIgnoreCase(song.getLocation())) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testAlreadyThere() throws Exception {
        assertTrue(dao.alreadyThere(song.getLocation(), playlist.getPlaylist_id()));
        assertFalse(dao.alreadyThere("YellowBrickRoad", playlist.getPlaylist_id()));
    }

    @Test
    public void testExists() throws Exception {
        assertTrue(dao.exists(song.getLocation()));
        assertFalse(dao.exists("YellowBrickRoad"));
    }


    @Test
    public void testGetLocation() throws Exception {
        assertTrue(dao.getLocation(song.getId()).equalsIgnoreCase("ZXGK It's Magic"));
    }

    @Test
    public void testGetSongIDs() throws Exception {
        boolean found = false;
        List<Integer> songIds = dao.getSongIds(playlist.getPlaylist_id());
        for (int index : songIds) {
            if (index == song.getId()) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGet() throws Exception {
        assertEquals("Names don't match", "ZXGK It's Magic", dao.read(song.getId()).getLocation());
    }

    @Test
    public void testModifySong() throws Exception {
        song.setLocation("ZXGK Submarine");
        song.setDescription("Corny, but fun");
        dao.update(song);
        song = dao.read(song.getId());
        assertEquals("Location not modified", "ZXGK Submarine", song.getLocation());
        assertEquals("Description not modified", "Corny, but fun", song.getDescription());
    }

    @Test
    public void testCD() throws Exception {
        int sID = song.getId();
        Song newSong = dao.read(sID);
        dao.delete(newSong);
        song.setId(dao.create(song));
    }

    @After
    public void cleanup() throws Exception {
        dao.delete(dao.read(song.getId()));
        playlistDAO.delete(playlist);
        assertEquals("Added and removed one, but found ", 0, dao.getAll().size() - originalNumberOfSongs);
    }
}