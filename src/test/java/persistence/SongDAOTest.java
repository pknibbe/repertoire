package persistence;

import entity.Song;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the SongDAO methods
 * Created by peter on 2/13/2017.
 */
public class SongDAOTest {
    
    private SongDAO dao;
    private entity.Song song;
    private int numberOfSongs;
    private List<Song> songList;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setup() throws Exception {
        dao = new SongDAO();
        songList = dao.getAll();
        logger.info("In @Before, songList has " + songList.size() + "entries");
        song = new Song("ZXGK It's Magic", "Great tune", 3);
        dao.add(song);
        songList = dao.getAll();
        logger.info("After setup, songList has " + songList.size() + "entries");
        numberOfSongs = songList.size();
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (entity.Song song : songList) {
            String thisName = song.getLocation();
            if (thisName.equalsIgnoreCase("ZXGK It's Magic")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        song = songList.get(songList.size() - 1); // retrieve most recent addition to table
        int id = song.getId(); // get the id of the most recent addition
        song = dao.get(id); // get the song by id
        assertEquals("Names don't match", "ZXGK It's Magic", song.getLocation());
    }

    @Test
    public void testAdd() throws Exception {
        song = new Song("ZXGK of Earl", "Great tune",  3);
        dao.add(song);
        songList = dao.getAll();
        assertEquals("Add did not work: ", numberOfSongs + 1, songList.size());
    }

    @Test
    public void testModifySong() throws Exception {
        song = songList.get(songList.size() - 1); // retrieve most recent addition to table
        int id = song.getId();
        song.setLocation("ZXGK Submarine");
        song.setDescription("Corny, but fun");
        logger.info(song.toString());
        logger.info("Updated song ID = " + dao.modify(song));
        song = dao.get(id);
        assertEquals("Location not modified", "ZXGK Submarine", song.getLocation());
        assertEquals("Description not modified", "Corny, but fun", song.getDescription());
        songList = dao.getAll();
        assertEquals("Modify changed the number of entries!", numberOfSongs, songList.size());
    }

    @Test
    public void testRemove() throws Exception {
        song = songList.get(songList.size() - 1); // retrieve most recent addition to table
        int id = song.getId();
        dao.remove(id);
        songList = dao.getAll();
        assertEquals("remove did not work: ", numberOfSongs - 1, songList.size());
    }

    @After
    public void cleanup() throws Exception {
        songList = dao.getAll();

        for (entity.Song song : songList) {
            String thisName = song.getLocation();
            if (thisName.equalsIgnoreCase("ZXGK Submarine")) {
                dao.remove(song.getId());
            } else             if (thisName.equalsIgnoreCase("ZXGK of Earl")) {
                dao.remove(song.getId());
            } else             if (thisName.equalsIgnoreCase("ZXGK It's Magic")) {
                dao.remove(song.getId());
            }
        }
    }
}