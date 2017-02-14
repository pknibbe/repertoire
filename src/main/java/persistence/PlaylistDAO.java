package persistence;

import entity.Playlist;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by peter on 2/13/2017.
 */
public class PlaylistDAO {
    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all Playlists
     *
     * @return All Playlists
     */
    public List<Playlist> getAll() throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Playlist> Playlists = session.createCriteria(Playlist.class).list();
        session.close();
        return Playlists;
    }

    /** Get a single Playlist for the given id
     *
     * @param id Playlist's id
     * @return Playlist
     */
    public Playlist get(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Playlist Playlist = (Playlist) session.get(Playlist.class, id);
        session.close();
        return Playlist;
    }

    /** save a new Playlist
     * @param Playlist
     * @return id the id of the inserted record
     */
    public int add(Playlist Playlist) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info("Saving Playlist " + Playlist.toString());
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
        logger.info("Updating Playlist " + updatedPlaylist.toString());
        logger.info(updatedPlaylist.toString());
        Playlist sessionPlaylist = (Playlist) session.get(Playlist.class, updatedPlaylist.getId());
        sessionPlaylist.setName(updatedPlaylist.getName());
        logger.info("Updating Playlist " + sessionPlaylist.getName());
        logger.info(sessionPlaylist.toString());
        Playlist resultantPlaylist = (Playlist) session.merge(sessionPlaylist);
        logger.info("Updated Playlist " + resultantPlaylist.toString());
        transaction.commit();
        session.close();
        return resultantPlaylist.getId();
    }

    /**
     * Removes a Playlist
     *
     * @param id ID of Playlist to be removed
     */
    public void remove(int id) throws HibernateException {
        logger.info("In dao.remove with id = " + id);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Playlist Playlist = (Playlist) session.get(Playlist.class, id);
        logger.info("In dao. remove with Playlist " + Playlist.toString());
        session.delete(Playlist);
        transaction.commit();
        session.close();
    }
}
