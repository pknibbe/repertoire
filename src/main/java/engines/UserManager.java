package engines;

import java.util.ArrayList;
import java.util.List;
import entity.Role;
import entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import persistence.RoleDAO;
import persistence.UserDAO;

public class UserManager {

    final private UserDAO userDAO = new UserDAO();
    final private RoleDAO roleDAO = new RoleDAO();
    final private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Adds a new user and the role record to the users and user_roles tables
     * @param userName The username part of the login credential
     * @param name The name of the person
     * @param pw The password part of the login credential
     * @param rolename The assigned role of the user
     * @return The unique user id for this user or zero if unable to complete
     */
    public int addUserWithRole(String userName, String name, String pw, String rolename) {
        if (userName == null) return 0;
        if (name == null) return 0;
        if (pw == null) return 0;
        if (rolename == null) return 0;

        logger.info("Creating a new user");
        User user = new entity.User(userName, name, pw, rolename);
        int added;
        if (checkUsername(userName)) { // userName is alright to use
            if (checkRoleName(rolename)) {
                if (checkPassword(pw)) {
                    Role role = new entity.Role(userName, rolename);
                    try {
                        added = roleDAO.add(role);
                        logger.info(role.toString());
                        logger.info(user.toString());
                        logger.info("Added user ID = " + userDAO.add(user));
                        return added;
                    }
                    catch (HibernateException hbe) {
                        logger.error("Unable to add user and role to database. Hibernate exception " + hbe);
                        return 0;
                    }
                } else logger.warn("Can't add user with unacceptable password " + pw);
            } else logger.warn("Can't add user with unacceptable rolename " + rolename);
        } else logger.warn("Can't add user with unacceptable username " + userName);
        return 0;
    }

    /**
     * Fetches the Names of the users other than the current user
     * @param identifier The system ID of the current user
     * @return The Names of the other users
     */
    public ArrayList<String> getOtherUserNames(int identifier) {
        ArrayList<String> otherUsers = new ArrayList<>();

        try {
            for (User user : userDAO.getAll()) {
                Integer userID = user.getId();
                if (identifier != userID) {
                    otherUsers.add(user.getName());
                }
            }                    }
        catch (HibernateException hbe) {
            logger.error("Unable to get user Ids from database. Hibernate exception " + hbe);
            return null;
        }
        return otherUsers;
    }

    /**
     * Retrieves a specified user record
     * @param identifier The unique identifier
     * @return The entire user record
     */
    public User getUser(int identifier) {

        try {
            return userDAO.get(identifier);
        }
        catch (HibernateException hbe) {
            logger.error("Unable to get user record from database. Hibernate exception " + hbe);
            return null;
        }
    }

    /**
     * Removes a user from the system by removing entries in both the users and user_roles tables
     * @param identifier The unique identifier
     */
    public int removeUserWithRole(int identifier) {
        if (identifier < 1) return 0; // can't remove a non-existent entry
        try {
            User user = userDAO.get(identifier);

            logger.info("About to remove user " + identifier);
            roleDAO.remove(user.getUser_role_id());
            userDAO.remove(identifier);
            return identifier;
        }
        catch (HibernateException hbe) {
            logger.error("Unable to remove user record from database. Hibernate exception " + hbe);
        }
        return 0;
    }

    /**
     * Updates a user's information in both the users and user_roles tables
     * @param identifier The unique identifier
     * @param userName The username part of the login credential
     * @param name The name of the person
     * @param password The password part of the login credential
     * @param rolename The assigned role of the user
     * @return The unique identifier of the updated user record
     */
    public int updateUserWithRole(int identifier, String userName, String name, String password, String rolename) {
        try {
            User user = userDAO.get(identifier);

            if (user == null) {
                logger.error("In updateUserWithRole and user is null");
                return 0;
            }
            Role role = roleDAO.get(user.getUser_role_id());
            if (checkUsername(userName)) { // userName is alright to use
                user.setUser_name(userName);
                user.setName(name);
                if (checkPassword(password)) {
                    user.setUser_pass(password);
                    if (checkRoleName(rolename)) {
                        user.setRole_name(rolename);
                        logger.info(user.toString());
                        role.setRole_name(rolename);
                        role.setUser_name(userName);
                        userDAO.modify(user);
                        return roleDAO.modify(role);
                    } else logger.warn("Can't update user because Role name is not acceptable " + rolename);
                } else logger.warn("Can't update user because password is not acceptable " + password);
            } else logger.warn("Can't update user because username is not acceptable " + userName);
        }
        catch (HibernateException hbe) {
            logger.error("Unable to update user record in database. Hibernate exception " + hbe);
        }
        return 0;
    }

    /**
     * Determines whether a set of credentials is valid
     * @param user_name The first part of the credential set
     * @param user_pass The other part of the credential set
     * @return The unique identifier or, if the credentials are not valid, 0
     */
    public int VerifyCredentials(String user_name, String user_pass) {
        int user_id = 0;
        try {
        List<User> users = userDAO.getAll();
        for (User user : users) {
            if (user_name.equalsIgnoreCase(user.getUser_name()) &&
                    (user_pass.equalsIgnoreCase(user.getUser_pass()))) {
                user_id = user.getId();
            }
        }
        }
        catch (HibernateException hbe) {
            logger.error("Unable to verify user record in database. Hibernate exception " + hbe);
        }

        return user_id;
    }

    /**
     * Retrieves a user's assigned role
     * @param userId The unique identifier
     * @return The assigned role value
     */
    public String DetermineRole(int userId) {
        try {
            User user = userDAO.get(userId);
            return user.getRole_name();
        }
        catch (HibernateException hbe) {
            logger.error("Unable to retrieve user role from database. Hibernate exception " + hbe);
            return null;
        }
    }

    /**
     * Retrieves a user's personal name
     * @param userId The unique identifier
     * @return The personal name
     */
    public String getName(int userId) {
        try {
            User user = userDAO.get(userId);
            return user.getName();
        }
        catch (HibernateException hbe) {
            logger.error("Unable to retrieve user name from database. Hibernate exception " + hbe);
            return null;
        }
    }

    /**
     * Performs simple checks for an acceptable username
     * @param username the proposed username
     * @return whether the username is acceptable
     */
    private boolean checkUsername(String username) {
        return (2 < username.length()); // Require at least 3 characters in username
    }

    /**
     * Performs simple checks for an acceptable password
     * @param password the proposed password
     * @return whether the password is acceptable
     */
    private boolean checkPassword(String password) {
        return (7 < password.length()); // Require at least 8 characters in password
    }

    /**
     * Determines whether a role name is valid
     * @param rolename The role name to be tested
     * @return Whether or not the role name is valid
     */
    private boolean checkRoleName(String rolename) {
        ArrayList<String> definedRoles = new ArrayList<>();
        definedRoles.add("administrator");
        definedRoles.add("registered-user");

        return (definedRoles.contains(rolename));
    }

    /**
     * Retrieves the system ID of a user with a given name
     * @param name The name of the user
     * @return The system ID of a user with that name or zero if it fails
     */
    public int getIdByName(String name) {
        try {
            List<User> users = userDAO.getAll();
            for (User user : users) {
                Integer userID = user.getId();
                if (name.equalsIgnoreCase(user.getName())) {
                    return userID;
                }
            }
        }
        catch (HibernateException hbe) {
            logger.error("Unable to retrieve user ID from database. Hibernate exception " + hbe);
        }
        return 0;
    }

    /**
     * Determines whether a session has an authenticated user
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
     * Retrieves the ID of the administrator user
     * @return The system ID of the administrator user
     */
    public int getAdminId() {
        String role = "administrator";
        try {
            List<User> users = userDAO.getAll();
            for (User user : users) {
                if (role.equalsIgnoreCase(user.getRole_name())) return user.getId();
            }
        }
        catch (HibernateException hbe) {
            logger.error("Unable to retrieve admin user ID from database. Hibernate exception " + hbe);
        }

        return 0;
    }
}