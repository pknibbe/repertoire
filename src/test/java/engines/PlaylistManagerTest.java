package engines;

import entity.Association;
import persistence.AssociationDAO;
import entity.Playlist;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.PlaylistDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods of the UserManager class
 * Created by peter on 2/16/2017.
 */
public class PlaylistManagerTest {

    private final PlaylistManager playlistManager = new PlaylistManager();
    private final PlaylistDAO dao = new PlaylistDAO();
    private final AssociationDAO associationDAO = new AssociationDAO();
    private final Logger logger = Logger.getLogger(this.getClass());
    private List<Playlist> originalPlaylistList;
    private List<Association> originalAssociationList;
    private ArrayList<Integer> bogusList = new ArrayList<>();
    private Integer target;
    private Association association;

    /**
     * Creates some bogus playlist table entries to manipulate during testing
     * @throws Exception general exception
     */
    @Before
    public void setup() throws Exception {

        originalPlaylistList = dao.getAll();
        originalAssociationList = associationDAO.getAll();
        logger.info("In @Before, originalPlaylistList has " + originalPlaylistList.size() + "entries");
        bogusList.add(playlistManager.add("Boogie", 27));
        bogusList.add(playlistManager.add("Andrews Sisters", 27));
        bogusList.add(playlistManager.add("Woogie", 987));
        bogusList.add(playlistManager.add("Bugle Boy", 75));
        target=bogusList.get(bogusList.size() - 1);
    }

    @Test
    public void testAdd() throws Exception {
        logger.info("*** Start of testAdd ***");
        for (int index : bogusList) {
            logger.info("bogus list includes entry # " + index);
            association = associationDAO.get(index);
            int playListID = association.getRightTableKey();
            logger.info("Playlist name is " + dao.get(playListID).getName());
            if (dao.get(playListID).getName().equalsIgnoreCase("Boogie")) {
                assertEquals("User ID didn't match: ", 27, association.getLeftTableKey());
            }
        }
        assertEquals("setup did not add expected # : ", 4, bogusList.size() );
        logger.info("*** End of testAdd ***");
    }

    @Test
    public void TestGetID() throws Exception {
        logger.info("*** Start of TestGetID ***");

        association = associationDAO.get(target);
        assertEquals("Didn't get expected entry: ", (long) association.getRightTableKey(), (long) playlistManager.getID("Bugle Boy", 75));

        logger.info("*** End of TestGetID ***");
    }

    @Test
    public void TestGetIDs() throws Exception {
        logger.info("*** Start of TestGetIDs ***");
        Playlist playlist;

        ArrayList<Integer> listIDs = playlistManager.getIDs(987);
        assertEquals("Wrong number of matching playlists", 1, listIDs.size());
        for (int index : listIDs) {
            playlist = dao.get(index);
            assertEquals("Names don't match", "Woogie", playlist.getName());
        }
        logger.info("*** End of TestGetIDs ***");
    }

    @Test
    public void TestRename() throws Exception {
        logger.info("*** Start of TestRename ***");

        association = associationDAO.get(target);
        int valid_id = association.getRightTableKey();
        int index = playlistManager.rename(25, valid_id, "Honky Tonk");
        assertEquals("This should have returned zero: ", 0, index);
        index = playlistManager.rename(75, valid_id, "Honky Tonk");
        assertEquals("This should have matched non-zero : ", valid_id, index);

        index = playlistManager.getID("Honky Tonk", 75);
        assertEquals("This also should have matched non-zero : ", valid_id, index);

        logger.info("*** End of TestRename ***");
    }

    @After
    public void cleanup() throws Exception {

        for (int index : bogusList) { // loop over new associations
            association = associationDAO.get(index);
            dao.remove(association.getRightTableKey());
            associationDAO.remove(index);
        }
        List<Playlist> finalPlaylistList = dao.getAll();
        List<Association> finalAssociationList = associationDAO.getAll();

        assertEquals("Didn't return playlists table to original state: ", finalPlaylistList.size(), originalPlaylistList.size());
        assertEquals("Didn't return associations table to original state: ", finalAssociationList.size(), originalAssociationList.size());
    }
}
