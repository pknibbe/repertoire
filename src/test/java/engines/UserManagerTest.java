package engines;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Test the UserManager access class
 * Created by peter on 4/4/2017.
 */
public class UserManagerTest {
    private ArrayList<Integer> testIds = new ArrayList<>();
    private int originalNumberOfUsers;
    private int originalNumberOfRoleRecords;

    @Before
    public void setUp() throws Exception {
        originalNumberOfUsers = UserManager.getAllUsers().size();
        originalNumberOfRoleRecords = UserManager.getAllRoles().size();
        testIds.add(UserManager.addUserWithRole("wtpooh", "Winnie the Pooh", "I luv hunny", "registered-user"));
        testIds.add(UserManager.addUserWithRole("crobbin", "Christopher Robbin Milne", "I hate Pooh", "grouch"));
        testIds.add(UserManager.addUserWithRole("tigger", "Tigger", "wonderful", "registered-user"));
        testIds.add(UserManager.addUserWithRole("ppppiglet", "Piglet", "house", "registered-user"));
    }

    @After
    public void tearDown() throws Exception {
        for (Integer index : testIds) UserManager.removeUserWithRole(index);
        assertEquals("Test changed the users table! ", originalNumberOfUsers, UserManager.getAllUsers().size());
        assertEquals("Test changed the roles table! ", originalNumberOfRoleRecords, UserManager.getAllRoles().size());
    }

    @Test
    public void addUserWithRole() throws Exception {
        assertEquals("Expected a different length of array", 4, testIds.size());
        assertEquals("Expected a different value", (long) 0, (long) testIds.get(1)); // bad role name
        assertEquals("Expected a different value", (long) 0, (long) testIds.get(3)); // bad role name
        assertNotEquals("Expected a different value", null, testIds.get(0)); // bad role name
        assertNotEquals("Expected a different value", null, testIds.get(2)); // bad role name
    }

    @Test
    public void getOtherUserNames() throws Exception {
        boolean found = false;
        for (String name : UserManager.getOtherUserNames(testIds.get(0))) {
            if ((name != null) && (name.equalsIgnoreCase("Tigger"))) {
                found = true;
            }
        }
        assertTrue("Should have found Tigger: ", found);
    }

    @Test
    public void getUserIds() throws Exception {
        boolean found = false;
        for (Integer Id : UserManager.getUserIds()) {
            if (Objects.equals(Id, testIds.get(0))) {
                found = true;
            }
        }
        assertTrue("Should have found Pooh: ", found);
    }

    @Test
    public void getUser() throws Exception {
        assertEquals("Should have been Tigger", "Tigger",
                UserManager.getUser(testIds.get(2)).getName());
    }

    @Test
    public void updateUserWithRole() throws Exception {
        assertEquals((long) 0, UserManager.updateUserWithRole(testIds.get(2),"Tigger", "Tigger", "Tiggers R Wonderful", "grouch"));
        assertNotNull(UserManager.updateUserWithRole(testIds.get(2),"Tigger", "Tigger", "Tiggers R Wonderful", "registered-user"));
        assertEquals((int) testIds.get(2), UserManager.getIdByName("Tigger"));
    }

    @Test
    public void verifyCredentials() throws Exception {
        assertEquals(0, UserManager.verifyCredentials("Christopher", "I hate Pooh"));
        assertEquals((int) testIds.get(0), UserManager.verifyCredentials("wtpooh", "I luv hunny"));

    }

    @Test
    public void determineRole() throws Exception {
        assertEquals("registered-user", UserManager.determineRole(testIds.get(2)));
    }

    @Test
    public void authenticated() throws Exception {
        assertFalse(UserManager.authenticated(null));
        assertFalse(UserManager.authenticated(-5));
        assertTrue(UserManager.authenticated(457839));
    }

    @Test
    public void getAdminId() throws Exception {
        assertEquals(UserManager.getIdByName("Peter Knibbe"), UserManager.getAdminId());
    }
}