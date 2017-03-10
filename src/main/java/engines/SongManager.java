package engines;

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
    final SongDAO songDAO = new SongDAO();
    final AssociationDAO associationDAO = new AssociationDAO();

    public Song getSong (int songID){
        return songDAO.get(songID);
    }

    public int addSong (Song song, int userID) {
        int songID = songDAO.add(song);
        Association association = new Association("users", userID, songID, "songs", "accessor");
        associationDAO.add(association);
        return songID;
    }

    public void removeSong (int songID, int userID) {
        songDAO.remove(songID);
        AssociationManager manager = new AssociationManager();
        Association association = manager.getAssociation("users", userID, songID, "songs");
        associationDAO.remove(association.getId());
    }

    public int updateSong (Song song) {
        return songDAO.modify(song);
    }
}
