package persistence;

import entity.PropertyManager;
import entity.Song;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import java.util.List;

public class SongDAO extends GenericDAO<Song, Integer> {
    private final static PropertyManager propertyManager = new PropertyManager();
    private static final String repository = System.getenv(propertyManager.getProperty("home")) +
            propertyManager.getProperty("musicDir");
    private final Logger logger = Logger.getLogger(this.getClass());

    public SongDAO() { super(Song.class); }

    /** Return a list of all songs
     *
     * @return All songs
     */
    public List<Song> getAll() throws HibernateException {
        Session session = getSession();
        List<Song> songs = session.createCriteria(Song.class).list();
        session.close();
        return songs;
    }

    /** Return a list of all songs in a playlist
     * @param playlist_id The system ID of the playlist
     * @return All songs in a playlist
     */
    public List<Song> getAllThese(int playlist_id) throws HibernateException {
        Session session = getSession();
        Query query = session.createQuery("From Song S WHERE S.playlist.playlist_id = :playlist_id");
        query.setParameter("playlist_id", playlist_id);
        List<Song> songs = query.list();
        session.close();
        return songs;
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
        Session session = getSession();
        Query query = session.createQuery("SELECT S.id FROM Song S WHERE S.playlist.playlist_id = :playlist_id and S.location = :location");
        query.setParameter("playlist_id", playlist_id);
        query.setParameter("location", location);
        if (query.list() != null) {
            List<Integer> ids = query.list();
            if (ids.size() > 0) {
                found = true;
            }
        }
        session.close();
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
        Session session = getSession();
        Query query = session.createQuery("SELECT S.id FROM Song S WHERE S.location = :location");
        query.setParameter("location", location);
        if (query.list() != null) {
            List<Integer> ids = query.list();
            if (ids.size() > 0) {
                found = true;
            }
        }
        session.close();
        return found;
    }


    public String getRepository() {
        String place = repository;
        return place;
    }

    /**
     * Retrieves the location of the current song
     * @return The song location
     */
    public String getLocation(int songID) {
        Song currentSong = read(songID);
        if (currentSong == null) {
            return null;
        }
        else {
            return currentSong.getLocation();
        }
    }

    /**
     * Retrieves the system identifiers of the songs in a playlist
     * @param list_ID The system identifier of the playlist
     * @return The song identifiers
     */
    public List<Integer> getSongIds(int list_ID) {
        Session session = getSession();
        Query query = session.createQuery("Select S.id From Song S WHERE S.playlist.playlist_id = :playlist_id");
        query.setParameter("playlist_id", list_ID);
        List<Integer> songs = query.list();
        session.close();
        return songs;
    }
}
