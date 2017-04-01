package persistence;

import entity.Message;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageDAOTest {


    private MessageDAO dao;
    private entity.Message message;
    private int numberOfMessages;
    private  List<Message> messageList;
    private final Logger logger = Logger.getLogger(this.getClass());

        @Before
        public void setup() throws Exception {
            dao = new MessageDAO();
            messageList = dao.getAll();
            logger.debug("In @Before, messageList has " + messageList.size() + "entries");
            justAdd(); // make sure table is not empty for purpose of test
            messageList = dao.getAll();
            logger.debug("After justAdd, messageList has " + messageList.size() + "entries");
            numberOfMessages = messageList.size();
        }

        @Test
        public void testGetAll() throws Exception {
            boolean found = false;

            for (entity.Message message : messageList) {
                String thisName = message.getSubject();
                if (thisName.equalsIgnoreCase("Election")) found = true;
            }
            assertTrue("The expected subject was not found: ", found);
        }

        @Test
        public void testGet() throws Exception {
            message = messageList.get(messageList.size() - 1); // retrieve most recent addition to table
            int id = message.getId(); // get the id of the most recent addition
            message = dao.get(id); // get the message by id
            assertEquals("Subjects don't match", "Election", message.getSubject());
        }

        @Test
        public void testAdd() throws Exception {
            justAdd();
            messageList = dao.getAll();
            logger.debug("message list has " + messageList.size() + " entries");
            assertEquals("Add did not work: ", numberOfMessages + 1, messageList.size());
        }

        @Test
        public void testModifyMessageName() throws Exception {
            message = messageList.get(messageList.size() - 1); // retrieve most recent addition to table
            int id = message.getId();
            message.setSubject("Johanna");
            logger.debug(message.toString());
            logger.debug("Updated message ID = " + dao.modify(message));
            //dao.modify(message);
            message = dao.get(id);
            assertEquals("Message label not modified", "Johanna", message.getSubject());
            messageList = dao.getAll();
            assertEquals("Modify added an entry!", numberOfMessages, messageList.size());
        }

        private void justAdd() {
            message = new entity.Message("Election", 1, 12, 1, "How about that?");
            logger.debug("New message is " + message.toString());
                dao.add(message);
        }

        @Test
        public void testRemove() throws Exception {
            message = messageList.get(messageList.size() - 1); // retrieve most recent addition to table
            int id = message.getId();
            dao.remove(id);
            messageList = dao.getAll();
            assertEquals("remove did not work: ", numberOfMessages - 1, messageList.size());
        }


        @After
        public void cleanup() throws Exception {
            messageList = dao.getAll();

            for (entity.Message message : messageList) {
                String thisName = message.getSubject();
                if (thisName.equalsIgnoreCase("Election")) {
                    dao.remove(message.getId());
                } else if (thisName.equalsIgnoreCase("Johanna")) {
                    dao.remove(message.getId());
                }
            }
        }
    }