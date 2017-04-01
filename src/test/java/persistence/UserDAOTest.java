package persistence;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.apache.log4j.Logger;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO dao;
    private entity.User user;
    private int numberOfUsers;
    private List<entity.User> userList;
    //final Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void setup() throws Exception {
        dao = new UserDAO();
        userList = dao.getAll();
        justAdd(); // make sure table is not empty for purpose of test
        userList = dao.getAll();
        numberOfUsers = userList.size();
        }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (entity.User user : userList) {
            String thisName = user.getName();
            if (thisName.equalsIgnoreCase("Obama")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId(); // get the id of the most recent addition
        user = dao.get(id); // get the user by id
        assertEquals("Names don't match", "Obama", user.getName());
    }

    @Test
    public void testAdd() throws Exception {
        user = new entity.User("Beth", "Trump", "Pass_Word", "readOnly");
        dao.add(user);
        userList = dao.getAll();
        assertEquals("Add did not work: ", numberOfUsers + 1, userList.size());
    }

    @Test
    public void testModifyUserName() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        user.setUser_name("Johanna");
        dao.modify(user);
        user = dao.get(id);
        assertEquals("Username not modified", "Johanna", user.getUser_name());
        userList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfUsers, userList.size());
    }

    @Test
    public void testModifyName() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        user.setName("Johanna");
        dao.modify(user);
        user = dao.get(id);
        assertEquals("Name not modified", "Johanna", user.getName());
        userList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfUsers, userList.size());
    }

    @Test
    public void testModifyPw() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        user.setUser_pass("Johanna");
        dao.modify(user);
        user = dao.get(id);
        assertEquals("Password not modified", "Johanna", user.getUser_pass());
        userList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfUsers, userList.size());
    }

    @Test
    public void testModifyRole() throws Exception {
        user = userList.get(userList.size() - 1); // retrieve most recent addition to table
        int id = user.getId();
        user.setRole_name("Nonsense");
        dao.modify(user);
        user = dao.get(id);
        assertEquals("Role not modified", "Nonsense", user.getRole_name());
        userList = dao.getAll();
        assertEquals("Modify added an entry!", numberOfUsers, userList.size());
    }

    private void justAdd() {
        user = new entity.User("Bach", "Obama", "Pass_Word", "readOnly");
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
            } else if (thisName.equalsIgnoreCase("Obama")) {
                dao.remove(user.getId());
            } else if (thisName.equalsIgnoreCase("Johanna")) {
                dao.remove(user.getId());
            }
        }
    }
}