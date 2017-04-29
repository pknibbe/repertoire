package persistence;

import entity.Shared;
import entity.User;
import entity.Playlist;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class SharedDAO extends GenericDAO<Shared, Integer> {
   private final Logger logger = Logger.getLogger(this.getClass());
   final static private UserDAO userDAO = new UserDAO();
   final static private PlaylistDAO playlistDAO = new PlaylistDAO();

    public SharedDAO() { super(Shared.class); }


    /** Return a list of all Shared records
     *
     * @return All Shared records
     */
    public List<Shared> getAll() throws HibernateException {
        Session session = getSession();
        List<Shared> sharings = session.createCriteria(Shared.class).list();
        session.close();
        return sharings;
    }

    /**
     * Adds the playlist sharing record to the database
     * @param playlist_id The system ID of the playlist
     * @param user_id The user to be granted access
     * @return zero or the system ID of the sharing record
     *
     * Checks to see if playlist is already shared with the user to avoid adding a duplicate record
     */
    public int share(int playlist_id, int user_id) throws HibernateException {
        if (find(playlist_id, user_id) > 0) {
            return find(playlist_id, user_id); // record is already in the database
        }
        Shared shared = new Shared(playlistDAO.read(playlist_id), userDAO.read(user_id));
        return create(shared);
    }

    /**
     * Looks up the system ID of a Sharing record by playlist ID and user ID
     * @param playlist_id The system ID of the playlist
     * @param user_id The system ID of the user with whom the playlist is shared
     * @return zero or the system ID of the sharing record
     */
    public int find(int playlist_id, int user_id) throws HibernateException {
        int id = 0;
        Session session = getSession();
        Query query = session.createQuery(new StringBuilder().append("SELECT S.id FROM Shared S")
                .append(" WHERE S.playlist.playlist_id = :playlist_id ")
                .append(" and S.recipient.id = :user_id").toString());
        query.setParameter("playlist_id", playlist_id);
        query.setParameter("user_id", user_id);
        if (query.list() != null) {
            List<Integer> ids = query.list();
            if (ids.size() > 0) {
                id = ids.get(0);
            }
        }
        return id;
    }

    /**
     * Indicates whether a playlist is currently being shared with another user
     * @param playlist_id The system ID of the playlist
     * @return True if it is currently being shared. Otherwise, false
     */
    public boolean isShared(int playlist_id) throws HibernateException {
        boolean isIt = false;
        Session session = getSession();
        Query query = session.createQuery("SELECT S.id FROM Shared S WHERE S.playlist.playlist_id = :playlist_id");
        query.setParameter("playlist_id", playlist_id);
        if (query.list() != null) {
            List<Integer> ids = query.list();
            if (ids.size() > 0) {
                isIt = true;
            }
        }
        return isIt;
    }

    /**
     * Returns the list of all non-owners sharing a playlist
     * @param playlist_id The system ID of the playlist
     * @return the list of user IDs
     */
    public ArrayList<Integer> sharing(int playlist_id) throws HibernateException {
        ArrayList<Integer> userIDs = new ArrayList<>();
        for (Shared list : getAll()) {
            if (list.getPlaylist().getPlaylist_id() == playlist_id) {
                userIDs.add(list.getRecipient().getId());
            }
        }
        return userIDs;
    }

    /**
     * Removes sharing of the specified playlist with the specified user
     * @param userID The system ID of the user
     * @param playlist_id The system ID of the playlist
     */
    public void remove(int userID, int playlist_id) throws HibernateException {
        delete(read(find(playlist_id, userID)));
    }

}
