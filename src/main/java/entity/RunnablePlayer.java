package entity;

import engines.SongManager;
import org.apache.log4j.Logger;
import persistence.SongDAO;

import java.util.ArrayList;

/**
 * Starts and stops the player on a particular song
 * Created by peter on 3/23/2017.
 */
public class RunnablePlayer implements Runnable{
    private MP3Player mp3Player;
    private SongManager songManager;
    private final Logger logger = Logger.getLogger(this.getClass());
    private int id;

    public RunnablePlayer(int id) {
        this.id = id;
        songManager = new SongManager();
        mp3Player = new MP3Player();
    }

    public void run() {
        String songPath = songManager.getPathToCurrentSong();
        play(songPath);
    }

    private void play(String song) {
        mp3Player.play(song);
    }
}
