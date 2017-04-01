package persistence;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.Message;
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
        logger.debug("Updating message " + updatedMessage.getSubject());
        logger.debug(updatedMessage.toString());
        Message sessionMessage = (Message) session.get(Message.class, updatedMessage.getId());
        sessionMessage.setSubject(updatedMessage.getSubject());
        sessionMessage.setSender(updatedMessage.getSender());
        sessionMessage.setReceiver(updatedMessage.getReceiver());
        sessionMessage.setReadFlag(updatedMessage.getReadFlag());
        sessionMessage.setContent(updatedMessage.getContent());
        logger.debug("Updating message " + sessionMessage.getSubject());
        logger.debug(sessionMessage.toString());
        Message resultantMessage = (Message) session.merge(sessionMessage);
        logger.debug("Updated message " + resultantMessage.toString());
        transaction.commit();
        session.close();
        return resultantMessage.getId();
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
