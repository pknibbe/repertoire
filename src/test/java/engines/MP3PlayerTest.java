package engines;

import engines.MP3Player;
import org.junit.Test;/**
 * Tests the MP3 player from javazoom
 * Created by peter on 3/21/2017.
 */
public class MP3PlayerTest {
    final private MP3Player mp3Player = new MP3Player();

    @Test
    public void play() {
        mp3Player.play("C:/Users/peter/tomee/Data/MoreThanAFeeling.mp3");
    }
}
