package persistence;

import entity.Song;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Created by peter on 2/13/2017.
 */
public class SongDAO {

    private final Logger logger = Logger.getLogger(this.getClass());

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

    /** save a new Song
     * @param Song
     * @return id the id of the inserted record
     */
    public int add(Song Song) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info("Saving Song " + Song.toString());
        int id = (Integer) session.save(Song);
        transaction.commit();
        session.close();
        return id;
    }

    /** modify a Song record
     * @param updatedSong the version of the Song with the new information
     * @return id the id of the updated record
     */
    public int modify(Song updatedSong) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info("Updating Song " + updatedSong.toString());
        logger.info(updatedSong.toString());
        Song sessionSong = (Song) session.get(Song.class, updatedSong.getId());
        sessionSong.setLocation(updatedSong.getLocation());
        sessionSong.setDescription(updatedSong.getDescription());
        sessionSong.setPlaylist_id(updatedSong.getPlaylist_id());
        logger.info("Updating Song " + sessionSong.getLocation());
        logger.info(sessionSong.toString());
        Song resultantSong = (Song) session.merge(sessionSong);
        logger.info("Updated Song " + resultantSong.toString());
        transaction.commit();
        session.close();
        return resultantSong.getId();
    }

    /**
     * Removes a Song
     *
     * @param id ID of Song to be removed
     */
    public void remove(int id) throws HibernateException {
        logger.info("In dao.remove with id = " + id);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Song Song = (Song) session.get(Song.class, id);
        logger.info("In dao. remove with Song " + Song.toString());
        session.delete(Song);
        transaction.commit();
        session.close();
    }
}
