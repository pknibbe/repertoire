package engines;

import entity.RunnablePlayer;
import entity.Song;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;

/**
 * Starts and stops the player on a particular song
 * Created by peter on 3/23/2017.
 */
public class PlayerManager {
    private SongManager songManager;
    private final Logger logger = Logger.getLogger(this.getClass());
    private ExecutorService executor;
    private Future future;

    public PlayerManager() {
        songManager = new SongManager();
        executor = Executors.newFixedThreadPool(1);
    }

    /**
     * Creates and manages threads to play the specified songs
     * @param songs The songs to be played
     */
    public void start(ArrayList<Song> songs) {
        int currentSongID = songManager.getCurrentSongIndex();
        boolean reachedCurrent = false;

        ListIterator<Song> iterator = songs.listIterator();

            for (Song song : songs) {
            song = iterator.next();
            int songId = song.getId();
            if (songId == currentSongID) { // At current song
                reachedCurrent = true;
                logger.info("Reached current song with id = " + songId);
            }

            if (reachedCurrent) {
                songManager.setCurrentSongIndex(songId);
                // when the thread finishes, set the current song ID to that of the next entry in the array
                future = executor.submit(new RunnablePlayer(songId));
                Thread thread = new Thread();
                thread.start();  // Start a thread to run the RunnablePlayer
                try {
                    thread.join(); // wait for the thread to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        future.cancel(true);
    }
}
