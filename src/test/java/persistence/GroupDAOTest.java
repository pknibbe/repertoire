package persistence;

import entity.Group;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Database accessor test
 * Created by peter on 2/13/2017.
 */
public class GroupDAOTest {

    final private GroupDAO dao = new GroupDAO();
    final private UserDAO userDAO = new UserDAO();
    private int originalNumberOfGroups;
    private Group group;

    @Before
    public void setup() throws Exception {
        originalNumberOfGroups = dao.getAll().size();
        group = new Group("Munsters");
        group.setId(dao.create(group));
    }

    @Test
    public void testCreate() throws Exception {
        assertEquals("Added one, but found ", 1, dao.getAll().size() - originalNumberOfGroups);
    }

    @Test
    public void testGetGroupByName() throws Exception {
        assertEquals(group.getId(), dao.getGroupByName("Munsters").getId());
    }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (Group herd : dao.getAll()) {
            String thisName = herd.getName();
            if (thisName.equalsIgnoreCase("Munsters")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testRead() throws Exception {
        assertEquals("Names don't match", "Munsters", dao.read(group.getId()).getName());
    }

    @Test
    public void testUpdate() throws Exception {
        group.setName("Bubbles");
        dao.update(group);
        assertEquals("Playlistname not modified", "Bubbles", dao.read(group.getId()).getName());
    }

    @Test
    public void testIsUsed() {
        assertTrue(dao.isUsed(userDAO.read(userDAO.getAdminId()).getGroup().getId()));
        assertFalse(dao.isUsed(group.getId()));
    }

    @After
    public void cleanup() throws Exception {
        assertTrue(dao.remove(group.getId(), userDAO.getAdminId()));
        assertEquals("Added and removed one, but found ", 0, dao.getAll().size() - originalNumberOfGroups);
        group.setId(dao.create(group));
        dao.delete(group);
        assertEquals("Added and removed one, but found ", 0, dao.getAll().size() - originalNumberOfGroups);
    }
}