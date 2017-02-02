package DAO;

import Beans.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOUserTest {
    DAOUser dao;
    List<User> userList;
    int numberOfUsers = 0;

    @Before
    public void setup() {
        dao = new DAOUser();
        userList = dao.getAllUsers();
        numberOfUsers = userList.size();
    }


    @Test
    public void testGetAllUsers() throws Exception {
        userList = dao.getAllUsers();
        assertEquals("Number of Users is incorrect.  ",numberOfUsers, userList.size());
    }

    @Test
    public void testGetUser() throws Exception {
        User user1 = userList.get(1);
        User user2 = dao.getUser(user1.getId());
        assertEquals("User name is incorrect.  ", user2.getUsername(), "pknibbe");
    }

    @Test
    public void testSave() throws Exception {
        User user = new User("Knibbe","password", "theknibbes@gmail.com", "2157049331",12);
        dao.save(user);
        userList = dao.getAllUsers();
        assertEquals("Number of users after save is incorrect",numberOfUsers + 1, userList.size());
    }

    @Test
    public void testRemove() throws Exception {
        User user = userList.get(1);
        dao.remove(user);
        userList = dao.getAllUsers();
        assertEquals("Number of users after remove is incorrect", numberOfUsers - 1, userList.size());
    }

}