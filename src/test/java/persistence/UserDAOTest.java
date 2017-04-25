package persistence;

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

    @Before
    public void setup() throws Exception {
        originalNumberOfUsers = dao.getAll().size();
        user = new User("hugeOne111", "Donald", "TrumpWhiteHouse", "registered-user"); // make sure table is not empty for purpose of test
        newUserID = dao.add(user);
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
    public void testGet() throws Exception {
        user = dao.get(newUserID);
        assertEquals("UserNames don't match", "hugeOne111", user.getUser_name());
        assertEquals("Names don't match", "Donald", user.getName());
        assertEquals("Passwords don't match", "TrumpWhiteHouse", user.getUser_pass());
        assertEquals("Roles don't match", "registered-user", user.getRole_name());
    }

    @Test
    public void testModifyUserName() throws Exception {
        user = dao.get(newUserID); // retrieve most recent addition to table
        user.setUser_name("Johanna");
        dao.modify(user);
        //dao.modify(user);
        user = dao.get(newUserID); // retrieve most recent addition to table
        assertEquals("Username not modified as expected ", "Johanna", user.getUser_name());
    }

    @Test
    public void testModifyName() throws Exception {
        user = dao.get(newUserID); // retrieve most recent addition to table
        user.setName("Johanna");
        dao.modify(user);
        //dao.modify(user);
        user = dao.get(newUserID); // retrieve most recent addition to table
        assertEquals("Username not modified as expected ", "Johanna", user.getName());
    }

    @Test
    public void testModifyPw() throws Exception {
        user = dao.get(newUserID); // retrieve most recent addition to table
        user.setUser_pass("Johanna");
        dao.modify(user);
        //dao.modify(user);
        user = dao.get(newUserID); // retrieve most recent addition to table
        assertEquals("Password not modified as expected ", "Johanna", user.getUser_pass());
    }

    @Test
    public void testModifyRole() throws Exception {
        user = dao.get(newUserID); // retrieve most recent addition to table
        user.setRole_name("Johanna");
        dao.modify(user);
        user = dao.get(newUserID); // retrieve most recent addition to table
        assertEquals("Role not modified as expected ", "Johanna", user.getRole_name());
    }

    @Test
    public void testAuthenticated() throws Exception {
        assertTrue(dao.authenticated(newUserID));
        assertFalse(dao.authenticated(null));
        assertFalse(dao.authenticated(-1));
    }

    @Test
    public void testVerifyCredentials() throws Exception {
        user = dao.get(newUserID); // retrieve most recent addition to table
        assertEquals(newUserID, dao.verifyCredentials(user.getUser_name(), user.getUser_pass()));
        assertEquals(0, dao.verifyCredentials(user.getName(), user.getUser_pass()));
        assertEquals(0, dao.verifyCredentials(user.getUser_name(), user.getRole_name()));
    }

    @Test
    public void testGetOtherUserNames() throws Exception {
        boolean found = false;
        for (String userName : dao.getOtherUserNames(0)){
            if (userName.equalsIgnoreCase("Donald")) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetIdByName() throws Exception {
        assertEquals((long) newUserID, (long) dao.getIdByName("Donald"));
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Donald", dao.getName(newUserID));
    }

    @Test
    public void testDetermineRole() throws Exception {
        assertEquals("registered-user", dao.determineRole(newUserID));
    }

    @Test
    public void testInputs() throws Exception {
        assertTrue(dao.checkUsername("Theodophilus"));
        assertFalse(dao.checkUsername("Jo"));
        assertTrue(dao.checkPassword("OpenSesame"));
        assertFalse(dao.checkPassword("Hello?"));
        assertTrue(dao.checkRoleName("administrator"));
        assertFalse(dao.checkRoleName("bossMan"));
        assertTrue(dao.checkRoleName("registered-user"));
        assertTrue(dao.checkRoleName("guest"));
    }

    @After
    public void cleanup() throws Exception {
        dao.remove(newUserID);
        numberOfUsers = dao.getAll().size();
        assertEquals("Added and removed one, but found ", 0, numberOfUsers - originalNumberOfUsers);
    }
}