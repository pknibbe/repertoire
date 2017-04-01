package entity;

import org.apache.log4j.Logger;
/**
 * Starts and stops the player on a particular song
 * Created by peter on 3/23/2017.
 */
@SuppressWarnings("CanBeFinal")
public class RunnablePlayer implements Runnable{
    private final Logger logger = Logger.getLogger(this.getClass());
    private AudioFilePlayer audioFilePlayer;
    private Player player;

    public RunnablePlayer(Player player) {
        this.player = player;
        audioFilePlayer = new AudioFilePlayer();
    }

    public void run() {
        logger.debug("In RunnablePlayer about to play " + player.getCurrentSongLocation());
        //mp3Player.play(player.getCurrentSongLocation());
        audioFilePlayer.play(player.getCurrentSongLocation());
        player.increment();
    }

}
