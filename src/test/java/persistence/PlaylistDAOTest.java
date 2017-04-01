package persistence;

import entity.Playlist;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Database accessor test
 * Created by peter on 2/13/2017.
 */
public class PlaylistDAOTest {

    private PlaylistDAO dao;
    private entity.Playlist playlist;
    private int numberOfPlaylists;
    private List<Playlist> playlistList;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setup() throws Exception {
        dao = new PlaylistDAO();
        playlistList = dao.getAll();
        logger.debug("In @Before, PlaylistList has " + playlistList.size() + "entries");
        justAdd(); // make sure table is not empty for purpose of test
        playlistList = dao.getAll();
        logger.debug("After justAdd, PlaylistList has " + playlistList.size() + "entries");
        numberOfPlaylists = playlistList.size();
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (entity.Playlist playlist : playlistList) {
            String thisName = playlist.getName();
            if (thisName.equalsIgnoreCase("Sinester")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        playlist = playlistList.get(playlistList.size() - 1); // retrieve most recent addition to table
        int id = playlist.getId(); // get the id of the most recent addition
        playlist = dao.get(id); // get the Playlist by id
        assertEquals("Names don't match", "Sinester", playlist.getName());
    }

    @Test
    public void testAdd() throws Exception {
        justAdd();
        playlistList = dao.getAll();
        assertEquals("Add did not work: ", numberOfPlaylists + 1, playlistList.size());
    }

    @Test
    public void testModifyPlaylistName() throws Exception {
        playlist = playlistList.get(playlistList.size() - 1); // retrieve most recent addition to table
        int id = playlist.getId();
        playlist.setName("Bubbles");
        dao.modify(playlist);
        playlist = dao.get(id);
        assertEquals("Playlistname not modified", "Bubbles", playlist.getName());
        playlistList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfPlaylists, playlistList.size());
    }

    private void justAdd() {
        playlist = new entity.Playlist("Sinester", 2);
        dao.add(playlist);
    }

    @Test
    public void testRemove() throws Exception {
        playlist = playlistList.get(playlistList.size() - 1); // retrieve most recent addition to table
        int id = playlist.getId();
        dao.remove(id);
        playlistList = dao.getAll();
        assertEquals("remove did not work: ", numberOfPlaylists - 1, playlistList.size());
    }


    @After
    public void cleanup() throws Exception {
        playlistList = dao.getAll();

        for (entity.Playlist playlist : playlistList) {
            String thisName = playlist.getName();
            if (thisName.equalsIgnoreCase("Sinester")) {
                dao.remove(playlist.getId());
            } else if (thisName.equalsIgnoreCase("Bubbles")) {
                dao.remove(playlist.getId());
            }
        }
    }
}