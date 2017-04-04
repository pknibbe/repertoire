package engines;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import entity.Role;
import entity.User;
import persistence.RoleDAO;
import persistence.UserDAO;

public class UserManager {

    final static private UserDAO userDAO = new UserDAO();
    final static private RoleDAO roleDAO = new RoleDAO();
    final static private LoggerManager loggerManager = new LoggerManager();
    final static private PropertyManager propertyManager = new PropertyManager();
    final static private int fieldLength = Integer.valueOf(propertyManager.getProperty("dbNameLength"));

    /**
     * Adds a new user and the role record to the users and user_roles tables
     *
     * @param userName The username part of the login credential
     * @param name     The name of the person
     * @param pw       The password part of the login credential
     * @param rolename The assigned role of the user
     * @return The unique user id for this user or zero if unable to complete
     */
    public static int addUserWithRole(String userName, String name, String pw, String rolename) {
        if (userName == null) return 0;
        if (name == null) return 0;
        if (pw == null) return 0;
        if (rolename == null) return 0;

        Logger logger = loggerManager.get();
        logger.debug("Creating a new user");
        User user = new entity.User(userName, name, pw, rolename);
        int added;
        if (checkUsername(userName)) { // userName is alright to use
            if (checkUsername(name)) { // name is alright to use
                if (checkRoleName(rolename)) {
                    if (checkPassword(pw)) {
                        Role role = new entity.Role(userName, rolename);
                        try {
                            added = roleDAO.add(role);
                            logger.debug(role.toString());
                            logger.debug(user.toString());
                            logger.debug("Added user ID = " + userDAO.add(user));
                            return added;
                        } catch (HibernateException hbe) {
                            logger.error("Unable to add user and role to database. Hibernate exception " + hbe);
                            return 0;
                        }
                    } else logger.warn("Can't add user with unacceptable password " + pw);
                } else logger.warn("Can't add user with unacceptable rolename " + rolename);
            } else logger.warn("Can't add user with unacceptable name " + name);
        } else logger.warn("Can't add user with unacceptable username " + userName);
        return 0;
    }

    /**
     * Returns all user records
     * @return The user records
     * @throws HibernateException Difficulty accessing the database
     */
    public static List<User> getAllUsers() throws HibernateException {
        return userDAO.getAll();
    }

    /**
     * Returns all role records
     * @return The role records
     * @throws HibernateException Difficulty accessing the database
     */
    static List<Role> getAllRoles() throws HibernateException {
        return roleDAO.getAll();
    }

    /**
     * Fetches the system IDs of the users
     *
     * @return The IDs of the other users
     */
    static ArrayList<Integer> getUserIds() {
        ArrayList<Integer> users = new ArrayList<>();

        Logger logger = loggerManager.get();
        try {
            for (User user : userDAO.getAll()) {
                Integer userID = user.getId();
                users.add(userID);
            }
        } catch (HibernateException hbe) {
            logger.error("Unable to get user Ids from database. Hibernate exception " + hbe);
            return null;
        }
        return users;
    }

    /**
     * Retrieves a specified user record
     *
     * @param identifier The unique identifier
     * @return The entire user record
     */
    public static User getUser(int identifier) {

        Logger logger = loggerManager.get();
        try {
            return userDAO.get(identifier);
        } catch (HibernateException hbe) {
            logger.error("Unable to get user record from database. Hibernate exception " + hbe);
            return null;
        }
    }

    /**
     * Fetches the Names of the users other than the current user
     *
     * @param identifier The system ID of the current user
     * @return The Names of the other users
     */
    public static ArrayList<String> getOtherUserNames(int identifier) {
        ArrayList<String> otherUsers = new ArrayList<>();

        Logger logger = loggerManager.get();
        try {
            for (User user : userDAO.getAll()) {
                Integer userID = user.getId();
                if (identifier != userID) {
                    otherUsers.add(user.getName());
                }
            }
        } catch (HibernateException hbe) {
            logger.error("Unable to get user Ids from database. Hibernate exception " + hbe);
            return null;
        }
        return otherUsers;
    }

    /**
     * Retrieves the system ID of a user with a given name
     *
     * @param name The name of the user
     * @return The system ID of a user with that name or zero if it fails
     */
    public static int getIdByName(String name) {
        Logger logger = loggerManager.get();
        try {
            List<User> users = userDAO.getAll();
            for (User user : users) {
                Integer userID = user.getId();
                if (name.equalsIgnoreCase(user.getName())) {
                    return userID;
                }
            }
        } catch (HibernateException hbe) {
            logger.error("Unable to retrieve user ID from database. Hibernate exception " + hbe);
        }
        return 0;
    }

    /**
     * Retrieves the ID of the administrator user
     *
     * @return The system ID of the administrator user
     */
    public static int getAdminId() {
        String role = "administrator";
        Logger logger = loggerManager.get();
        try {
            List<User> users = userDAO.getAll();
            for (User user : users) {
                if (role.equalsIgnoreCase(user.getRole_name())) return user.getId();
            }
        } catch (HibernateException hbe) {
            logger.error("Unable to retrieve admin user ID from database. Hibernate exception " + hbe);
        }

        return 0;
    }

    /**
     * Retrieves a user's personal name
     *
     * @param userId The unique identifier
     * @return The personal name
     */
    public static String getName(int userId) {
        Logger logger = loggerManager.get();
        try {
            User user = userDAO.get(userId);
            if (user == null) {
                return null;
            }
            return user.getName();
        } catch (HibernateException hbe) {
            logger.error("Unable to retrieve user name from database. Hibernate exception " + hbe);
            return null;
        }
    }

    /**
     * Retrieves a user's assigned role
     *
     * @param userId The unique identifier
     * @return The assigned role value
     */
    public static String determineRole(int userId) {
        Logger logger = loggerManager.get();
        try {
            User user = userDAO.get(userId);
            return user.getRole_name();
        } catch (HibernateException hbe) {
            logger.error("Unable to retrieve user role from database. Hibernate exception " + hbe);
            return null;
        }
    }

    /**
     * Updates a user's information in both the users and user_roles tables
     *
     * @param identifier The unique identifier
     * @param userName   The username part of the login credential
     * @param name       The name of the person
     * @param password   The password part of the login credential
     * @param rolename   The assigned role of the user
     * @return The unique identifier of the updated user record
     */
    public static int updateUserWithRole(int identifier, String userName, String name, String password, String rolename) {
        Logger logger = loggerManager.get();
        try {
            User user = userDAO.get(identifier);

            if (user == null) {
                logger.error("In updateUserWithRole and user is null");
                return 0;
            }
            Role role = roleDAO.get(user.getUser_role_id());
            if ((checkUsername(userName)) &&(checkUsername(name))) { // userName is alright to use
                user.setUser_name(userName);
                user.setName(name);
                if (checkPassword(password)) {
                    user.setUser_pass(password);
                    if (checkRoleName(rolename)) {
                        user.setRole_name(rolename);
                        logger.debug(user.toString());
                        role.setRole_name(rolename);
                        role.setUser_name(userName);
                        userDAO.modify(user);
                        return roleDAO.modify(role);
                    } else logger.warn("Can't update user because Role name is not acceptable " + rolename);
                } else logger.warn("Can't update user because password is not acceptable " + password);
            } else logger.warn("Can't update user because username or name is not acceptable " + userName + " " + name);
        } catch (HibernateException hbe) {
            logger.error("Unable to update user record in database. Hibernate exception " + hbe);
        }
        return 0;
    }

    /**
     * Determines whether a set of credentials is valid
     *
     * @param user_name The first part of the credential set
     * @param user_pass The other part of the credential set
     * @return The unique identifier or, if the credentials are not valid, 0
     */
    public static int verifyCredentials(String user_name, String user_pass) {
        Logger logger = loggerManager.get();
        int user_id = 0;
        try {
            List<User> users = userDAO.getAll();
            for (User user : users) {
                if (user_name.equalsIgnoreCase(user.getUser_name()) &&
                        (user_pass.equalsIgnoreCase(user.getUser_pass()))) {
                    user_id = user.getId();
                }
            }
        } catch (HibernateException hbe) {
            logger.error("Unable to verify user record in database. Hibernate exception " + hbe);
        }

        return user_id;
    }

    /**
     * Removes a user from the system by removing entries in both the users and user_roles tables
     *
     * @param identifier The unique identifier
     */
    public static int removeUserWithRole(int identifier) {
        if (identifier < 1) return 0; // can't remove a non-existent entry
        Logger logger = loggerManager.get();
        try {
            User user = userDAO.get(identifier);

            logger.debug("About to remove user " + identifier);
            roleDAO.remove(user.getUser_role_id());
            userDAO.remove(identifier);
            return identifier;
        } catch (HibernateException hbe) {
            logger.error("Unable to remove user record from database. Hibernate exception " + hbe);
        }
        return 0;
    }

    /**
     * Performs simple checks for an acceptable username
     *
     * @param username the proposed username
     * @return whether the username is acceptable
     */
    private static boolean checkUsername(String username) {
        return ((2 < username.length() && (fieldLength >= username.length()))); // Require at least 3 characters in username
    }

    /**
     * Performs simple checks for an acceptable password
     *
     * @param password the proposed password
     * @return whether the password is acceptable
     */
    private static boolean checkPassword(String password) {
        return ((7 < password.length()) && (fieldLength >= password.length())); // Require at least 8 characters in password
    }

    /**
     * Determines whether a role name is valid
     *
     * @param rolename The role name to be tested
     * @return Whether or not the role name is valid
     */
    private static boolean checkRoleName(String rolename) {
        ArrayList<String> definedRoles = new ArrayList<>();
        definedRoles.add("administrator");
        definedRoles.add("registered-user");

        return (definedRoles.contains(rolename));
    }

    /**
     * Determines whether a session has an authenticated user
     *
     * @param user_id The id to be authenticated
     * @return whether the user could be authenticated
     */
    public static boolean authenticated(Integer user_id) {
        boolean aok = false;
        if (user_id != null) {
            if (user_id > 0) aok = true;
        }
        return aok;
    }
}