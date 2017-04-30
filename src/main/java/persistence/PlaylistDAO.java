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
    public List<Playlist> getAllMine(int user_id) throws HibernateException {
        Session session = getSession();
        Query query = session.createQuery("FROM Playlist P WHERE P.owner.id = :user_id");
        query.setParameter("user_id", user_id);
        List<Playlist> playlists = query.list();
        session.close();
        return playlists;
    }

    /**
     * Removes a playlist after making safety checks for ownership and sharing status
     * @param listID The system ID of the playlist
     * @param user_id The user trying to delete the playlist
     */
    public void remove(int listID, int user_id) {
        Playlist playlist = read(listID);
        if (playlist.getOwner().getId() != user_id) return; // Non-owner can't delete a playlist
        if (new SharedDAO().isShared(listID)) return;
        delete(playlist);
    }

}
