package engines;

import entity.Association;
import java.util.List;
import java.util.ArrayList;
import entity.Message;
import entity.Playlist;
import entity.Song;
import entity.User;
import persistence.AssociationDAO;
import persistence.MessageDAO;
import persistence.PlaylistDAO;
import persistence.SongDAO;
import persistence.UserDAO;
/**
 * Manage associations between different types of entities
 * Created by peter on 3/6/2017.
 */
public class AssociationManager {
    final AssociationDAO associationDAO = new AssociationDAO();
    final MessageDAO messageDAO = new MessageDAO();
    final PlaylistDAO playlistDAO = new PlaylistDAO();
    final SongDAO songDAO = new SongDAO();
    final UserDAO userDAO = new UserDAO();

    /**
     * Gets single association for a pair of table keys
     * @param leftTableName
     * @param leftTableKey
     * @param rightTableKey
     * @param rightTableName
     * @return
     */
    public Association getAssociation(String leftTableName, int leftTableKey, int rightTableKey, String rightTableName)
    {
        List<Association> associations = associationDAO.getAll();
        for ( Association a : associations) {
            if (  (rightTableName.equalsIgnoreCase(a.getRightTableName())) &&
                    (leftTableName.equalsIgnoreCase(a.getLeftTableName())) &&
                    (leftTableKey==a.getLeftTableKey()) &&
                    (rightTableKey==a.getRightTableKey())) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<Integer> getIDs(int identifier, String leftTableName, String rightTableName) {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        List<Association> associations = associationDAO.getAll();
        for ( Association a : associations) {
            if (  (rightTableName.equalsIgnoreCase(a.getRightTableName())) &&
                    (leftTableName.equalsIgnoreCase(a.getLeftTableName())) &&
                    (identifier==a.getLeftTableKey())) {
                ids.add(a.getRightTableKey());
            }
        }
        return ids;
    }

}
