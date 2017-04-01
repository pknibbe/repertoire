package engines;

import java.util.*;

import entity.Role;
import entity.User;
import persistence.RoleDAO;
import persistence.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.apache.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Test the methods of the UserManager class
 * Created by peter on 2/16/2017.
 */
public class UserManagerTest {

    private UserManager target;
    private List<User> userList;
    private List<Role> roleList;
    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    //private final Logger logger = Logger.getLogger(this.getClass());
    private int numberOfUsers;
    private int numberOfRoles;

    @Before
    public void setup() throws Exception {
        target = new UserManager();
        userList = userDAO.getAll();
        roleList = roleDAO.getAll();
        justAdd(); // make sure tables are not empty for purpose of test
        userList = userDAO.getAll();
        roleList = roleDAO.getAll();
        numberOfUsers = userList.size();
        numberOfRoles = roleList.size();
    }

    @Test
    public void testAdd() throws Exception {
        target.addUserWithRole("Thomas","Dylan", "DoNotGoGently", "registered-user");
        roleList = roleDAO.getAll();
        assertEquals("Add did not work on role table: ", numberOfRoles + 1, roleList.size());
        userList = userDAO.getAll();
        assertEquals("Add did not work on user table: ", numberOfUsers + 1, userList.size());
    }

    @Test
    public void testRemove() throws Exception {
        User user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        target.removeUserWithRole(id);
        roleList = roleDAO.getAll();
        userList = userDAO.getAll();
        assertEquals("Remove did not work on role table: ", numberOfRoles - 1, roleList.size());
        assertEquals("Remove did not work on user table: ", numberOfUsers - 1, userList.size());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        target.updateUserWithRole(user.getId(), "Simon", "Paul", "Rock5844", "registered-user");
        Role role = roleDAO.get(id);
        roleList = roleDAO.getAll();
        assertEquals("role table username not correct", "Simon", role.getUser_name());
        assertEquals("role table rolename not correct", "registered-user", role.getRole_name());
        assertEquals("role table size changed", numberOfRoles, roleList.size());
        user = userDAO.get(id);
        userList = userDAO.getAll();
        assertEquals("user table username not correct", "Simon", user.getUser_name());
        assertEquals("user table name not correct", "Paul", user.getName());
        assertEquals("user table password not correct", "Rock5844", user.getUser_pass());
        assertEquals("user table rolename not correct", "registered-user", user.getRole_name());
        assertEquals("user table size changed", numberOfRoles, roleList.size());
    }

    private void justAdd() {
        target.addUserWithRole("Dylan","Bob", "Stoned2602", "registered-user");
    }


    @After
    public void cleanup() throws Exception {
        userList = userDAO.getAll();
        roleList = roleDAO.getAll();

        for (entity.User user : userList) {
            String thisName = user.getUser_name();
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
