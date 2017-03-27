package engines;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import persistence.SongDAO;
import entity.Song;
/**
 * Performs very simple entity management on songs
 * Created by peter on 3/6/2017.
 */
public class SongManager {
    private final SongDAO songDAO = new SongDAO();
    private final Logger logger = Logger.getLogger(this.getClass());

    public SongManager() {
    }

    /**
     * Retrieves the location of the current song
     * @return The song location
     */
    public String getPathToSong(int songID) {
        Song currentSong = songDAO.get(songID);
        if (currentSong == null) {
            logger.error("Current song is null and cannot be located");
            return null;
        }
        else {
            return currentSong.getLocation();
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
}
