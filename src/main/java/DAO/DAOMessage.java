package DAO;

import Beans.Message;
import Utilities.SessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOMessage {

    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all messages
     *
     * @return All messages
     */
    public List<Message> getAllMessages() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return session.createCriteria(Message.class).list();
    }

    /** Get a single message for the given id
     *
     * @param id message's id
     * @return message
     */
    public Message getMessage(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (Message) session.get(Message.class, id);
    }

    /** save or update message
     * @param message
     * @return id of the inserted message
     */

    public void save(Message message) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info(session.save(message));
        transaction.commit();
    }


    /**
     * Removes a message
     *
     * @param message user to be removed
     */
    public void remove(Message message) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(message);
        transaction.commit();
    }

}
