package engines;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
    private final Logger logger = Logger.getLogger(this.getClass());
    private Song currentSong;
    private int currentSongIndex;
    private String songRoot;

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public String getSongRoot() {
        return songRoot;
    }

    public void setSongRoot(String songRoot) {
        this.songRoot = songRoot;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public String getPathToCurrentSong() {
        if (currentSong == null) {
            logger.error("Current song is null and cannot be located");
            return null;
        }
        else {
            return getSongRoot() + currentSong.getLocation();
        }
    }


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
