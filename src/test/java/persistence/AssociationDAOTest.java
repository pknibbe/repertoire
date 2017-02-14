package persistence;

import entity.Association;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by peter on 2/14/2017.
 */
public class AssociationDAOTest {

    AssociationDAO dao;
    entity.Association association;
    int numberOfAssociations;
    List<Association> associationList;
    final Logger logger = Logger.getLogger(this.getClass());

        @Before
        public void setup() throws Exception {
            dao = new AssociationDAO();
            associationList = dao.getAll();
            logger.info("In @Before, associationList has " + associationList.size() + "entries");
            justAdd(); // make sure table is not empty for purpose of test
            associationList = dao.getAll();
            logger.info("After justAdd, associationList has " + associationList.size() + "entries");
            numberOfAssociations = associationList.size();
        }

        @Test
        public void testGetAll() throws Exception {
            boolean found = false;

            for (entity.Association association : associationList) {
                String thisName = association.getRightTableName();
                if (thisName.equalsIgnoreCase("messages")) found = true;
            }
            assertTrue("The expected table name was not found: ", found);
        }

        @Test
        public void testGet() throws Exception {
            association = associationList.get(associationList.size() - 1); // retrieve most recent addition to table
            int id = association.getId(); // get the id of the most recent addition
            association = dao.get(id); // get the association by id
            assertEquals("Table names don't match", "messages", association.getRightTableName());
        }

        @Test
        public void testAdd() throws Exception {
            justAdd();
            associationList = dao.getAll();
            logger.info("association list has " + associationList.size() + " entries");
            assertEquals("Add did not work: ", numberOfAssociations + 1, associationList.size());
        }

        @Test
        public void testModifyAssociationName() throws Exception {
            association = associationList.get(associationList.size() - 1); // retrieve most recent addition to table
            int id = association.getId();
            association.setRightTableName("credentials");
            logger.info(association.toString());
            logger.info("Updated association ID = " + dao.modify(association));
            //dao.modify(association);
            association = dao.get(id);
            assertEquals("association table name not modified", "credentials", association.getRightTableName());
            associationList = dao.getAll();
            assertEquals("Modify added an entry!", numberOfAssociations, associationList.size());
        }

        private void justAdd() {
            association = new entity.Association("users", 1, 12, "messages");
            logger.info("New association is " + association.toString());
            dao.add(association);
        }

        @Test
        public void testRemove() throws Exception {
            association = associationList.get(associationList.size() - 1); // retrieve most recent addition to table
            int id = association.getId();
            dao.remove(id);
            associationList = dao.getAll();
            assertEquals("remove did not work: ", numberOfAssociations - 1, associationList.size());
        }


        @After
        public void cleanup() throws Exception {
            associationList = dao.getAll();

            for (entity.Association association : associationList) {
                String thisName = association.getRightTableName();
                if (thisName.equalsIgnoreCase("messages")) {
                    dao.remove(association.getId());
                } else if (thisName.equalsIgnoreCase("credentials")) {
                    dao.remove(association.getId());
                }
            }
        }
    }
