package entity;

import entity.Playlist;
import java.util.ArrayList;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
@ToString
/**
 * Compound convenience class
 * Created by peter on 3/6/2017.
 */
public class FullPlayList {
    private Playlist playlist;
    private ArrayList<Song> songs;

    public FullPlayList(Playlist playlist, ArrayList<Song> songs) {
        this.playlist = playlist;
        this.songs = songs;
    }
}
