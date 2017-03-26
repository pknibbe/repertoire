package entity;

import java.util.ArrayList;

import engines.PlayManager;
import engines.SongManager;
import org.apache.log4j.Logger;

/**
 * Information about a playlist that is being played
 * Created by peter on 3/25/2017.
 */
public class Player {

    private final Logger logger = Logger.getLogger(this.getClass());
    private int playlistId;
    private int currentSongId;
    private ArrayList<Integer> songIds;
    private Thread pmThread;
    private String action;
    final public Integer lock = 0;

    /**
     * Populates the instance variables
     * @param playlistId The system ID of the playlist
     */
    public Player(int playlistId) {
        this.playlistId = playlistId;
        songIds = new SongManager().getSongIds(playlistId);
        logger.info("In constructor");
        currentSongId = songIds.get(0);
        pmThread = new Thread(new PlayManager(this));
    }

    /**
     * Tells playlist manager to start playback
     * It returns quickly so the user doesn't have to wait a long time before doing something else
     */
    public void start() {
        action = "START";
        logger.info("In start");
        signal();
    }

    /**
     * Tells the PlayerManager thread to stop playback and terminate
     */
    public void stop() {
        action = "TERMINATE";
        logger.info("In stop");
        signal();
    }

    /**
     * Tells the PlayerManager thread to stop the current song and resume playing with the next one
     */
    public void skip() {
        action = "STOP";
        logger.info("In skip");
        signal();
        logger.info("In skip after first signal");
        setCurrentSongId(getCurrentSongId() + 1);
        action = "START";
        signal();
    }

    /**
     * Tells the PlayerManager thread to stop the current song and resume playing with the previous one
     */
    public void previous() {
        action = "STOP";
        logger.info("In previous");
        signal();
        logger.info("In previous after first signal");
        setCurrentSongId(getCurrentSongId() - 1);
        action = "START";
        signal();
    }

    private void signal() { // Plan is to use this to communicate with the PlayerManager while it is in wait() mode;
        logger.info("In signal");
        synchronized (lock) {
            logger.info("In synchronized part of signal");
            lock.notify();
        }
    }


    public Integer getLock() {
        return lock;
    }

    public String getAction() {
        return action;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public int getCurrentSongId() {
        return currentSongId;
    }

    public void setCurrentSongId(int currentSongId) {
        this.currentSongId = currentSongId;
    }

    public ArrayList<Integer> getSongIds() {
        return songIds;
    }

}
