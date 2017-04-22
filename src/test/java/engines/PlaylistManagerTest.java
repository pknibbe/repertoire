package engines;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import entity.Playlist;
import static org.junit.Assert.*;

/**
 * Created by peter on 4/5/2017.
 */
public class PlaylistManagerTest {
    private ArrayList<Integer> testIds = new ArrayList<>();
    private int originalNumberOfPlaylists;
    private final Logger logger = Logger.getLogger(this.getClass());
    private int song_id;

    @Before
    public void setUp() throws Exception {
        originalNumberOfPlaylists = PlaylistManager.getIDs(89).size();
        testIds.add(PlaylistManager.add(89, "Winnie the Pooh"));
        testIds.add(PlaylistManager.add(89, "Christopher Robbin Milne"));
        testIds.add(PlaylistManager.add(90, "Tigger"));
        testIds.add(PlaylistManager.add(91, "Piglet"));
        song_id = SongManager.add("Way down Yonder", 112);

    }

    @After
    public void tearDown() throws Exception {
        for (Integer index : testIds) PlaylistManager.remove(index);
        assertEquals("Test changed the table! ", originalNumberOfPlaylists, PlaylistManager.getIDs(89).size());
        SongManager.remove(song_id);
    }

    @Test
    public void get() throws Exception {
        Playlist playlist = PlaylistManager.get(testIds.get(0)); // Winnie the Pooh
        assertEquals(89, playlist.getOwner().getId());
        assertEquals("Winnie the Pooh", playlist.getName());
    }

    @Test
    public void getID() throws Exception {
        assertEquals(testIds.get(0), (Integer) PlaylistManager.getID(89, "Winnie the Pooh"));
    }

    @Test
    public void getIDs() throws Exception {
        ArrayList<Integer> ids = PlaylistManager.getIDs(90);
        Playlist playlist = PlaylistManager.get(ids.get(0));
        assertEquals("Tigger", playlist.getName());
    }

    @Test
    public void alreadyThere() throws Exception {
        assertTrue(PlaylistManager.alreadyThere("Way down Yonder", 112));
        assertFalse(PlaylistManager.alreadyThere("Way down Yonder", 113));
    }

    @Test
    public void rename() throws Exception {
        PlaylistManager.rename(90, testIds.get(2), "Owl");
        assertEquals(testIds.get(2), (Integer) PlaylistManager.getID(90, "Owl"));
    }
}