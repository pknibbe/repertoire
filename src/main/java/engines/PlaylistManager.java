package engines;

import java.util.ArrayList;
import java.util.List;
import entity.Playlist;
import persistence.PlaylistDAO;


/**
 * Manage changes to playlists
 * Created by peter on 3/6/2017.
 */
public class PlaylistManager {

    final static private PlaylistDAO pDAO = new PlaylistDAO();

    /**
     * Creates a new playlist record in the database
     * @param user_id The system ID of the owning user
     * @param name The name of the new playlist
     * @return The system ID of the new playlist
     */
    public static int add(int user_id, String name) {
        int id = getID(user_id, name);
        if (id == 0) {
            Playlist playlist = new Playlist(name, user_id);
            id = pDAO.add(playlist);
        }
        return id;
    }

    public static void play(int listID) {

    }

    public static void stop() {}

    /**
     * Retrieves the specified playlist record from the database
     * @param playlist_id The system ID of the playlist record
     * @return The playlist record
     */
    public static Playlist get(int playlist_id) {
        return pDAO.get(playlist_id);
    }


    /**
     * Gets the system ID of a playlist from the user ID and the playlist name
     *
     * @param user_id The system ID of the user seeking the playlist
     * @param name    The name of the playlist being sought
     * @return The system ID of the sought playlist or zero if it is not found
     */
    static int getID(int user_id, String name) {
        List<Playlist> playlists = pDAO.getAll();
        for (Playlist playlist : playlists) {
            if (user_id != playlist.getOwner_id()) {
                continue;
            }
            if (name.equalsIgnoreCase(playlist.getName())) return playlist.getId();
        }
        return 0;
    }

    /**
     * Returns the system IDs of all playlists associated with the user
     *
     * @param user_id The system ID of the user
     * @return The list of IDs of the associated playlists
     */
    public static ArrayList<Integer> getIDs(int user_id) {
        List<Playlist> playlists = pDAO.getAll();
        ArrayList<Integer> playlistIDs = new ArrayList<>();
        for (Playlist playlist : playlists) { // playlists owned by the user
            if (user_id == playlist.getOwner_id()) {
                playlistIDs.add(playlist.getId());
            }
        }
        ArrayList<Integer> sharedIDs = SharedManager.getAll(user_id);
        // playlists shared with the user
        playlistIDs.addAll(sharedIDs);
        return playlistIDs;
    }

    /**
     * Returns whether or not a particular song is already associated with a particular playlist
     *
     * @param location    The place where the song should be
     * @param playlist_id The system ID of the playlist
     * @return whether or not it is there
     */
    public static boolean alreadyThere(String location, int playlist_id) {
        boolean found = false;
        ArrayList<Integer> song_ids = SongManager.getIDs(location);
        for (Integer index : song_ids) {
            if (SongManager.getPlaylistID(index) == playlist_id) {
                found = true;
            }
        }
        return found;
    }


    /**
     * Renames a playlist after checking that it is associated with the user as owner
     *
     * @param user_id     The system ID of the requester
     * @param playlist_id The system ID of the playlist
     * @param new_name    The desired new name of the playlist
     * @return The playlist ID or zero if the request did not succeed
     */
    static int rename(int user_id, int playlist_id, String new_name) {

        Playlist playlist = pDAO.get(playlist_id);
        if (user_id == playlist.getOwner_id()) {
            playlist.setName(new_name);
            return pDAO.modify(playlist);
        }
        return 0;
    }

    /**
     * Removes a playlist after checking that it is not shared
     *
     * @param playlist_id The system ID of the playlist
     * @return The playlist ID or zero if the request did not succeed
     */
    public static int remove(int playlist_id) {

        if (SharedManager.isShared(playlist_id)) {
            return 0;
        }
        return pDAO.remove(playlist_id);
    }
}
