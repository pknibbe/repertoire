package engines;

import java.util.*;

import entity.Role;
import entity.User;
import persistence.RoleDAO;
import persistence.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Test the methods of the RoleAndUserManager class
 * Created by peter on 2/16/2017.
 */
public class RoleAndUserManagerTest {

    private RoleAndUserManager target;
    private List<User> userList;
    private List<Role> roleList;
    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private final Logger logger = Logger.getLogger(this.getClass());
    private int numberOfUsers;
    private int numberOfRoles;

    @Before
    public void setup() throws Exception {
        target = new RoleAndUserManager();
        userList = userDAO.getAll();
        roleList = roleDAO.getAll();
        logger.info("In @Before, userList has " + userList.size() + "entries");
        logger.info("In @Before, roleList has " + roleList.size() + "entries");
        justAdd(); // make sure tables are not empty for purpose of test
        userList = userDAO.getAll();
        roleList = roleDAO.getAll();
        logger.info("After justAdd, userList has " + userList.size() + "entries");
        logger.info("After justAdd, roleList has " + roleList.size() + "entries");
        numberOfUsers = userList.size();
        numberOfRoles = roleList.size();
    }

    @Test
    public void testAdd() throws Exception {
        logger.info("*** Start of testAdd ***");
        target.addUserWithRole("Thomas","Dylan", "DoNotGoGently", "admin");
        roleList = roleDAO.getAll();
        logger.info("role list has " + roleList.size() + " entries after addition");
        assertEquals("Add did not work on role table: ", numberOfRoles + 1, roleList.size());
        userList = userDAO.getAll();
        logger.info("User list has " + userList.size() + " entries after addition");
        assertEquals("Add did not work on user table: ", numberOfUsers + 1, userList.size());
        logger.info("*** End of testAdd ***");
    }

    @Test
    public void testRemove() throws Exception {
        logger.info("*** Start of testRemove ***");
        User user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        target.removeUserWithRole(id);
        roleList = roleDAO.getAll();
        userList = userDAO.getAll();
        assertEquals("Remove did not work on role table: ", numberOfRoles - 1, roleList.size());
        assertEquals("Remove did not work on user table: ", numberOfUsers - 1, userList.size());
        logger.info("*** End of testRemove ***");
    }

    @Test
    public void testUpdate() throws Exception {
        logger.info("*** Start of testUpdate ***");
        User user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        target.updateUserWithRole(user.getId(), "Simon", "Paul", "Rock5844", "readOnly");
        logger.info("Updated role ID = " + user.getId());
        Role role = roleDAO.get(id);
        roleList = roleDAO.getAll();
        assertEquals("role table username not correct", "Simon", role.getUsername());
        assertEquals("role table rolename not correct", "readOnly", role.getRolename());
        assertEquals("role table size changed", numberOfRoles, roleList.size());
        user = userDAO.get(id);
        userList = userDAO.getAll();
        assertEquals("user table username not correct", "Simon", user.getUsername());
        assertEquals("user table name not correct", "Paul", user.getName());
        assertEquals("user table password not correct", "Rock5844", user.getPw());
        assertEquals("user table rolename not correct", "readOnly", user.getRole());
        assertEquals("user table size changed", numberOfRoles, roleList.size());
        logger.info("*** End of testUpdate ***");
    }

    private void justAdd() {
        target.addUserWithRole("Dylan","Bob", "Stoned2602", "edit");
    }


    @After
    public void cleanup() throws Exception {
        userList = userDAO.getAll();
        roleList = roleDAO.getAll();

        for (entity.User user : userList) {
            String thisName = user.getUsername();
            logger.info("After test, userList includes " + user.getUsername());
            if (thisName.equalsIgnoreCase("Dylan")) {
                target.removeUserWithRole(user.getId());
            } else if (thisName.equalsIgnoreCase("Simon")) {
                target.removeUserWithRole(user.getId());
            } else if (thisName.equalsIgnoreCase("Thomas")) {
                target.removeUserWithRole(user.getId());
            }
        }
    }
}
