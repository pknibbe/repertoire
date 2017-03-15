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

    public FullPlayList compilePlayList(int identifier) {
        Playlist playlist = pDAO.get(identifier);
        // get the ids of all songs associated with this playlist
        ArrayList<Integer> songIDs = associationManager.getIDs(identifier, "playlists", "songs");
        ArrayList<Song> songList = new ArrayList<Song>();
        for (int index : songIDs) {
            songList.add(songDAO.get(index));
        }
        return new FullPlayList(playlist, songList);
    }

    /**
     * Creates a playlist database entry and an association with the creating user
     * @param name The name of the playlist - a label that the user will recognize
     * @param user_id The system ID for the creating user
     * @return The system ID of the new playlist
     */
    public int add(String name, int user_id) {
        Playlist playlist = new Playlist(name);
        int listID = playlist.getId();
        return associationManager.add("users", user_id, listID, "playlists", "owner");
    }

    public void remove(int listID) {
        int associationID = associationManager.getID("users", "playlists", listID);
        aDAO.remove(associationID);
        pDAO.remove(listID);
    }

}
