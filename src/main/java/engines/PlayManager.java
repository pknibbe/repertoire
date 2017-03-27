package engines;

import entity.RunnablePlayer;
import entity.Player;
import org.apache.log4j.Logger;

/**
 * Starts and stops the player on a particular song
 * Created by peter on 3/23/2017.
 */
public class PlayManager implements Runnable {
     final Logger logger = Logger.getLogger(this.getClass());
     Player player;
     Thread thread;
     RunnablePlayer runnablePlayer;

    public PlayManager(Player player) {
        this.player = player;
        logger.info("In constructor");
        runnablePlayer = new RunnablePlayer(player);
    }

    public void run() {
        logger.info("In run");
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
                logger.info("Received message to terminate");
                stop();
                break;
            }
        }
        logger.info("Outside the run loop. About to exit");
    }

    /**
     * Launches a thread to play the current song in the player object
     */
    private void start() {
        logger.info("In start method");
        if (thread == null) {
            logger.info("creating a new runnable thread");
            thread = new Thread(runnablePlayer);
        } else if (thread.isAlive()) return;
        thread = new Thread(runnablePlayer);
        logger.info("Calling start method on the thread " + thread);
        thread.start();
        logger.info("Launched runnable player");
    }

    private void stop() {
        //thread.stop();
        if (thread == null) return;
        if (thread.isAlive()) {
            logger.info("Stopping runnable player");
            thread.interrupt();
            thread.stop();
        }
    }
}
