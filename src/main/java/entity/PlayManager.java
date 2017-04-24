package entity;

import entity.RunnablePlayer;
import entity.Player;
import org.apache.log4j.Logger;

/**
 * Starts and stops the player on a particular song
 * Created by peter on 3/23/2017.
 */
@SuppressWarnings({"deprecation", "CanBeFinal"})
public class PlayManager implements Runnable {
    final private Logger logger = Logger.getLogger(this.getClass());
    private Player player;
    private Thread thread;
    private RunnablePlayer runnablePlayer;

    public PlayManager(Player player) {
        this.player = player;
        logger.debug("In constructor");
        runnablePlayer = new RunnablePlayer(player);
    }

    public void run() {
        logger.debug("In run");
        while(true) {
            try {
                Thread.sleep(500); // give Player time to set the action
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String start = "START";
            String stop = "STOP";

            if (start.equalsIgnoreCase(player.getAction())) {
                start();
            } else if (stop.equalsIgnoreCase(player.getAction())) {
                stop();

            } else {
                logger.debug("Received message to terminate");
                stop();
                break;
            }
        }
        logger.debug("Outside the run loop. About to exit");
    }

    /**
     * Launches a thread to play the current song in the player object
     */
    private void start() {
        //logger.debug("In start method");
        if (thread == null) {
            logger.debug("creating a new runnable thread");
            thread = new Thread(runnablePlayer);
        } else if (thread.isAlive()) return;
        thread = new Thread(runnablePlayer);
        logger.debug("Calling start method on the thread " + thread);
        thread.start();
        logger.debug("Launched runnable player");
    }

    private void stop() {
        //thread.stop();
        if (thread == null) return;
        if (thread.isAlive()) {
            logger.debug("Stopping runnable player");
            thread.interrupt();
            thread.stop();
        }
    }
}
