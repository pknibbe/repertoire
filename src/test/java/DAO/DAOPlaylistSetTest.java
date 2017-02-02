package DAO;

import Beans.PlaylistSet;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOPlaylistSetTest {
    DAOPlaylistSet dao;
    List<PlaylistSet> setList;
    int numberOfSets = 0;

    @Before
    public void setUp() throws Exception {
        dao = new DAOPlaylistSet();
        testSave();
        setList = dao.getAllPlaylistSets();
        numberOfSets = setList.size();
    }

    @Test
    public void testGetAllPlaylistSets() throws Exception {
        setList = dao.getAllPlaylistSets();
        assertEquals("Number of Users is incorrect.  ", numberOfSets, setList.size());
    }

    @Test
    public void testGetPlaylistSet() throws Exception {
        PlaylistSet playlistSet = setList.get(0);
        assertEquals("Set name is incorrect.  ", playlistSet.getName(), "pknibbe");
    }

    @Test
    public void testSave() throws Exception {
        PlaylistSet playlistSet = new PlaylistSet("Rocking", 5);
        dao.save(playlistSet);
        setList = dao.getAllPlaylistSets();
        assertEquals("Number of users after save is incorrect", numberOfSets + 1, setList.size());
    }

    @Test
    public void testRemove() throws Exception {
        PlaylistSet playlistSet = setList.get(0);
        dao.remove(playlistSet);
        setList = dao.getAllPlaylistSets();
        assertEquals("Number of users after save is incorrect", numberOfSets - 1, setList.size());
    }

}