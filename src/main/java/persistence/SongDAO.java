package persistence;

import entity.PropertyManager;
import entity.Song;
//import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class SongDAO {
    private final static PropertyManager propertyManager = new PropertyManager();
    private static final String repository = System.getenv(propertyManager.getProperty("home")) +
            propertyManager.getProperty("musicDir");
    //private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all songs
     *
     * @return All songs
     */
    public List<Song> getAll() throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Song> songs = session.createCriteria(Song.class).list();
        session.close();
        return songs;
    }

    /** Return a list of all songs in a playlist
     * @param playlist_id The system ID of the playlist
     * @return All songs in a playlist
     */
    public List<Song> getAllThese(int playlist_id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Query query = session.createQuery("From Song S WHERE S.playlist.playlist_id = playlist_id");
        List<Song> songs = query.list();
        session.close();
        return songs;
    }

    /** Get a single Song for the given id
     *
     * @param id Song's id
     * @return Song
     */
    public Song get(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Song Song = (Song) session.get(Song.class, id);
        session.close();
        return Song;
    }


    /**
     * Saves a song to the database with a playlist association
     * @param Song The song record to add
     * @return The system ID of the song
     * @throws HibernateException Database access issue
     */
    public int add(Song Song) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(Song);
        transaction.commit();
        session.close();
        return id;
    }

    /** modify a Song record
     * @param updatedSong the version of the Song with the new information
     */
    public void modify(Song updatedSong) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Song sessionSong = (Song) session.get(Song.class, updatedSong.getId());
        sessionSong.setLocation(updatedSong.getLocation());
        sessionSong.setDescription(updatedSong.getDescription());
        sessionSong.setPlaylist(updatedSong.getPlaylist());
        session.merge(sessionSong);
        transaction.commit();
        session.close();
    }


    /**
     * Returns whether or not a particular song is already associated with a particular playlist
     *
     * @param location    The place where the song should be
     * @param playlist_id The system ID of the playlist
     * @return whether or not it is there
     */
    public boolean alreadyThere(String location, int playlist_id) {
        boolean found = false;
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Song S WHERE S.playlist.playlist_id = playlist_id and S.location = location");
        if (query.list() != null) { found = true; }
        return found;
    }


    /**
     * Returns whether or not a particular song is already uploaded
     *
     * @param location    The place where the song should be
     * @return whether or not it is there
     */
    public boolean exists(String location) {
        boolean found = false;
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Song S WHERE S.location = location");
        if (query.list() != null) { found = true; }
        return found;
    }

    /**
     * Removes a Song
     *
     * @param id ID of Song to be removed
     */
    public void remove(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Song Song = (Song) session.get(Song.class, id);
        session.delete(Song);
        transaction.commit();
        session.close();
    }


    public String getRepository() {
        return repository;
    }

    /**
     * Retrieves the location of the current song
     * @return The song location
     */
    public String getLocation(int songID) {
        Song currentSong = get(songID);
        if (currentSong == null) {
            return null;
        }
        else {
            return currentSong.getLocation();
        }
    }

    /**
     * Retrieves the songs in a playlist
     * @param list_ID The system identifier of the playlist
     * @return The songs
     */
    public ArrayList<Song> getAll(int list_ID) {
        ArrayList<Song> songList = new ArrayList<>();
        List<Song> songs = getAll();
        for (Song song : songs) {
            if (list_ID == song.getPlaylist().getPlaylist_id()) {
                songList.add(song);
            }
        }
        return songList;
    }

    /**
     * Retrieves the system identifiers of the songs in a playlist
     * @param list_ID The system identifier of the playlist
     * @return The song identifiers
     */
    public ArrayList<Integer> getSongIds(int list_ID) {
        ArrayList<Integer> songList = new ArrayList<>();
        List<Song> songs = getAll();
        for (Song song : songs) {
            if (list_ID == song.getPlaylist().getPlaylist_id()) {
                songList.add(song.getId());
            }
        }
        return songList;
    }

}
