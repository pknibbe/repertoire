package persistence;

import entity.Song;
import entity.Playlist;
import entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the SongDAO methods
 * Created by peter on 2/13/2017.
 */
public class SongDAOTest {
    
    final private SongDAO dao = new SongDAO();
    final private PlaylistDAO playlistDAO = new PlaylistDAO();
    final private UserDAO userDAO = new UserDAO();
    private User user = userDAO.getAll().get(0);
    private int newPlaylistID;
    private Song song;
    private int newSongID;
    private int numberOfSongs;
    private int originalNumberOfSongs;


    @Before
    public void setup() throws Exception {
        originalNumberOfSongs = dao.getAll().size();
        Playlist playlist = new Playlist("Sinester", user);
        newPlaylistID = playlistDAO.add(playlist);

        song = new Song("ZXGK It's Magic", "Great tune", playlist);
        newSongID = dao.add(song);
        numberOfSongs = dao.getAll().size();
        assertEquals("Added one, but found ", 1, numberOfSongs - originalNumberOfSongs);
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (Song song : dao.getAll()) {
            String thisName = song.getLocation();
            if (thisName.equalsIgnoreCase("ZXGK It's Magic")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGetAllThese() throws Exception {}

    @Test
    public void testAlreadyThere() throws Exception {}

    @Test
    public void testExists() throws Exception {}

    @Test
    public void testGetRepository() throws Exception {}

    @Test
    public void testGetLocation() throws Exception {}

    @Test
    public void testGetAllByID() throws Exception {}

    @Test
    public void testGetSongIDs() throws Exception {}

    @Test
    public void testGet() throws Exception {
        assertEquals("Names don't match", "ZXGK It's Magic", dao.get(newSongID).getLocation());
    }

    @Test
    public void testModifySong() throws Exception {
        song.setLocation("ZXGK Submarine");
        song.setDescription("Corny, but fun");
        dao.modify(song);
        song = dao.get(newSongID);
        assertEquals("Location not modified", "ZXGK Submarine", song.getLocation());
        assertEquals("Description not modified", "Corny, but fun", song.getDescription());
    }

    @After
    public void cleanup() throws Exception {
        dao.remove(newSongID);
        playlistDAO.remove(newPlaylistID);
        numberOfSongs = dao.getAll().size();
        assertEquals("Added and removed one, but found ", 0, numberOfSongs - originalNumberOfSongs);
    }
}