package DAO;

import org.junit.Before;
import org.junit.Test;
import Beans.Message;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOMessageTest {
    DAOMessage dao;
    List<Message> messageList;
    int numberOfMessages = 0;

    @Before
    public void setUp() throws Exception {
        dao = new DAOMessage();
        testSave(); // create an entry so getters can get it
        messageList = dao.getAllMessages();
        numberOfMessages = messageList.size();
    }

    @Test
    public void testGetAllMessages() throws Exception {
        messageList = dao.getAllMessages();
        assertEquals("Number of Messages is incorrect.  ",numberOfMessages, messageList.size());
    }

    @Test
    public void testGetMessage() throws Exception {
        Message message = messageList.get(0);
        assertEquals("Message content is incorrect.  ", message.getContent(), "Hello");
    }

    @Test
    public void testSave() throws Exception {
        Message message = new Message(1, 2, 0, "Hello");
        dao.save(message);
        messageList = dao.getAllMessages();
        assertEquals("Number of users after save is incorrect", numberOfMessages + 1, messageList.size());
    }

    @Test
    public void testRemove() throws Exception {
        Message message = messageList.get(0);
        dao.remove(message);
        messageList = dao.getAllMessages();
        assertEquals("Number of users after remove is incorrect", numberOfMessages - 1, messageList.size());
    }

}