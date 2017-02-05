package persistence;

import entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
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
    public List<User> getAll() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<User> users = session.createCriteria(User.class).list();
        session.close();
        return users;
    }

    /** Get a single user for the given id
     *
     * @param id user's id
     * @return User
     */
    public User get(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }

    /** save a new user
     * @param user
     * @return id the id of the inserted record
     */
    public int add(User user) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(user);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Removes a user
     *
     * @param id ID of user to be removed
     */
    public void remove(int id) {
        logger.info("In dao.remove with id = " + id);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        logger.info("In dao. remove with user " + user.toString());
        session.delete(user);
        transaction.commit();
        session.close();
    }
}
