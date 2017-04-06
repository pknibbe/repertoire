package engines;

import entity.Shared;
import persistence.SharedDAO;

import java.util.List;
import java.util.ArrayList;

/**
 * Manage access to Shared records
 * Created by peter on 3/22/2017.
 */
public class SharedManager {
    final static private SharedDAO dao = new SharedDAO();

    /**
     * Adds the playlist sharing record to the database
     * @param playlist_id The system ID of the playlist
     * @param user_id The user to be granted access
     * @return zero or the system ID of the sharing record
     *
     * Checks to see if playlist is already shared with the user to avoid adding a duplicate record
     */
    public static int share(int playlist_id, int user_id) {
        if (find(playlist_id, user_id) > 0) return find(playlist_id, user_id); // record is already in the database
        Shared shared = new Shared(playlist_id, user_id);
        return dao.add(shared);
    }

    /**
     * Looks up the system ID of a Sharing record by playlist ID and user ID
     * @param playlist_id The system ID of the playlist
     * @param user_id The system ID of the user with whom the playlist is shared
     * @return zero or the system ID of the sharing record
     */
    private static int find(int playlist_id, int user_id) {
        int id = 0;
        List<Shared> all = dao.getAll();
        for (Shared shared : all) {
            if ((shared.getPlaylist_id() == playlist_id) &&
                    (shared.getShared_with() == user_id)){
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
    public static boolean isShared(int playlist_id) {
        boolean isIt = false;
        List<Shared> all = dao.getAll();
        for (Shared shared : all) {
            if (shared.getPlaylist_id() == playlist_id) {
                isIt = true;
            }
        }
        return isIt;
    }

    /**
     * Retrieves the sharing records
     * @return The sharing records
     */
    public static List<Shared> getAll() {
        return dao.getAll();
    }


    /**
     * Retrieves the system IDS of playlists shared with the user
     * @param user_id The system ID of the user
     * @return The playlist IDs
     */
    public static ArrayList<Integer> getAll(int user_id) {
        ArrayList<Integer> list_IDs = new ArrayList<>();
        List<Shared> shared = dao.getAll();
        for (Shared playlist : shared) {
            if (playlist.getShared_with() == user_id) list_IDs.add(playlist.getId());
        }
        return list_IDs;
    }

    /**
     * Returns the list of all non-owners sharing a playlist
     * @param playlist_id The system ID of the playlist
     * @return the list of user IDs
     */
    public static ArrayList<Integer> sharing(int playlist_id) {
        ArrayList<Integer> userIDs = new ArrayList<>();
        for (Shared list : dao.getAll()) {
            if (list.getPlaylist_id() == playlist_id) {
                userIDs.add(list.getShared_with());
            }
        }
        return userIDs;
    }

    /**
     * Returns the list of all non-owners NOT sharing a playlist
     * @param playlist_id The system ID of the playlist
     * @return the list of user IDs
     */
    public static ArrayList<Integer> notSharing(int playlist_id) {
        ArrayList<Integer> userIDs = UserManager.getUserIds();

        for (Shared record : dao.getAll()) {
            if (record.getPlaylist_id() == playlist_id) {
                if (userIDs != null) {
                    int shared_with = record.getShared_with();
                    userIDs.remove((Integer) record.getShared_with());
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
    public static void remove(int userID, int playlist_id) {
        int id =  find(playlist_id, userID);
        dao.remove(id);
    }

    /**
     * Removes the specified sharing record
     * @param shared_id The system ID of the record
     */
    public static void remove(int shared_id) {
        dao.remove(shared_id);
    }
}
