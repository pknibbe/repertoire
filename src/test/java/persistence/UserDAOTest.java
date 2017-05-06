package persistence;

import java.util.List;
import java.util.ArrayList;
//import org.apache.log4j.Logger;
import entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    final private UserDAO dao = new UserDAO();
    private User user;
    private int numberOfUsers;
    private int originalNumberOfUsers;
    private int newUserID;
    //final private Logger logger = Logger.getLogger(this.getClass());
    private ArrayList<User> bunch = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        originalNumberOfUsers = dao.getAll().size();
        user = new User("hugeOne111", "Donald", "TrumpWhiteHouse", "registered-user"); // make sure table is not empty for purpose of test
        newUserID = dao.create(user);
        numberOfUsers = dao.getAll().size();
        assertEquals("Added one, but found ", 1, numberOfUsers - originalNumberOfUsers);
        }

    @Test
    public void testGetAll() throws Exception {
        boolean found = false;

        for (entity.User user : dao.getAll()) {
            String thisName = user.getName();
            if (thisName.equalsIgnoreCase("Donald")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }


    @Test
    public void testOthers() throws Exception {
        boolean found = false;

        for (String name : dao.getOtherUserNames(dao.getAdminId())) {
            if (name.equalsIgnoreCase("Donald")) found = true;
        }
        assertTrue("The expected name was not found: ", found);
    }

    @Test
    public void testGet() throws Exception {
        user = dao.read(newUserID);
        assertEquals("UserNames don't match", "hugeOne111", user.getUser_name());
        assertEquals("Names don't match", "Donald", user.getName());
        assertEquals("Passwords don't match", "TrumpWhiteHouse", user.getUser_pass());
        assertEquals("Roles don't match", "registered-user", user.getRole_name());
    }

    @Test
    public void testModifyUserName() throws Exception {
        user = dao.read(newUserID); // retrieve most recent addition to table
        user.setUser_name("Johanna");
        dao.update(user);
        //dao.modify(user);
        user = dao.read(newUserID); // retrieve most recent addition to table
        assertEquals("Username not modified as expected ", "Johanna", user.getUser_name());
    }

    @Test
    public void testModifyName() throws Exception {
        user = dao.read(newUserID); // retrieve most recent addition to table
        user.setName("Johanna");
        dao.update(user);
        //dao.modify(user);
        user = dao.read(newUserID); // retrieve most recent addition to table
        assertEquals("Username not modified as expected ", "Johanna", user.getName());
    }

    @Test
    public void testModifyPw() throws Exception {
        user = dao.read(newUserID); // retrieve most recent addition to table
        user.setUser_pass("Johanna");
        dao.update(user);
        //dao.modify(user);
        user = dao.read(newUserID); // retrieve most recent addition to table
        assertEquals("Password not modified as expected ", "Johanna", user.getUser_pass());
    }

    @Test
    public void testModifyRole() throws Exception {
        user = dao.read(newUserID); // retrieve most recent addition to table
        user.setRole_name("Johanna");
        dao.update(user);
        user = dao.read(newUserID); // retrieve most recent addition to table
        assertEquals("Role not modified as expected ", "Johanna", user.getRole_name());
    }

    @Test
    public void testVerifyCredentials() throws Exception {
        /*        int user_id = userDAO.getIdByUsername(userName);

        if (!userDAO.verifyCredentials(user_id, userName, password))*/
        user = dao.read(newUserID); // retrieve most recent addition to table
        assertTrue(dao.verifyCredentials(newUserID, user.getUser_name(), user.getUser_pass()));
        assertFalse(dao.verifyCredentials(0, user.getName(), user.getUser_pass()));
        assertFalse(dao.verifyCredentials(newUserID, user.getUser_name(), user.getRole_name()));
    }

    @Test
    public void testGetOtherUsers() {
        boolean includesHappy = false;
        boolean includesSleepy = false;
        //First, create a bunch more users
        User testUser = new User("GrumpyDwarf", "Grumpy", "GrumpyDwarf", "registered-user");
        bunch.add(testUser);
        dao.create(testUser);
        testUser = new User("DopeyDwarf", "Dopey", "DopeyDwarf", "registered-user");
        bunch.add(testUser);
        dao.create(testUser);
        testUser = new User("SleepyDwarf", "Sleepy", "SleepyDwarf", "registered-user");
        bunch.add(testUser);
        dao.create(testUser);
        testUser = new User("SneezyDwarf", "Sneezy", "SneezyDwarf", "registered-user");
        bunch.add(testUser);
        dao.create(testUser);
        testUser = new User("HappyDwarf", "Happy", "HappyDwarf", "registered-user");
        bunch.add(testUser);
        dao.create(testUser);

        //Second, create a subset
        ArrayList<User> subBunch = new ArrayList<>();
        subBunch.add(bunch.get(0));
        subBunch.add(bunch.get(1));
        subBunch.add(bunch.get(2));

        int pariah = bunch.get(3).getId();  //Is Sneezy!

        List<User> others = dao.getOtherUsers(subBunch, pariah);
        for (User miner : others) {
            if (miner.getId() == bunch.get(2).getId()) {
                includesSleepy = true;
            } else if (miner.getId() == bunch.get(4).getId()) {
                includesHappy = true;
            }
        }
        assertTrue(includesHappy);
        assertFalse(includesSleepy);
    }

    @Test
    public void testGetIdByName() throws Exception {
        assertEquals((long) newUserID, (long) dao.getIdByName("Donald"));
    }


    @Test
    public void testGetIdByUsername() throws Exception {
        assertEquals((long) newUserID, (long) dao.getIdByUsername("hugeOne111"));
    }

    @Test
    public void testGetAdminId() {
        assertTrue(dao.read(dao.getAdminId()).getRole_name().equalsIgnoreCase("administrator"));
    }


    @Test
    public void testInputs() throws Exception {
        assertTrue(UserDAO.checkUsername("Theodophilus"));
        assertFalse(UserDAO.checkUsername("Jo"));
        assertTrue(UserDAO.checkPassword("OpenSesame"));
        assertFalse(UserDAO.checkPassword("Hello?"));
        assertTrue(UserDAO.checkRoleName("administrator"));
        assertFalse(UserDAO.checkRoleName("bossMan"));
        assertTrue(UserDAO.checkRoleName("registered-user"));
        assertTrue(UserDAO.checkRoleName("guest"));
    }

    @After
    public void cleanup() throws Exception {
        dao.delete(user);
        for (User testUser : bunch) dao.delete(testUser);
        numberOfUsers = dao.getAll().size();
        assertEquals(0,numberOfUsers - originalNumberOfUsers);
    }
}