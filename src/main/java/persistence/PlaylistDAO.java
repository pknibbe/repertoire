package persistence;

import entity.Playlist;
import org.apache.log4j.Logger;
import org.hibernate.*;

import java.util.List;

public class PlaylistDAO extends GenericDAO<Playlist, Integer> {
    private final Logger logger = Logger.getLogger(this.getClass());

    public PlaylistDAO() { super(Playlist.class); }

    /** Return a list of all Playlists
     *
     * @return All Playlists
     */
    public List<Playlist> getAll() throws HibernateException {
        Session session = getSession();
        List<Playlist> playlists = session.createCriteria(Playlist.class).list();
        session.close();
        return playlists;
    }

    /** Return a list of all Playlists owned by a user
     *
     * @param user_id the system ID of the owner
     * @return All Playlists
     */
    @SuppressWarnings("JpaQlInspection")
    public List<Playlist> getAll(int user_id) throws HibernateException {
        Session session = getSession();
        Query query = session.createQuery("FROM Playlist P WHERE P.owner.id = :user_id");
        List<Playlist> playlists = query.list();
        query.setParameter("user_id", user_id);
        session.close();
        return playlists;
    }

}
