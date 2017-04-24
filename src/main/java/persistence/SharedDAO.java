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

public class SharedDAO {
   private final Logger logger = Logger.getLogger(this.getClass());
   final static private UserDAO userDAO = new UserDAO();
   final static private PlaylistDAO playlistDAO = new PlaylistDAO();

    /** Return a list of all Shared records
     *
     * @return All Shared records
     */
    public List<Shared> getAll() throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Shared> sharings = session.createCriteria(Shared.class).list();
        session.close();
        return sharings;
    }

    /** Get a single Shared for the given id
     *
     * @param id Shared's id
     * @return Shared
     */
    public Shared get(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Shared sharing = (Shared) session.get(Shared.class, id);
        session.close();
        return sharing;
    }

    /** save a new Playlist sharing record
     * @param shared The record to add to the database
     * @return id the id of the inserted record
     */
    public int add(Shared shared) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(shared);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Removes a Sharing record
     *
     * @param id ID of Sharing to be removed
     */
    public void remove(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Shared shared = (Shared) session.get(Shared.class, id);
        session.delete(shared);
        transaction.commit();
        session.close();
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
        Shared shared = new Shared(playlistDAO.get(playlist_id), userDAO.get(user_id));
        return add(shared);
    }

    /**
     * Looks up the system ID of a Sharing record by playlist ID and user ID
     * @param playlist_id The system ID of the playlist
     * @param user_id The system ID of the user with whom the playlist is shared
     * @return zero or the system ID of the sharing record
     */
    private int find(int playlist_id, int user_id) throws HibernateException {
        int id = 0;
        List<Shared> all = getAll();
        for (Shared shared : all) {
            if ((shared.getPlaylist().getPlaylist_id() == playlist_id) &&
                    (shared.getRecipient().getId() == user_id)){
                id = shared.getId();
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
        List<Shared> all = getAll();
        for (Shared shared : all) {
            if (shared.getPlaylist().getPlaylist_id() == playlist_id) {
                isIt = true;
            }
        }
        return isIt;
    }


    /**
     * Retrieves the system IDS of playlists shared with the user
     * @param user_id The system ID of the user
     * @return The playlist IDs
     */
    public ArrayList<Integer> getAll(int user_id) throws HibernateException {
        ArrayList<Integer> list_IDs = new ArrayList<>();
        List<Shared> shared = getAll();
        for (Shared playlist : shared) {
            if (playlist.getRecipient().getId() == user_id) {
                list_IDs.add(playlist.getId());
            }
        }
        return list_IDs;
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
     * Returns the list of all non-owners NOT sharing a playlist
     * @param playlist_id The system ID of the playlist
     * @return the list of user IDs
     */
    public ArrayList<Integer> notSharing(int playlist_id) throws HibernateException {
        ArrayList<Integer> userIDs = userDAO.getUserIds();

        for (Shared record : getAll()) {
            if (record.getPlaylist().getPlaylist_id() == playlist_id) {
                if (userIDs != null) {
                    userIDs.remove((Integer) record.getRecipient().getId());
                }
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
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        User recipient = userDAO.get(userID);
        Playlist playlist = playlistDAO.get(playlist_id);
        Query query = session.createQuery("DELETE FROM shared WHERE recipient = :recipient and playlist = :playlist");
        query.setParameter("recipient", recipient);
        query.setParameter("playlist", playlist);
        logger.info("Number of rows affected = " + query.executeUpdate());
        session.close();
    }

}
