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

    /**
     * Retrieves the location of the current song
     * @return The song location
     */
    public String getPathToCurrentSong() {
        if (currentSong == null) {
            logger.error("Current song is null and cannot be located");
            return null;
        }
        else {
            return getSongRoot() + currentSong.getLocation();
        }
    }


    /**
     * Retrieves the songs in a playlist
     * @param list_ID The system identifier of the playlist
     * @return The songs
     */
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

    /**
     * Retrieves the system identifiers of the songs in a playlist
     * @param list_ID The system identifier of the playlist
     * @return The song identifiers
     */
    public ArrayList<Integer> getSongIds(int list_ID) {
        ArrayList<Integer> songList = new ArrayList<>();
        List<Song> songs = songDAO.getAll();
        for (Song song : songs) {
            if (list_ID == song.getPlaylist_id()) {
                songList.add(song.getId());
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
