package persistence;

import entity.Playlist;
//import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlaylistDAO {
    //private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all Playlists
     *
     * @return All Playlists
     */
    public List<Playlist> getAll() throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Playlist> playlists = session.createCriteria(Playlist.class).list();
        session.close();
        return playlists;
    }

    /** Get a single Playlist for the given id
     *
     * @param id Playlist's id
     * @return Playlist
     */
    public Playlist get(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Playlist playlist = (Playlist) session.get(Playlist.class, id);
        session.close();
        return playlist;
    }

    /** save a new Playlist
     * @param Playlist The list of songs
     * @return id the id of the inserted record
     */
    public int add(Playlist Playlist) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(Playlist);
        transaction.commit();
        session.close();
        return id;
    }

    /** modify a Playlist record
     * @param updatedPlaylist the version of the Playlist with the new information
     * @return id the id of the updated record
     */
    public int modify(Playlist updatedPlaylist) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Playlist sessionPlaylist = (Playlist) session.get(Playlist.class, updatedPlaylist.getPlaylist_id());
        sessionPlaylist.setName(updatedPlaylist.getName());
        Playlist resultantPlaylist = (Playlist) session.merge(sessionPlaylist);
        transaction.commit();
        session.close();
        return resultantPlaylist.getPlaylist_id();
    }

    /**
     * Removes a Playlist
     *
     * @param id ID of Playlist to be removed
     * @return id the id of the removed record
     */
    public int remove(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Playlist Playlist = (Playlist) session.get(Playlist.class, id);
        session.delete(Playlist);
        transaction.commit();
        session.close();
        return id;
    }
}
