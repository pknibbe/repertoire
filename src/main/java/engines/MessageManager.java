package engines;

import entity.Message;
import persistence.MessageDAO;

import java.util.ArrayList;
import java.util.List;


/**
 * Manage access to messages
 * Created by peter on 3/22/2017.
 */
public class MessageManager {
    final static private MessageDAO dao = new MessageDAO();

    /**
     * Adds a new message to the database
     * @param message The message
     */
    public static void add(Message message) {
        dao.add(message);
    }

    /**
     * Returns the specified message record from the database
     * @param message_id The system ID of the message
     * @return The Message
     */
    public static Message get(int message_id) {
        return dao.get(message_id);
    }

    /**
     * Returns the system IDs of all Messages associated with the user
     * @param user_id The system ID of the user
     * @return The list of IDs of the associated Messages
     */
    public static ArrayList<Integer> getIDs(int user_id) {
        List<Message> MessageList = dao.getAll();
        ArrayList<Integer> MessageIDs = new ArrayList<>();
        for (Message Message : MessageList) {
            if (user_id == Message.getReceiver()) {
                MessageIDs.add(Message.getId());
            }
        }
        return MessageIDs;
    }

}
