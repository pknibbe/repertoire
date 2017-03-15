package engines;

import entity.Association;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.AssociationDAO;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods of the UserManager class
 * Created by peter on 2/16/2017.
 */
public class AssociationManagerTest {

    private final AssociationManager associationManager = new AssociationManager();
    private final AssociationDAO dao = new AssociationDAO();
    private final Logger logger = Logger.getLogger(this.getClass());
    private List<Association> originalAssociationList;
    private ArrayList<Integer> bogusList = new ArrayList<>();
    private Association association;
    private Integer target;

    /**
     * Creates some bogus association table entries to manipulate during testing
     * @throws Exception general exception
     */
    @Before
    public void setup() throws Exception {

        originalAssociationList = dao.getAll();
        logger.info("In @Before, originalAssociationList has " + originalAssociationList.size() + "entries");
        association = new Association("LeftTable", 27, 16, "RightTable", "equals");
        bogusList.add(dao.add(association));
        association.setLeftTableKey(28);
        bogusList.add(dao.add(association));
        association.setRightTableKey(17);
        bogusList.add(dao.add(association));
        target=bogusList.get(bogusList.size() - 1);
    }

    @Test
    public void testAdd() throws Exception {
        logger.info("*** Start of testAdd ***");
        for (int index : bogusList) {
            logger.info("bogus list includes entry # " + index);
            association = dao.get(index);
            String associationString = association.getLeftTableName() + " ";
            associationString += association.getLeftTableKey() + " ";
            associationString += association.getRightTableKey() + " ";
            associationString += association.getRightTableName() + " ";
            associationString += association.getRelationship() + "";
            logger.info("Corresponding association is " + associationString);
        }
        assertEquals("setup did not add expected # : ", 3, bogusList.size() );
        logger.info("*** End of testAdd ***");
    }

    @Test
    public void TestGetWithTwoKeys() throws Exception {
        logger.info("*** Start of TestGetWithTwoKeys ***");

        assertEquals("Didn't get expected entry", (long) target, (long) associationManager.getID("LeftTable", 28, 17, "RightTable"));

        logger.info("*** End of TestGetWithTwoKeys ***");
    }

    @Test
    public void TestGetWithLeftKey() throws Exception {
        logger.info("*** Start of TestGetWithLeftKey ***");

        int associationID = associationManager.getID(27, "LeftTable","RightTable");
        association = dao.get(associationID);
        assertEquals("Keys don't match: ", 16, association.getRightTableKey());
        logger.info("*** End of TestGetWithLeftKey ***");
    }

    @Test
    public void TestGetWithRightKey() throws Exception {
        logger.info("*** Start of TestGetWithRightKey ***");

        int associationID = associationManager.getID("LeftTable","RightTable", 17);
        association = dao.get(associationID);
        assertEquals("Keys don't match: ", 28, association.getLeftTableKey());

        logger.info("*** End of TestGetWithRightKey ***");
    }

    @Test
    public void TestGetWithNoKey() throws Exception {
        logger.info("*** Start of TestGetWithNoKey ***");

        ArrayList<Integer> testList = associationManager.getIDs(28, "LeftTable", "RightTable");
        assertEquals("List size is wrong: ", 2, testList.size());

        logger.info("*** End of TestGetWithNoKey ***");
    }


    @After
    public void cleanup() throws Exception {

        for (int index : bogusList) {
            dao.remove(index);
        }
        List<Association> finalAssociationList = dao.getAll();

        assertEquals("Didn't return associations table to original state: ", finalAssociationList.size(), originalAssociationList.size());
    }
}
