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
     * @param leftTableName The name of the first table
     * @param leftTableKey The unique key to the first table
     * @param rightTableKey The unique key to the other table
     * @param rightTableName The name of the other table
     * @return The entire Association entry
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

    /**
     * Gets all associations for a single table key and other table name
     * @param identifier The unique key for the left table
     * @param leftTableName The name of the first table
     * @param rightTableName The name of the other table
     * @return All of the keys to the second table associated with the first table and key
     */
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
