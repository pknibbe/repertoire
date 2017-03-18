package persistence;

import entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;



/**
 * Connect entity and database table
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
     * @param user the record to add to the users table
     * @return id the id of the inserted record
     */
    public int add(User user) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(user);
        transaction.commit();
        session.close();
        user.setUser_role_id(id);
        id = modify(user);
        return id;
    }

    /** modify a user record
     * @param updatedUser the version of the user with the new information
     * @return id the id of the updated record
     */
    public int modify(User updatedUser) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User sessionUser = (User) session.get(User.class, updatedUser.getId());
        sessionUser.setUser_name(updatedUser.getUser_name());
        sessionUser.setName(updatedUser.getName());
        sessionUser.setUser_pass(updatedUser.getUser_pass());
        sessionUser.setRole_name(updatedUser.getRole_name());
        sessionUser.setUser_role_id(updatedUser.getUser_role_id());
        User resultantUser = (User) session.merge(sessionUser);
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
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }
}
