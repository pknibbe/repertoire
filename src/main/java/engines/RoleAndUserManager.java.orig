package engines;

import java.util.ArrayList;
import java.util.List;
import entity.Role;
import entity.User;
import org.apache.log4j.Logger;
import persistence.RoleDAO;
import persistence.UserDAO;
/**
 * Manage users and roles
 * Created by peter on 2/8/2017.
 */
public class RoleAndUserManager {

    final private UserDAO userDAO = new UserDAO();
    final private RoleDAO roleDAO = new RoleDAO();
    final private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Adds a new user and the role record to the users and user_roles tables
     * @param userName The username part of the login credential
     * @param name The name of the person
     * @param pw The password part of the login credential
     * @param rolename The assigned role of the user
     * @return The unique user id for this user
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
                    added = roleDAO.add(role);
                    logger.info(role.toString());
                    logger.info(user.toString());
                    logger.info("Added user ID = " + userDAO.add(user));
                    return added;
                } else logger.info("Can't add user with unacceptable password " + pw);
            } else logger.info("Can't add user with unacceptable rolename " + rolename);
        } else logger.info("Can't add user with unacceptable username " + userName);
        return 0;
    }

    /**
     * Retrieves a specified user record
     * @param identifier The unique identifier
     * @return The entire user record
     */
    public entity.User getUser(int identifier) {
        return userDAO.get(identifier);
    }

    /**
     * Removes a user from the system by removing entries in both the users and user_roles tables
     * @param identifier The unique identifier
     */
    public void removeUserWithRole(int identifier) {
        if (identifier < 1) return; // can't remove a non-existent entry
        User user = userDAO.get(identifier);
        logger.info("About to identify role to remove as " + user.getUser_name());
        Role role = getRole(user.getUser_name());
        logger.info("About to remove user " + identifier);
        userDAO.remove(identifier);
        if (role == null) {
            logger.info("Trying to remove a non-existent (null) role");
            return;
        } else {
            logger.info("about to remove role " + role.getId());
        }
        roleDAO.remove(role.getId());
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
        User user = userDAO.get(identifier);
        if (user != null) {
            logger.info("In updateUserWithRole and user is " + user.toString());
        } else {
            logger.info("In updateUserWithRole and user is null");
            return 0;
        }
        Role role = getRole(user.getUser_name()); // Have to use old userName to find role entry
        if (role != null) {
            logger.info("In updateUserWithRole and role is " + role.toString());
        } else {
            logger.info("In updateUserWithRole and role is null");
            return 0;
        }
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
                } else logger.info("Can't update user because Role name is not acceptable " + rolename);
            } else logger.info("Can't update user because password is not acceptable " + password);
        }
        else logger.info("Can't update user because username is not acceptable " + userName);
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
        List<User> users = userDAO.getAll();
        for (User user : users) {
            if (user_name.equalsIgnoreCase(user.getUser_name()) &&
                    (user_pass.equalsIgnoreCase(user.getUser_pass()))) {
                user_id = user.getId();
            }
        }
        return user_id;
    }

    /**
     * Retrieves a user's assigned role
     * @param userId The unique identifier
     * @return The assigned role value
     */
    public String DetermineRole(int userId) {
        User user = userDAO.get(userId);
        return user.getRole_name();
    }

    /**
     * Retrieves a user's personal name
     * @param userId The unique identifier
     * @return The personal name
     */
    public String getName(int userId) {
        User user = userDAO.get(userId);
        return user.getName();
    }

    /**
     * Returns the user's role record
     * @param username the user's username
     * @return the complete Role record
     */
    private Role getRole(String username) {
        List<Role> roleList = roleDAO.getAll();
        logger.info("In getRole with username = " + username);
        Role target = null;
        for (entity.Role role : roleList) {
            logger.info("In loop with username = " + role.getUser_name());
            if (username.equalsIgnoreCase(role.getUser_name())) {
                target = role;
                logger.info("found match");
            }
        }
        if (target == null) return null;

        logger.info("got role " + target.toString());
        return target;
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
        ArrayList<String> definedRoles = new ArrayList<String>();
        definedRoles.add("administrator");
        definedRoles.add("registered-user");

        return (definedRoles.contains(rolename));
    }
}