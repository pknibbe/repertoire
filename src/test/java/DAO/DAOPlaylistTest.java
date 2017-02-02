package DAO;

import Beans.Playlist;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOPlaylistTest {
    DAOPlaylist dao;
    List<Playlist> playListList;
    int numberOfPlaylists = 0;

    @Before
    public void setUp() throws Exception {
        dao = new DAOPlaylist();
        testSave();
        playListList = dao.getAllPlaylists();
        numberOfPlaylists = playListList.size();
    }

    @Test
    public void testGetAllPlaylists() throws Exception {
        playListList = dao.getAllPlaylists();
        assertEquals("Number of Playlists is incorrect.  ", numberOfPlaylists, playListList.size());
    }

    @Test
    public void testGetPlaylist() throws Exception {
        Playlist playlist = playListList.get(0);
        assertEquals("Playlist song is incorrect.  ", playlist.getSong(), 12);

    }

    @Test
    public void testSave() throws Exception {
        Playlist playlist = new Playlist(12);
        dao.save(playlist);
        playListList = dao.getAllPlaylists();
        assertEquals("Number of users after save is incorrect",numberOfPlaylists + 1, playListList.size());
    }

    @Test
    public void testRemove() throws Exception {
        Playlist playlist = playListList.get(0);
        dao.remove(playlist);
        playListList = dao.getAllPlaylists();
        assertEquals("Number of users after save is incorrect",numberOfPlaylists - 1, playListList.size());
    }
}