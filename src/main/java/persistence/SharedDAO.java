package persistence;

import entity.Shared;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Database table accessor class
 * Created by peter on 2/13/2017.
 */
public class SharedDAO {
    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all Shared records
     *
     * @return All Shared records
     */
    public List<Shared> getAll() throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Shared> sharings = session.createCriteria(Shared.class).list();
        session.close();
        return sharings;
    }

    /** Get a single Shared for the given id
     *
     * @param id Shared's id
     * @return Shared
     */
    public Shared get(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Shared sharing = (Shared) session.get(Shared.class, id);
        session.close();
        return sharing;
    }

    /** save a new Playlist sharing record
     * @param shared The record to add to the database
     * @return id the id of the inserted record
     */
    public int add(Shared shared) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(shared);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Removes a Sharing record
     *
     * @param id ID of Sharing to be removed
     */
    public void remove(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Shared shared = (Shared) session.get(Shared.class, id);
        session.delete(shared);
        transaction.commit();
        session.close();
    }
}
