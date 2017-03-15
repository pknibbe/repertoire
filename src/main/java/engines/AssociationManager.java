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
     * Gets ID of a single association for a pair of table keys
     * @param leftTableName The name of the first table
     * @param leftTableKey The unique key to the first table
     * @param rightTableKey The unique key to the other table
     * @param rightTableName The name of the other table
     * @return The system ID for the association entry
     */
    public int getID(String leftTableName, int leftTableKey, int rightTableKey, String rightTableName)
    {
        List<Association> associations = associationDAO.getAll();
        for ( Association a : associations) {
            if (  (rightTableName.equalsIgnoreCase(a.getRightTableName())) &&
                    (leftTableName.equalsIgnoreCase(a.getLeftTableName())) &&
                    (leftTableKey==a.getLeftTableKey()) &&
                    (rightTableKey==a.getRightTableKey())) {
                return a.getId();
            }
        }
        return 0;
    }

    /**
     * Gets the association ID (or first found association ID) matching specified tables and right table key
     * @param leftTableName The name of the left Table
     * @param rightTableName The name of the right Table
     * @param rightTableKey The key for the right Table
     * @return The system ID of the matching association table entry
     */
    public int getID(String leftTableName, String rightTableName, int rightTableKey) {
        List<Association> associations = associationDAO.getAll();
        for ( Association a : associations) {
            if (  (rightTableName.equalsIgnoreCase(a.getRightTableName())) &&
                    (leftTableName.equalsIgnoreCase(a.getLeftTableName())) &&
                    (rightTableKey==a.getRightTableKey())) {
                return a.getId();
            }
        }
        return 0;
    }

    /**
     * Gets the association ID (or first found association ID) matching specified tables and left table key
     * @param leftTableKey The unique key to the first table
     * @param leftTableName The name of the left Table
     * @param rightTableName The name of the right Table
     * @return The system ID of the matching association table entry
     */
    public int getID(int leftTableKey, String leftTableName, String rightTableName) {
        List<Association> associations = associationDAO.getAll();
        for ( Association a : associations) {
            if (  (rightTableName.equalsIgnoreCase(a.getRightTableName())) &&
                    (leftTableName.equalsIgnoreCase(a.getLeftTableName())) &&
                    (leftTableKey==a.getLeftTableKey())) {
                return a.getId();
            }
        }
        return 0;
    }

    /**
     * Gets all associations for a left table key and other table name
     * @param identifier The unique key for the left table
     * @param leftTableName The name of the first table
     * @param rightTableName The name of the other table
     * @return All of the keys to the association table associated with the tables and key
     */
    public ArrayList<Integer> getIDs(int identifier, String leftTableName, String rightTableName) {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        List<Association> associations = associationDAO.getAll();
        for ( Association a : associations) {
            if (  (rightTableName.equalsIgnoreCase(a.getRightTableName())) &&
                    (leftTableName.equalsIgnoreCase(a.getLeftTableName())) &&
                    (identifier==a.getLeftTableKey())) {
                ids.add(a.getId());
            }
        }
        return ids;
    }

    /**
     * Gets all associations for a right table key and other table name
     * @param leftTableName The name of the first table
     * @param rightTableName The name of the other table
     * @param identifier The unique key for the right table
     * @return All of the keys to the association table associated with the tables and key
     */
    public ArrayList<Integer> getIDs(String leftTableName, String rightTableName, int identifier) {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        List<Association> associations = associationDAO.getAll();
        for ( Association a : associations) {
            if (  (rightTableName.equalsIgnoreCase(a.getRightTableName())) &&
                    (leftTableName.equalsIgnoreCase(a.getLeftTableName())) &&
                    (identifier==a.getRightTableKey())) {
                ids.add(a.getId());
            }
        }
        return ids;
    }

    public int add(String leftTableName, int leftTableKey, int rightTableKey, String rightTableName, String relationship) {
        Association association = new Association(leftTableName, leftTableKey, rightTableKey, rightTableName, relationship);
        return associationDAO.add(association);
        }

}
