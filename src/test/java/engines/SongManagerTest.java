package engines;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;

/**
 * Tests the SongManager accessor
 * Created by peter on 4/4/2017.
 */
public class SongManagerTest {
    private ArrayList<Integer> testIds = new ArrayList<>();
    private int originalNumberOfSongs;
    private final static int playlist_id = 289;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setUp() throws Exception {
        originalNumberOfSongs = SongManager.getAll(289).size();
        testIds.add(SongManager.add("The Hear and Know", playlist_id));
        testIds.add(SongManager.add("WockaWockaWocka", playlist_id));
        testIds.add(SongManager.add("The Hear and Know", playlist_id + 1));
        assertEquals(originalNumberOfSongs + 2, SongManager.getAll(289).size());
    }

    @After
    public void tearDown() throws Exception {
        for (int index : testIds) {
            SongManager.remove(index);
        }
        assertEquals(originalNumberOfSongs, SongManager.getAll(289).size());
    }

    @Test
    public void getLocation() throws Exception {
        boolean found = false;
        for (int index : testIds) {
            logger.debug("Location: " + SongManager.getLocation(index));
            if (SongManager.getLocation(index).equalsIgnoreCase("The Hear and Know")) {
                found = true;
            }
            assertTrue(found);
        }
    }

    @Test
    public void getSongIds() throws Exception {
        ArrayList<Integer> song_Ids = SongManager.getSongIds(playlist_id);
        assertEquals(2, song_Ids.size());
        assertEquals((song_Ids.get(0) + song_Ids.get(1)), testIds.get(0) + testIds.get(1));
    }

    @Test
    public void getPlaylistID() throws Exception {
        assertEquals(playlist_id, SongManager.getPlaylistID(testIds.get(1)));
    }

    @Test
    public void exists() throws Exception {
        assertTrue(SongManager.exists("The Hear and Know"));
        assertFalse(SongManager.exists("Well, what do you nose?"));
    }

    @Test
    public void getIDs() throws Exception {
        ArrayList<Integer> song_Ids = SongManager.getIDs("The Hear and Know");
        assertEquals(2, song_Ids.size());
        assertEquals(2,  (testIds.get(2) - testIds.get(0)));
    }

}