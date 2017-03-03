package engines;

import java.util.ArrayList;
import java.util.List;
import entity.Role;
import entity.User;
import org.apache.log4j.Logger;
import persistence.RoleDAO;
import persistence.UserDAO;
/**
 * Manage changes to users and roles
 * Created by peter on 2/8/2017.
 */
public class RoleAndUserManager {

    final private UserDAO userDAO = new UserDAO();
    final private RoleDAO roleDAO = new RoleDAO();
    final private Logger logger = Logger.getLogger(this.getClass());

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

    public entity.User getUser(int identifier) {
        return userDAO.get(identifier);
    }

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

    private boolean checkRoleName(String rolename) {
        ArrayList<String> definedRoles = new ArrayList<String>();
        definedRoles.add("admin");
        definedRoles.add("edit");
        definedRoles.add("readOnly");

        return (definedRoles.contains(rolename));
    }
}