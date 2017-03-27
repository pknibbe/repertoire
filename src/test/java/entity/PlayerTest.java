package entity;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Tests the player
 * Created by peter on 3/26/2017.
 */
public class PlayerTest {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void run() throws InterruptedException {
        Player player = new Player(3);
        player.start();
        logger.info("Called player.start");
        Thread.sleep(22000);
        player.skip();
        logger.info("Called player.skip");
        Thread.sleep(22000);
        player.skip();
        logger.info("Called player.skip");
        Thread.sleep(22000);
        player.previous();
        logger.info("Called player.previous");
        Thread.sleep(22000);
        player.stop();
        logger.info("Called player.stop");
    }
}
