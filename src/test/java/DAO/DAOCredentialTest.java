package DAO;

import Beans.Credential;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOCredentialTest {
    DAOCredential dao;
    List<Credential> credentialList;
    int numberOfCredentials = 0;

    @Before
    public void setUp() throws Exception {
        dao = new DAOCredential();
        testSave(); // create an entry so getters can get it
        credentialList = dao.getAllCredentials();
        numberOfCredentials = credentialList.size();

    }

    @Test
    public void testGetAllCredentials() throws Exception {
        credentialList = dao.getAllCredentials();
        assertEquals("Number of Users is incorrect.  ", numberOfCredentials, credentialList.size());

    }

    @Test
    public void testGetCredential() throws Exception {
        Credential credential = credentialList.get(0);
        assertEquals("Site is incorrect.  ", credential.getSite(), "www.WaynesWorld.edu");
    }

    @Test
    public void testSave() throws Exception {
        Credential credential = new Credential(1,"www.WaynesWorld.edu", "Howlar", "12345");
        dao.save(credential);
        credentialList = dao.getAllCredentials();
        assertEquals("Number of credentials after save is incorrect", numberOfCredentials + 1, credentialList.size());
    }


    @Test
    public void testRemove() throws Exception {
        Credential credential = credentialList.get(0);
        dao.remove(credential);
        credentialList = dao.getAllCredentials();
        assertEquals("Number of credentials after save is incorrect", numberOfCredentials - 1, credentialList.size());
    }

}