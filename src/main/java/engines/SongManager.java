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
    private String repository;

    public SongManager() {
        PropertyManager propertyManager = new PropertyManager("/repertoire.properties");
        setRepository(propertyManager.getProperty("musicDir"));
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
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

    /**
     * Adds the song to the playlist if it is not already there, also adding it to the songs table if necessary
     * @param location Where the song should be
     * @param playlist_id The system ID of the playlist
     * @return The system ID of the song
     */
    public int add(String location, int playlist_id) {
        List<Song> songs = songDAO.getAll();
        for (Song song : songs) {
            if (location.equalsIgnoreCase(song.getLocation()) &&
                    playlist_id == song.getPlaylist_id()) return song.getId();
        }
        Song song = new Song(location, playlist_id );
        return songDAO.add(song);
    }

    /**
     * Returns whether or not the specified song is in the database
     * @param location The file sought
     * @return whether or not the specified song is in the database
     */
    public boolean exists(String location) {
        boolean found = false;
        List<Song> songs = songDAO.getAll();
        for (Song song : songs) {
            if (song.getLocation().equalsIgnoreCase(location)) found = true;
        }
        return found;
    }

    /**
     * Returns the system ID of the song at a specified file
     * @param location The specification of the file
     * @return zero or the system ID
     */
    public ArrayList<Integer> getIDs(String location) {
        List<Song> songs = songDAO.getAll();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Song song : songs) {
            if (song.getLocation().equalsIgnoreCase(location)) ids.add(song.getId());
        }
        return ids;
    }

}
