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
    public void testAuthenticated() throws Exception {
        assertTrue(dao.authenticated(newUserID));
        assertFalse(dao.authenticated(null));
        assertFalse(dao.authenticated(-1));
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
    public void testGetIdByName() throws Exception {
        assertEquals((long) newUserID, (long) dao.getIdByName("Donald"));
    }


    @Test
    public void testGetIdByUsername() throws Exception {
        assertEquals((long) newUserID, (long) dao.getIdByUsername("hugeOne111"));
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
        numberOfUsers = dao.getAll().size();
        assertEquals("Added and removed one, but found ", 0, numberOfUsers - originalNumberOfUsers);
    }
}