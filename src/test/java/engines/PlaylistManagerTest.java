package engines;

//import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import entity.Playlist;
import persistence.PlaylistDAO;
/**
 * Test the methods of the PlaylistManager class
 * Created by peter on 2/16/2017.
 */
public class PlaylistManagerTest {

    private final PlaylistManager playlistManager = new PlaylistManager();
    private final PlaylistDAO dao = new PlaylistDAO();
//    private final Logger logger = Logger.getLogger(this.getClass());
    private List<Playlist> originalPlaylistList;
    private ArrayList<Integer> bogusList = new ArrayList<>();
    private Playlist playlist;

    /**
     * Creates some bogus playlist table entries to manipulate during testing
     * @throws Exception general exception
     */
    @Before
    public void setup() throws Exception {

        originalPlaylistList = dao.getAll();
        playlist = new Playlist("Boogie", 27);
        bogusList.add(dao.add(playlist));
        playlist = new Playlist("Andrews Sisters", 27);
        bogusList.add(dao.add(playlist));
        playlist = new Playlist("Woogie", 987);
        bogusList.add(dao.add(playlist));
        playlist = new Playlist("Bugle Boy", 75);
        bogusList.add(dao.add(playlist));
    }

    @Test
    public void TestGetIDs() throws Exception {

        ArrayList<Integer> listIDs = playlistManager.getIDs(987);
        assertEquals("Wrong number of matching playlists", 1, listIDs.size());
        for (int index : listIDs) {
            playlist = dao.get(index);
            assertEquals("Names don't match", "Woogie", playlist.getName());
        }
    }

    @Test
    public void TestRename() throws Exception {

        int index = bogusList.get(0);
        assertEquals("This should have returned zero: ", 0,
                playlistManager.rename(25, index, "Honky Tonk"));

        index = bogusList.get(1);
        assertEquals("This should have matched non-zero : ", index,
                playlistManager.rename(27, index, "Honky Cat"));

        index = bogusList.get(2);
        assertEquals("This also should have matched non-zero : ", index,
                playlistManager.rename(987, index, "Tonka Truck"));
    }

    @After
    public void cleanup() throws Exception {

        for (int index : bogusList) { // loop over new playlists
            dao.remove(index);
        }
        List<Playlist> finalPlaylistList = dao.getAll();

        assertEquals("Didn't return playlists table to original state: ", finalPlaylistList.size(), originalPlaylistList.size());
    }
}
