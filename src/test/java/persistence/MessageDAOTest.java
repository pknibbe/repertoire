package persistence;

import entity.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import entity.User;

import static org.junit.Assert.*;

public class MessageDAOTest {


    final private MessageDAO dao = new MessageDAO();
    final private UserDAO userDAO = new UserDAO();
    final private User user = userDAO.read(userDAO.getAdminId());
    private Message message;
    private int originalNumberOfMessages;

        @Before
        public void setup() throws Exception {
            originalNumberOfMessages = dao.getAll().size();
            message = new Message("Election", user, user, 0, "Can you hear me now?");
            message.setId(dao.create(message));
        }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        assertEquals("Added one, but found ", 1, dao.getAll().size() - originalNumberOfMessages);
        for (entity.Message message : dao.getAllToMe(user.getId())) {
            String thisName = message.getSubject();
            if (thisName.equalsIgnoreCase("Election")) found = true;
        }
        assertTrue("The expected subject was not found: ", found);
    }

    @Test
    public void testRead() throws Exception {
            assertEquals(message.getId(), dao.read(message.getId()).getId());
    }

    @Test
    public void testGetAllForRecipient() throws Exception {
        boolean found = false;

        for (entity.Message message : dao.getAllToMe(user.getId())) {
            String thisName = message.getSubject();
            if (thisName.equalsIgnoreCase("Election")) found = true;
        }
        assertTrue("The expected subject was not found: ", found);
    }

    @Test
    public void testCRUD() {
        dao.delete(message);
        message.setId(dao.create(message));
        message.setSubject("Nomination");
        dao.update(message);
        assertTrue(dao.read(message.getId()).getSubject().equalsIgnoreCase("Nomination"));
    }

    @Test
        public void testGet() throws Exception {
            assertEquals("Subjects don't match", "Election", dao.read(message.getId()).getSubject());
    }

    @After
    public void cleanup() throws Exception {
        dao.delete(message);
        assertEquals("Added and removed one, but found ", 0, dao.getAll().size() - originalNumberOfMessages);
    }
}