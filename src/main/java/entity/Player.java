package entity;

import java.util.ArrayList;
import java.lang.Thread;
import engines.PlayManager;
import engines.SongManager;
import org.apache.log4j.Logger;

/**
 * Information about a playlist that is being played
 * Created by peter on 3/25/2017.
 */
@SuppressWarnings("CanBeFinal")
public class Player {

    private final Logger logger = Logger.getLogger(this.getClass());
    private int currentSongIndex;
    private ArrayList<Integer> songIds;
    private String action;
    private SongManager songManager;

    /**
     * Populates the instance variables
     * @param playlistId The system ID of the playlist
     */
    public Player(int playlistId) {
        songManager = new SongManager();
        songIds = songManager.getSongIds(playlistId);
        logger.debug("In constructor");
        currentSongIndex = 0;
        Thread pmThread = new Thread(new PlayManager(this));
        pmThread.start();
    }

    /**
     * Tells playlist manager to start playback
     * It returns quickly so the user doesn't have to wait a long time before doing something else
     */
    public void start() {
        action = "START";
        logger.debug("In start");
    }

    /**
     * Tells the PlayerManager thread to stop playback and terminate
     */
    public void stop() {
        action = "TERMINATE";
        logger.debug("In stop");
    }

    /**
     * Tells the PlayerManager thread to stop the current song and resume playing with the next one
     */
    public void skip() {
        action = "STOP";
        logger.debug("In skip");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("In skip after STOP");
        increment();

    }

    public void increment() {
        if (currentSongIndex < (songIds.size() - 1)) { //Possible to skip forward
            currentSongIndex++;
            action = "START";
        } else {
            stop();
        }
    }

    /**
     * Tells the PlayerManager thread to stop the current song and resume playing with the previous one
     */
    public void previous() {
        action = "STOP";
        logger.debug("In previous");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("In previous after first signal");
        if (currentSongIndex < (songIds.size() - 1)) { //Possible to skip forward
            currentSongIndex++;
            action = "START";
        }
        action = "START";
    }

    public String getCurrentSongLocation() {
        String relativePath = songManager.getLocation(songIds.get(currentSongIndex));
        return songManager.getRepository() + relativePath;
    }

    public String getAction() {
        return action;
    }

}
