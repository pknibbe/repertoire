package engines;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import entity.Playlist;
import entity.FullPlayList;
import entity.Song;
import entity.Association;
import persistence.PlaylistDAO;
import persistence.SongDAO;
import persistence.AssociationDAO;
import engines.AssociationManager;
/**
 * Manage changes to playlists including effects on song and association entities
 * Created by peter on 3/6/2017.
 */
public class PlaylistAndSongManager {

    final private PlaylistDAO pDAO = new PlaylistDAO();
    final private SongDAO songDAO = new SongDAO();
    final private AssociationDAO aDAO = new AssociationDAO();
    final private AssociationManager manager = new AssociationManager();
    final private Logger logger = Logger.getLogger(this.getClass());

    public FullPlayList compilePlayList(int identifier) {
        Playlist playlist = pDAO.get(identifier);
        // get the ids of all songs associated with this playlist
        ArrayList<Integer> songIDs = manager.getIDs(identifier, "playlists", "songs");
        ArrayList<Song> songList = new ArrayList<Song>();
        for (int index : songIDs) {
            songList.add(songDAO.get(index));
        }
        return new FullPlayList(playlist, songList);
    }

}
