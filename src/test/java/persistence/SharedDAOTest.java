package persistence;

import static org.junit.Assert.*;
import entity.Shared;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Database accessor test
 * Created by peter on 3/28/2017.
 */
public class SharedDAOTest {

    private SharedDAO dao;
    private Shared playlist;
    private int numberOfPlaylists;
    private int originalNumberOfPlaylists;
    private List<Shared> playlistList;
    private final Logger logger = Logger.getLogger(this.getClass());
    private ArrayList<Integer> sharingIDs = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        dao = new SharedDAO();
        playlistList = dao.getAll();
        originalNumberOfPlaylists = playlistList.size();
        logger.info("In @Before, PlaylistList has " + playlistList.size() + "entries");
        playlist = new Shared(1, 2); // make sure table is not empty for purpose of test
        sharingIDs.add(dao.add(playlist));
        playlist = new Shared(3, 4); // make sure table is not empty for purpose of test
        sharingIDs.add(dao.add(playlist));
        playlist = new Shared(2, 3); // make sure table is not empty for purpose of test
        sharingIDs.add(dao.add(playlist));
        playlistList = dao.getAll();
        logger.info("After justAdd, PlaylistList has " + playlistList.size() + "entries");
        numberOfPlaylists = playlistList.size();
    }

    @Test
    public void testGetAll() throws Exception {
        for (Integer index : sharingIDs) { // Loop over the entries created in the Before section
            playlist = dao.get(index);
            assertTrue("The expected relationship was not found: ",
                    (playlist.getPlaylist_id() == (playlist.getShared_with() - 1)));
        }
    }

    @Test
    public void testGet() throws Exception {
        playlist = dao.get(sharingIDs.get(0)); // retrieve the first addition to table
        assertEquals("user_id wrong: ", playlist.getShared_with(), 2);
    }

    @Test
    public void testAdd() throws Exception {
        assertEquals("Add did not work: ", numberOfPlaylists -3, originalNumberOfPlaylists);
    }

    @Test
    public void testRemove() throws Exception {
        for (Integer index : sharingIDs) { // Loop over the entries created in the Before section
            dao.remove(index);
        }
        playlistList = dao.getAll();
        assertEquals("remove did not work: ", originalNumberOfPlaylists, playlistList.size());
    }

}