package DAO;

import Beans.Song;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOSongTest {
    DAOSong dao;
    List<Song> songList;
    int numberOfSongs = 0;

    @Before
    public void setUp() throws Exception {
        dao = new DAOSong();
        testSave();
        songList = dao.getAllSongs();
        numberOfSongs = songList.size();

    }

    @Test
    public void testGetAllSongs() throws Exception {
        songList = dao.getAllSongs();
        assertEquals("Number of Songs is incorrect.  ", numberOfSongs, songList.size());
    }

    @Test
    public void testGetSong() throws Exception {
        Song song = songList.get(0);
        assertEquals("Location of song is incorrect.  ", song.getLocation(), "C:/Music/More_Than_A_Feeling");
    }

    @Test
    public void testSave() throws Exception {
        Song song = new Song(1, "C:/Music/More_Than_A_Feeling", "More Than a Feeling", "Boston", "4:13");
        dao.save(song);
        songList = dao.getAllSongs();
        assertEquals("Number of users after save is incorrect", numberOfSongs + 1, songList.size());
    }

    @Test
    public void testRemove() throws Exception {
        Song song = songList.get(0);
        dao.remove(song);
        songList = dao.getAllSongs();
        assertEquals("Number of users after save is incorrect", numberOfSongs - 1, songList.size());
    }

}