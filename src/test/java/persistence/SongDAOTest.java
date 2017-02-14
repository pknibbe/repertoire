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
 * Created by peter on 2/13/2017.
 */
public class SongDAOTest {
    
    SongDAO dao;
    entity.Song song;
    int numberOfSongs;
    List<Song> songList;
    final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setup() throws Exception {
        dao = new SongDAO();
        songList = dao.getAll();
        logger.info("In @Before, songList has " + songList.size() + "entries");
        justAdd(); // make sure table is not empty for purpose of test
        songList = dao.getAll();
        logger.info("After justAdd, songList has " + songList.size() + "entries");
        numberOfSongs = songList.size();
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (entity.Song song : songList) {
            String thisName = song.getName();
            if (thisName.equalsIgnoreCase("Johanna")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        song = songList.get(songList.size() - 1); // retrieve most recent addition to table
        int id = song.getId(); // get the id of the most recent addition
        song = dao.get(id); // get the song by id
        assertEquals("Names don't match", "Johanna", song.getName());
    }

    @Test
    public void testAdd() throws Exception {
        justAdd();
        songList = dao.getAll();
        assertEquals("Add did not work: ", numberOfSongs + 1, songList.size());
    }

    @Test
    public void testModifySongName() throws Exception {
        song = songList.get(songList.size() - 1); // retrieve most recent addition to table
        int id = song.getId();
        song.setName("Rosie");
        song.setLocation("Rose");
        song.setPerformer("Beth");
        song.setDuration("4:23");
        logger.info(song.toString());
        logger.info("Updated song ID = " + dao.modify(song));
        song = dao.get(id);
        assertEquals("Songname not modified", "Rosie", song.getName());
        assertEquals("Location not modified", "Rose", song.getLocation());
        assertEquals("Performer not modified", "Beth", song.getPerformer());
        assertEquals("Duration not modified", "4:23", song.getDuration());
        songList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfSongs, songList.size());
    }

    private void justAdd() {
        song = new entity.Song("Johanna", "Johanna", "Johanna", "5:03");
        dao.add(song);
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
            String thisName = song.getName();
            if (thisName.equalsIgnoreCase("Johanna")) {
                dao.remove(song.getId());
            } else             if (thisName.equalsIgnoreCase("Rosie")) {
                dao.remove(song.getId());
            }
        }
    }
}