package edu.matc.JLA.persistence;

import edu.matc.JLA.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.List;


public class UserDao extends GenericDao<User, Integer> {

    private final Logger log = Logger.getLogger(this.getClass());

    public UserDao() {
        super(User.class);
    }

    /** Return a list of all users
     *
     * @return All users
     */
    public List<User> getAllUsers() {
        log.debug("****** UserDao.getAllUsers()... entering...");
        List<User> users = new ArrayList<User>();
        Session session = getSession();
        users = session.createCriteria(User.class).list();
        session.close();
        log.debug("****** UserDao.getAllUsers()... leaving...");
        return users;
    }

}


