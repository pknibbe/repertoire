package engines;

import java.util.ArrayList;
import java.util.List;
import persistence.SongDAO;
import entity.Song;
/**
 * Performs very simple entity management on songs
 * Created by peter on 3/6/2017.
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class SongManager {
    private static final SongDAO songDAO = new SongDAO();
    private final static PropertyManager propertyManager = new PropertyManager();
    private static final String repository = System.getenv(propertyManager.getProperty("home")) +
                                             propertyManager.getProperty("musicDir");

    /**
     * Adds the song to the playlist if it is not already there, also adding it to the songs table if necessary
     * @param location Where the song should be
     * @param playlist_id The system ID of the playlist
     */
    public static int add(String location, int playlist_id) {
        List<Song> songs = songDAO.getAll();
        for (Song song : songs) {
            if (location.equalsIgnoreCase(song.getLocation()) &&
                    playlist_id == song.getPlaylist_id()) return song.getId();
        }
        Song song = new Song(location, playlist_id );
        return songDAO.add(song);
    }

    public static String getRepository() {
        return repository;
    }

    /**
     * Retrieves the location of the current song
     * @return The song location
     */
    public static String getLocation(int songID) {
        Song currentSong = songDAO.get(songID);
        if (currentSong == null) {
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
    public static ArrayList<Song> getAll(int list_ID) {
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
    public static ArrayList<Integer> getSongIds(int list_ID) {
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
     * Returns whether or not the specified song is in the database
     * @param location The file sought
     * @return whether or not the specified song is in the database
     */
    public static boolean exists(String location) {
        boolean found = false;
        List<Song> songs = songDAO.getAll();
        for (Song song : songs) {
            if (song.getLocation().equalsIgnoreCase(location)) found = true;
        }
        return found;
    }

    /**
     * Returns the system IDs of the song at a specified file
     * @param location The specification of the file
     * @return null or the system IDs
     */
     static ArrayList<Integer> getIDs(String location) {
        List<Song> songs = songDAO.getAll();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Song song : songs) {
            if (song.getLocation().equalsIgnoreCase(location)) ids.add(song.getId());
        }
        return ids;
    }

    /**
     * Retrieves the playlist id from a song record
     * @param songID The record ID
     * @return the ID of the associated playlist
     */
     static int getPlaylistID(int songID) {
         Song song = songDAO.get(songID);
         if (song == null) {
             return 0;
         } else return song.getPlaylist_id();
    }

    /**
     * Removes the specified song record
     * @param id The system ID of the song
     */
    public static void remove(int id) {
         songDAO.remove(id);
    }

}
