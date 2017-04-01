package engines;

import java.util.ArrayList;
import java.util.List;
//import org.apache.log4j.Logger;
import entity.Playlist;
import persistence.PlaylistDAO;
import persistence.SongDAO;


/**
 * Manage changes to playlists
 * Created by peter on 3/6/2017.
 */
public class PlaylistManager {

    final private PlaylistDAO pDAO = new PlaylistDAO();
    //final private Logger logger = Logger.getLogger(this.getClass());
    private List<Playlist> PlaylistList;
    final private SongManager songManager = new SongManager();
    final private SongDAO songDAO = new SongDAO();
    final private SharedManager sharedManager = new SharedManager();

    /**
     * Gets the system ID of a playlist from the user ID and the playlist name
     *
     * @param user_id The system ID of the user seeking the playlist
     * @param name    The name of the playlist being sought
     * @return The system ID of the sought playlist or zero if it is not found
     */
     private int getID(int user_id, String name) {
        PlaylistList = pDAO.getAll();
        for (Playlist playlist : PlaylistList) {
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
    public ArrayList<Integer> getIDs(int user_id) {
        PlaylistList = pDAO.getAll();
        ArrayList<Integer> playlistIDs = new ArrayList<>();
        for (Playlist playlist : PlaylistList) { // playlists owned by the user
            if (user_id == playlist.getOwner_id()) {
                playlistIDs.add(playlist.getId());
            }
        }
        ArrayList<Integer> sharedIDs = sharedManager.getAll(user_id);
        // playlists shared with the user
        playlistIDs.addAll(sharedIDs);
        return playlistIDs;
    }

    /**
     * Renames a playlist after checking that it is associated with the user as owner
     *
     * @param user_id     The system ID of the requester
     * @param playlist_id The system ID of the playlist
     * @param new_name    The desired new name of the playlist
     * @return The playlist ID or zero if the request did not succeed
     */
     int rename(int user_id, int playlist_id, String new_name) {

        Playlist playlist = pDAO.get(playlist_id);
        if (user_id == playlist.getOwner_id()) {
            playlist.setName(new_name);
            return pDAO.modify(playlist);
        }
        return 0;
    }

    public int add(int user_id, String name) {
        int id = this.getID(user_id, name);
        if (id == 0) {
            Playlist playlist = new Playlist(name, user_id);
            id = pDAO.add(playlist);
        }
        return id;
    }

    /**
     * Returns whether or not a particular song is already associated with a particular playlist
     *
     * @param location    The place where the song should be
     * @param playlist_id The system ID of the playlist
     * @return whether or not it is there
     */
    public boolean alreadyThere(String location, int playlist_id) {
        boolean found = false;
        ArrayList<Integer> song_ids = songManager.getIDs(location);
        for (Integer index : song_ids) {
            if (songDAO.get(index).getPlaylist_id() == playlist_id) found = true;
        }
        return found;
    }


}
