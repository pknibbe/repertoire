package engines;

import java.util.ArrayList;
import java.util.List;
import persistence.SongDAO;
import entity.Song;
import entity.Association;
import persistence.AssociationDAO;
import engines.AssociationManager;
/**
 * Performs very simple file management
 * Created by peter on 3/6/2017.
 */
public class SongManager {
    private final SongDAO songDAO = new SongDAO();

    public ArrayList<Song> getSongs(int list_ID) {
        ArrayList<Song> songList = new ArrayList<>();
        List<Song> songs = songDAO.getAll();
        for (Song song : songs) {
            if (list_ID == song.getPlaylist_id()) {
                songList.add(song);
            }
        }
        return songList;
    }

/*    public void removeSong (int songID, int userID) {
        songDAO.remove(songID);
        AssociationManager manager = new AssociationManager();
        Association association = manager.getAssociation("users", userID, songID, "songs");
        associationDAO.remove(association.getId());
    }

    public int updateSong (Song song) {
        return songDAO.modify(song);
    }
*/
}
