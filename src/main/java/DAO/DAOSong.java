package DAO;

import Beans.Song;
import Utilities.SessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOSong {

    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all songs
     *
     * @return All songs
     */
    public List<Song> getAllSongs() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return session.createCriteria(Song.class).list();
    }

    /** Get a single song for the given id
     *
     * @param id song's id
     * @return song
     */
    public Song getSong(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (Song) session.get(Song.class, id);
    }

    /** save or update song
     * @param song
     */

    public void save(Song song) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info(session.save(song));
        transaction.commit();
    }


    /**
     * Removes a song
     *
     * @param song user to be removed
     */
    public void remove(Song song) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(song);
        transaction.commit();
    }


}

