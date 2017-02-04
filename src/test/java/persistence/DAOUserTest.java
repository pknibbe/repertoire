package persistence;

import java.util.*;
import org.junit.Before;
import org.junit.Test;
import org.apache.log4j.*;
import org.apache.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/3/2017.
 */
public class DAOUserTest {

    DAOUser dao;
    entity.User user;
    int numberOfUsers;
    List<entity.User> userList;
    final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setup() throws Exception {
        dao = new DAOUser();
        userList = dao.getAll();
        logger.info("In @Before, userList has " + userList.size() + "entries");
        justAdd(); // make sure table is not empty for purpose of test
        userList = dao.getAll();
        logger.info("After justAdd, userList has " + userList.size() + "entries");
        numberOfUsers = userList.size();
        }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (entity.User user : userList) {
            String thisName = user.getName();
            if (thisName.equalsIgnoreCase("Rose")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId(); // get the id of the most recent addition
        user = dao.get(id); // get the user by id
        assertEquals("Names don't match", "Rose", user.getName());
    }

    @Test
    public void testAdd() throws Exception {
        justAdd();
        userList = dao.getAll();
        assertEquals("Add did not work: ", numberOfUsers + 1, userList.size());
    }

    @Test
    public void testModifyUserName() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        user.setUsername("Rose");
        dao.add(user);
        user = dao.get(id);
        assertEquals("Username not modified", "RoseMarie", user.getUsername());
    }

    private void justAdd() {
        user = new entity.User("RoseMarie", "Rose", 12);
        dao.add(user);
    }

    @Test
    public void testRemove() throws Exception {
        logger.info("In testRemove, userList has " + userList.size() + "entries");
        logger.info("In testRemove, last index calculated as " + (userList.size() - 1));
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        logger.info("About to remove " + user.toString());
        int id = user.getId();
        logger.info("About to remove " + user.getId());
        dao.remove(id);
        userList = dao.getAll();
        assertEquals("remove did not work: ", numberOfUsers - 1, userList.size());
    }

}