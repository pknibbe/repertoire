package engines;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
import entity.Shared;

public class SharedManagerTest {
    private ArrayList<Integer> testIds = new ArrayList<>();
    private int originalNumberOfShared;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setUp() throws Exception {
        originalNumberOfShared = SharedManager.getAll(4).size();
        testIds.add(SharedManager.share(89, 3));
        testIds.add(SharedManager.share(89, 4));
        testIds.add(SharedManager.share(90, 4));
        testIds.add(SharedManager.share(91, 5));
        for (Shared shared : SharedManager.getAll() ) {
            logger.info("Shared record " + shared.getId() + " shares playlist " + shared.getPlaylist_id() + " with " + shared.getShared_with());
        }
    }

    @After
    public void tearDown() throws Exception {
        logger.info(" " + testIds.size() + " shared entries to remove");
        for (Integer index : testIds) {
            logger.info("Removing entry " + index);
            SharedManager.remove(index);
        }
        assertEquals("Test changed the users table! ", originalNumberOfShared, SharedManager.getAll(4).size());
    }

    @Test
    public void share() throws Exception {
        assertEquals("Didn't add right number of entries: ", originalNumberOfShared + 2, SharedManager.getAll(4).size());
    }

    @Test
    public void isShared() throws Exception {
        assertFalse(SharedManager.isShared(88));
        assertTrue(SharedManager.isShared(89));
        assertTrue(SharedManager.isShared(90));
        assertTrue(SharedManager.isShared(91));
        assertFalse(SharedManager.isShared(92));
    }

    @Test
    public void getAll() throws Exception {
        assertEquals("Wrong number shared with 87",0, SharedManager.getAll(87).size());
    }

    @Test
    public void sharing() throws Exception {
        assertEquals(0, SharedManager.sharing(88).size());
        assertEquals(2, SharedManager.sharing(89).size());
        assertEquals(1, SharedManager.sharing(90).size());
        assertEquals(1, SharedManager.sharing(91).size());
    }

    @Test
    public void notSharing() throws Exception {
        int expected = UserManager.getUserIds().size();
        int actual = SharedManager.notSharing(88).size();
        assertEquals("Wrong number not sharing 88", expected, actual);
        actual = SharedManager.notSharing(89).size() + 2;
        assertEquals("Wrong number not sharing 89", expected, actual);
        actual = SharedManager.notSharing(90).size() + 1;
        assertEquals("Wrong number not sharing 90", expected, actual);
        actual = SharedManager.notSharing(91).size() + 1;
        assertEquals("Wrong number not sharing 91", expected, actual);
    }
}