package entity;

import org.junit.Test;

/**
 * Tests the player
 * Created by peter on 3/26/2017.
 */
public class AudioFilePlayerTest {

    @Test
    public void run() throws InterruptedException {
        AudioFilePlayer player = new AudioFilePlayer();
        player.play("C:/Users/peter/tomee/Data/MoreThanAFeeling.mp3");
    }
}
