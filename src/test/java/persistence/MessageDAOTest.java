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
    final private User user = userDAO.getAll().get(0);
    private int newMessageID;
    private int numberOfMessages;
    private int originalNumberOfMessages;

        @Before
        public void setup() throws Exception {
            originalNumberOfMessages = dao.getAll().size();
            Message message = new Message("Election", user, user, 0, "Can you hear me now?");
            newMessageID = dao.add(message);
            numberOfMessages = dao.getAll().size();
            assertEquals("Added one, but found ", 1, numberOfMessages - originalNumberOfMessages);
        }

        @Test
        public void testGetAll() throws Exception {
            boolean found = false;

            for (entity.Message message : dao.getAll()) {
                String thisName = message.getSubject();
                if (thisName.equalsIgnoreCase("Election")) found = true;
            }
            assertTrue("The expected subject was not found: ", found);
        }

        @Test
        public void testGet() throws Exception {

            assertEquals("Subjects don't match", "Election", dao.get(newMessageID).getSubject());
        }

        @After
        public void cleanup() throws Exception {
            dao.remove(newMessageID);
            numberOfMessages = dao.getAll().size();
            assertEquals("Added and removed one, but found ", 0, numberOfMessages - originalNumberOfMessages);

        }
    }