package engines;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import entity.Playlist;
import entity.FullPlayList;
import entity.Song;
import entity.Association;
import persistence.PlaylistDAO;
import persistence.SongDAO;
import persistence.AssociationDAO;
import engines.AssociationManager;
/**
 * Manage changes to playlists including effects on associations
 * Created by peter on 3/6/2017.
 */
public class PlaylistManager {

    final private PlaylistDAO pDAO = new PlaylistDAO();
    final private SongDAO songDAO = new SongDAO();
    final private AssociationDAO aDAO = new AssociationDAO();
    final private AssociationManager associationManager = new AssociationManager();
    final private Logger logger = Logger.getLogger(this.getClass());
    private Playlist playlist;
    private Association association;


    /**
     * Creates a playlist database entry and an association with the creating user
     * @param name The name of the playlist - a label that the user will recognize
     * @param user_id The system ID for the creating user
     * @return The system ID of the new association entry
     */
    public int add(String name, int user_id) {
        Playlist playlist = new Playlist(name);
        pDAO.add(playlist);
        int listID = playlist.getId();
        return associationManager.add("users", user_id, listID, "playlists", "owner");
    }

    /**
     * Gets the system ID of a playlist from the user ID and the playlist name
     * @param name The name of the playlist being sought
     * @param user_id The system ID of the user seeking the playlist
     * @return The system ID of the sought playlist or zero if it is not found
     */
    public int getID(String name, int user_id) {
        ArrayList<Integer> playlistAssociations = associationManager.getIDs(user_id, "users", "playlists");
        for (int index : playlistAssociations) {
            association = aDAO.get(index);
            int playlist_id = association.getRightTableKey();
            playlist = pDAO.get(playlist_id);
            if (name.equalsIgnoreCase(playlist.getName())) return playlist_id;
        }
        return 0;
    }

    /**
     * Returns the system IDs of all playlists associated with the user
     * @param user_id The system ID of the user
     * @return The list of IDs of the associated playlists
     */
    public ArrayList<Integer> getIDs(int user_id) {
        ArrayList<Integer> associations = associationManager.getIDs(user_id, "users", "playlists");
        ArrayList<Integer> playlistIDs = new ArrayList<>();
        for (int index : associations) {
            Association association = aDAO.get(index);
            if (user_id == association.getLeftTableKey()) {
                playlistIDs.add(association.getRightTableKey());
            }
        }
        return playlistIDs;
    }

    /**
     * Renames a playlist after checking that it is associated with the user as owner
     * @param user_id The system ID of the requester
     * @param playlist_id The system ID of the playlist
     * @param new_name The desired new name of the playlist
     * @return The playlist ID or zero if the request did not succeed
     */
    public int rename(int user_id, int playlist_id, String new_name) {
        int associationID = associationManager.getID("users", user_id, playlist_id, "playlists");
        Association association = aDAO.get(associationID);
        if (association == null) return 0;

        if (association.getRelationship().equalsIgnoreCase("owner")) {
            Playlist playlist = pDAO.get(playlist_id);
            playlist.setName(new_name);
            return pDAO.modify(playlist);
        }
        return 0;
    }

    /**
     * Removes the specified playlist from the associations table
     * @param listID The system ID of the playlist to remove
     */
    public void remove(int listID) {
        int associationID = associationManager.getID("users", "playlists", listID);
        aDAO.remove(associationID);
        pDAO.remove(listID);
    }

}
