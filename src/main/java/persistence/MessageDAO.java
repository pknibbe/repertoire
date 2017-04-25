package persistence;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    private final Logger logger = Logger.getLogger(this.getClass());
    private Session session;
    private Transaction transaction;

    /** Return a list of all messages
     *
     * @return All messages
     */
    public List<Message> getAll() throws HibernateException {
        session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Message> messages = session.createCriteria(Message.class).list();
        session.close();
        return messages;
    }

    /** Get a single message for the given id
     *
     * @param id message's id
     * @return message
     */
    public Message get(int id) throws HibernateException {
        session = SessionFactoryProvider.getSessionFactory().openSession();
        Message message = (Message) session.get(Message.class, id);
        session.close();
        return message;
    }

    /**
     * Returns the system IDs of all Messages associated with the user
     * @param user_id The system ID of the user
     * @return The list of IDs of the associated Messages
     */
    public ArrayList<Integer> getIDs(int user_id) {
        List<Message> MessageList = getAll();
        ArrayList<Integer> MessageIDs = new ArrayList<>();
        for (Message Message : MessageList) {
            if (user_id == Message.getReceiver().getId()) {
                MessageIDs.add(Message.getId());
            }
        }
        return MessageIDs;
    }
    /** save a new message
     * @param message The message to be stored
     * @return id the id of the inserted record
     */
    public int add(Message message) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.debug("Saving message " + message.getSubject());
        logger.debug(message.toString());
        int id = (Integer) session.save(message);
        transaction.commit();
        session.close();
        return id;
    }

    /** modify a message record
     * @param updatedMessage the version of the message with the new information
     * @return id the id of the updated record
     */
    public int modify(Message updatedMessage) throws HibernateException {
        session = SessionFactoryProvider.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.update(updatedMessage);
        logger.debug("Updated message " + updatedMessage.toString());
        transaction.commit();
        session.close();
        return updatedMessage.getId();
    }

    /**
     * Removes a message
     *
     * @param id ID of message to be removed
     */
    public void remove(int id) throws HibernateException {
        logger.debug("In dao.remove with id = " + id);
        session = SessionFactoryProvider.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Message message = (Message) session.get(Message.class, id);
        logger.debug("In dao. remove with message " + message.toString());
        session.delete(message);
        transaction.commit();
        session.close();
    }
}
