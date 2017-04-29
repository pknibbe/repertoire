package persistence;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO  extends GenericDAO<Message, Integer> {

    private final Logger logger = Logger.getLogger(this.getClass());
    private Session session;
    private Transaction transaction;

    public MessageDAO() {super(Message.class); }

    /** Return a list of all messages
     *
     * @return All messages
     */
    public List<Message> getAll() throws HibernateException {
        Session session = getSession();
        List<Message> messages = session.createCriteria(Message.class).list();
        session.close();
        return messages;
    }

    /**
     * Returns the Messages associated with the user
     * @param recipient_id The system ID of the recipient
     * @return The list of the associated Messages
     */
    public List<Message> getAll(int recipient_id) {
        Session session = getSession();
        Query query = session.createQuery("FROM Message M WHERE M.receiver.id = :recipient_id");
        query.setParameter("recipient_id", recipient_id);
        List<Message> messages = query.list();
        session.close();
        return messages;
    }

}
