package engines;

//import org.apache.log4j.Logger;

import entity.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.MessageDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods of the MessageManager class
 * Created by peter on 3/22/2017.
 */
public class MessageManagerTest {

    private final MessageManager manager = new MessageManager();
    private final MessageDAO dao = new MessageDAO();
//    private final Logger logger = Logger.getLogger(this.getClass());
    private List<Message> originalMessageList;
    private ArrayList<Integer> bogusList = new ArrayList<>();
    private Message message;

    /**
     * Creates some bogus playlist table entries to manipulate during testing
     * @throws Exception general exception
     */
    @Before
    public void setup() throws Exception {

        originalMessageList = dao.getAll();
        message = new Message("Badgers", 0, 75, 0, "Boy, they are amazing");
        bogusList.add(dao.add(message));

        message = new Message("Badgers", 0, 75, 0, "They won!!!");
        bogusList.add(dao.add(message));

        message = new Message("Badgers", 0, 75, 0, "Again!");
        bogusList.add(dao.add(message));

        message = new Message("Badgers", 0, 76, 0, "Boy, they are amazing");
        bogusList.add(dao.add(message));
    }

    @Test
    public void TestGetIDs() throws Exception {
        //    public ArrayList<Integer> getIDs(int user_id) {


        ArrayList<Integer> listIDs = manager.getIDs(75);
        assertEquals("Wrong number of matching messages", 3, listIDs.size());
        for (int index : listIDs) {
            message = dao.get(index);
            assertEquals("Subjects don't match", "Badgers", message.getSubject());
        }
    }

    @After
    public void cleanup() throws Exception {

        for (int index : bogusList) { // loop over new messages
            dao.remove(index);
        }
        List<Message> finalMessagelist = dao.getAll();

        assertEquals("Didn't return messages table to original state: ", finalMessagelist.size(), originalMessageList.size());
    }
}
