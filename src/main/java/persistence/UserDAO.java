package persistence;

import entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;



/**
 * Created by peter on 2/1/2017.
 */
public class UserDAO {
    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all users
     *
     * @return All users
     */
    public List<User> getAll() throws HibernateException {
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
    public User get(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }

    /** save a new user
     * @param user
     * @return id the id of the inserted record
     */
    public int add(User user) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info("Saving user " + user.getUsername());
        logger.info(user.toString());
        int id = (Integer) session.save(user);
        transaction.commit();
        session.close();
        return id;
    }

    /** modify a user record
     * @param updatedUser the version of the user with the new information
     * @return id the id of the updated record
     */
    public int modify(User updatedUser) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info("Updating user " + updatedUser.getUsername());
        logger.info(updatedUser.toString());
        User sessionUser = (User) session.get(User.class, updatedUser.getId());
        sessionUser.setUsername(updatedUser.getUsername());
        sessionUser.setName(updatedUser.getName());
        logger.info("Updating user " + sessionUser.getUsername());
        logger.info(sessionUser.toString());
        User resultantUser = (User) session.merge(sessionUser);
        logger.info("Updated user " + resultantUser.toString());
        transaction.commit();
        session.close();
        return resultantUser.getId();
    }

    /**
     * Removes a user
     *
     * @param id ID of user to be removed
     */
    public void remove(int id) throws HibernateException {
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
