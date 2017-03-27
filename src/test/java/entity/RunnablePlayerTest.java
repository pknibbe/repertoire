package entity;

import org.junit.Test;

/**
 * Tests the player
 * Created by peter on 3/26/2017.
 */
public class RunnablePlayerTest {

    @Test
    public void run() throws InterruptedException {
        Player player = new Player(3);
        RunnablePlayer runnablePlayer = new RunnablePlayer(player);
        Thread thread = new Thread(runnablePlayer);
        thread.start();
        Thread.sleep(22000);
        thread.interrupt();
    }
}
