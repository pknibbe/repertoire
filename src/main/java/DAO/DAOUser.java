package DAO;

import Beans.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Utilities.SessionFactoryProvider;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by peter on 2/1/2017.
 */
public class DAOUser {
    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all users
     *
     * @return All users
     */
    public List<User> getAllUsers() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return session.createCriteria(User.class).list();
    }

    /** Get a single user for the given id
     *
     * @param id user's id
     * @return User
     */
    public User getUser(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (User) session.get(User.class, id);
    }

    /** save or update user
     * @param user
     */
    public void save(User user) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info(session.save(user));
        transaction.commit();
    }

    /**
     * Removes a user
     *
     * @param user user to be removed
     */
    public void remove(User user) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
    }
}
