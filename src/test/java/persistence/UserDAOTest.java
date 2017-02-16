package persistence;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/3/2017.
 */
public class UserDAOTest {

    UserDAO dao;
    entity.User user;
    int numberOfUsers;
    List<entity.User> userList;
    final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setup() throws Exception {
        dao = new UserDAO();
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
        assertEquals("Names don't match", "Trump", user.getName());
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
        user.setUsername("Johanna");
        logger.info(user.toString());
        logger.info("Updated user ID = " + dao.modify(user));
        //dao.modify(user);
        user = dao.get(id);
        assertEquals("Username not modified", "Johanna", user.getUsername());
        userList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfUsers, userList.size());
    }

    @Test
    public void testModifyName() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        user.setName("Johanna");
        logger.info(user.toString());
        logger.info("Updated user ID = " + dao.modify(user));
        //dao.modify(user);
        user = dao.get(id);
        assertEquals("Name not modified", "Johanna", user.getName());
        userList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfUsers, userList.size());
    }

    @Test
    public void testModifyPw() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        user.setPw("Johanna");
        logger.info(user.toString());
        logger.info("Updated user ID = " + dao.modify(user));
        //dao.modify(user);
        user = dao.get(id);
        assertEquals("Password not modified", "Johanna", user.getPw());
        userList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfUsers, userList.size());
    }

    private void justAdd() {
        user = new entity.User("Rose", "Trump", "Pass_Word");
        dao.add(user);
    }

    @Test
    public void testRemove() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        dao.remove(id);
        userList = dao.getAll();
        assertEquals("remove did not work: ", numberOfUsers - 1, userList.size());
    }


    @After
    public void cleanup() throws Exception {
        userList = dao.getAll();

        for (entity.User user : userList) {
            String thisName = user.getName();
            if (thisName.equalsIgnoreCase("Trump")) {
                dao.remove(user.getId());
            } else if (thisName.equalsIgnoreCase("Johanna")) {
                dao.remove(user.getId());
            }
        }
    }
}