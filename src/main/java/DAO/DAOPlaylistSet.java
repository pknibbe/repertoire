package DAO;

import Beans.PlaylistSet;
import Utilities.SessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOPlaylistSet {
    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all PlaylistSets
     *
     * @return All PlaylistSets
     */
    public List<PlaylistSet> getAllPlaylistSets() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return session.createCriteria(PlaylistSet.class).list();
    }

    /** Get a single PlaylistSet for the given id
     *
     * @param id PlaylistSet's id
     * @return PlaylistSet
     */
    public PlaylistSet getPlaylistSet(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (PlaylistSet) session.get(PlaylistSet.class, id);
    }

    /** save or update PlaylistSet
     * @param user
     */
    public void save(PlaylistSet user) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info(session.save(user));
        transaction.commit();
    }

    /**
     * Removes a playlistSet
     *
     * @param playlistSet user to be removed
     */
    public void remove(PlaylistSet playlistSet) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(playlistSet);
        transaction.commit();
    }


}
