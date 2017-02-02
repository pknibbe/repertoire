package DAO;

import Beans.Playlist;
import Utilities.SessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOPlaylist {
    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all Playlists
     *
     * @return All Playlists
     */
    public List<Playlist> getAllPlaylists() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return session.createCriteria(Playlist.class).list();
    }

    /** Get a single Playlist for the given id
     *
     * @param id Playlist's id
     * @return Playlist
     */
    public Playlist getPlaylist(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (Playlist) session.get(Playlist.class, id);
    }

    /** save or update Playlist
     * @param playlist
     */
    public void save(Playlist playlist) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info(session.save(playlist));
        transaction.commit();
    }

    /**
     * Removes a playlist
     *
     * @param playlist user to be removed
     */
    public void remove(Playlist playlist) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(playlist);
        transaction.commit();
    }


}
