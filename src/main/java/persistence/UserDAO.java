package persistence;

import entity.PropertyManager;
import entity.User;
//import org.apache.log4j.Logger;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the persistent user table contents
 * @author P Knibbe
 */
public class UserDAO extends GenericDAO<User, Integer> {
    //private final Logger logger = Logger.getLogger(this.getClass());
    final static private PropertyManager propertyManager = new PropertyManager();
    final static private int fieldLength = Integer.valueOf(propertyManager.getProperty("dbNameLength"));

    public UserDAO() {
        super(User.class);
    }


    /** Return a list of all users
     *
     * @return All users
     */
    public List<User> getAll() throws HibernateException {
        Session session = getSession();
        List<User> users = session.createCriteria(User.class).list();
        session.close();
        return users;
    }

    /**
     * Determines whether a session has an authenticated user
     *
     * @param user_id The id to be authenticated
     * @return whether the user could be authenticated
     */
    public boolean authenticated(Integer user_id) {
        boolean aok = false;
        if (user_id != null) {
            if (user_id > 0) aok = true;
        }
        return aok;
    }

    /**
     * Determines whether a set of credentials is valid
     *
     * @param user_id The user id first part of the credential set
     * @param user_name The first part of the credential set
     * @param user_pass The other part of the credential set
     * @return The unique identifier or, if the credentials are not valid, 0
     */
    public boolean verifyCredentials(int user_id, String user_name, String user_pass) throws HibernateException {
        boolean verified = true;
        User user = read(user_id);
        if (user == null) {
            verified = false;
        } else if (!user.getUser_name().equalsIgnoreCase(user_name)) {
            verified = false;
        } else if (!user.getUser_pass().equalsIgnoreCase(user_pass)) {
            verified = false;
        }
        return verified;
    }

    /**
     * Fetches the Names of the users other than the current user
     *
     * @param identifier The system ID of the current user
     * @return The Names of the other users
     */
    public List<String> getOtherUserNames(int identifier) {
        Session session = getSession();
        Query query = session.createQuery("SELECT U.name FROM User U WHERE U.id <> :identifier");
        query.setParameter("identifier", identifier);
        session.close();
        return query.list();
    }

    /**
     * Retrieves the system ID of a user with a given name
     *
     * @param name The name of the user
     * @return The system ID of a user with that name or zero if it fails
     */
    public int getIdByName(String name) throws HibernateException {
        Session session = getSession();
        Query query = session.createQuery("SELECT U.id FROM User U WHERE U.name = :name");
        query.setParameter("name", name);
        int id = 0;
        if (query.list() != null) {
            List<Integer> ids = query.list();
            id = ids.get(0);
        }
        session.close();
        return id;
    }

    /**
     * Retrieves the system ID of a user with a given username
     *
     * @param name The name of the user
     * @return The system ID of a user with that name or zero if it fails
     */
    public int getIdByUsername(String userName) throws HibernateException {
        Session session = getSession();
        Query query = session.createQuery("SELECT U.id FROM User U WHERE U.user_name = :userName");
        query.setParameter("userName", userName);
        int id = 0;
        if (query.list() != null) {
            List<Integer> ids = query.list();
            id = ids.get(0);
        }
        session.close();
        return id;
    }

    /**
     * Retrieves the ID of the administrator user
     *
     * @return The system ID of the administrator user
     */
    public  int getAdminId() throws HibernateException {
        String role = "administrator";

            List<User> users = getAll();
            for (User user : users) {
                if (role.equalsIgnoreCase(user.getRole_name())) return user.getId();
            }
        return 0;
    }

    /**
     * Performs simple checks for an acceptable username
     *
     * @param username the proposed username
     * @return whether the username is acceptable
     */
    static boolean checkUsername(String username) {
        return ((2 < username.length() && (fieldLength >= username.length()))); // Require at least 3 characters in username
    }

    /**
     * Performs simple checks for an acceptable password
     *
     * @param password the proposed password
     * @return whether the password is acceptable
     */
    static boolean checkPassword(String password) {
        return ((7 < password.length()) && (fieldLength >= password.length())); // Require at least 8 characters in password
    }

    /**
     * Determines whether a role name is valid
     *
     * @param rolename The role name to be tested
     * @return Whether or not the role name is valid
     */
    static boolean checkRoleName(String rolename) {
        ArrayList<String> definedRoles = new ArrayList<>();
        definedRoles.add("administrator");
        definedRoles.add("registered-user");
        definedRoles.add("guest");

        return (definedRoles.contains(rolename));
    }
}
