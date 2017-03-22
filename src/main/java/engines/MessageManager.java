package engines;

import entity.Message;
import persistence.MessageDAO;

import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

/**
 * Manage access to messages
 * Created by peter on 3/22/2017.
 */
public class MessageManager {

    final private MessageDAO pDAO = new MessageDAO();
    //final private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Returns the system IDs of all Messages associated with the user
     * @param user_id The system ID of the user
     * @return The list of IDs of the associated Messages
     */
    public ArrayList<Integer> getIDs(int user_id) {
        List<Message> MessageList = pDAO.getAll();
        ArrayList<Integer> MessageIDs = new ArrayList<>();
        for (Message Message : MessageList) {
            if (user_id == Message.getReceiver()) {
                MessageIDs.add(Message.getId());
            }
        }
        return MessageIDs;
    }

}
