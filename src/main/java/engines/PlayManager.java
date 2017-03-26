package engines;

import entity.RunnablePlayer;
import entity.Player;
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
public class PlayManager implements Runnable {
     final Logger logger = Logger.getLogger(this.getClass());
     Player player;

    public PlayManager(Player player) {
        this.player = player;
        logger.info("In constructor");
    }

    public void run() {
        logger.info("In run");
        while(true) {
            synchronized (player.getLock()) {
                logger.info("In synchronized section");
                try {
                    player.getLock().wait(1000);
                    logger.info("After wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String start = "START";
            String stop = "STOP";
            //String terminate = "TERMINATE";
            logger.info("Action is " + player.getAction());

            if (start.equalsIgnoreCase(player.getAction())) {
                logger.info("Received message to start");
            } else if (stop.equalsIgnoreCase(player.getAction())) {
                logger.info("Received message to stop");
            } else {
                logger.info("Received message to terminate");
                break;
            }
        }
    }
        /**
         * Creates and manages threads to play the specified songs
         * @param songs The songs to be played
         *
    public void start(Player player) {
        logger.info("In start");
        int currentSongID = player.getCurrentSongId();
        boolean reachedCurrent = false;

        ListIterator<Song> iterator = songs.listIterator();

        for (Song song : songs) {
            logger.info("In song list loop with currentSongID = " + currentSongID);
            song = iterator.next();
            int songId = song.getId();
            logger.info("Song id is " + songId);
            if (songId == currentSongID) { // At current song
                reachedCurrent = true;
                logger.info("Reached current song with id = " + songId);
            }

            if (reachedCurrent) {
                player.setCurrentSongId(songId);
                logger.info("Just set the current song index to " + songId);
                future = executor.submit(new RunnablePlayer(songId));
                logger.info("Just submitted new RunnablePlayer to the executor");
            }
        }
        logger.info("Outside the loop over songs - about to shutdown the executor");
        executor.shutdown();
        try {
            logger.info("About to await termination of the executor");
            executor.awaitTermination(2, TimeUnit.HOURS);
            logger.info("Done awaiting the termination");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } */
}
