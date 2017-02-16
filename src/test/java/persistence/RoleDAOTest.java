package persistence;

import entity.Role;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by peter on 2/15/2017.
 */
public class RoleDAOTest {


    RoleDAO dao;
        entity.Role role;
        int numberOfRoles;
        List<Role> roleList;
        final Logger logger = Logger.getLogger(this.getClass());

        @Before
        public void setup() throws Exception {
            dao = new RoleDAO();
            roleList = dao.getAll();
            logger.info("In @Before, roleList has " + roleList.size() + "entries");
            justAdd(); // make sure table is not empty for purpose of test
            roleList = dao.getAll();
            logger.info("After justAdd, roleList has " + roleList.size() + "entries");
            numberOfRoles = roleList.size();
        }

        @Test
        public void testGetAll() throws Exception {
            boolean found = false;

            for (entity.Role role : roleList) {
                String thisName = role.getUsername();
                if (thisName.equalsIgnoreCase("Donald")) found = true;
            }
            assertTrue("The expected table name was not found: ", found);
        }

        @Test
        public void testGet() throws Exception {
            role = roleList.get(roleList.size() - 1); // retrieve most recent addition to table
            int id = role.getId(); // get the id of the most recent addition
            role = dao.get(id); // get the role by id
            assertEquals("Table names don't match", "Donald", role.getUsername());
        }

        @Test
        public void testAdd() throws Exception {
            justAdd();
            roleList = dao.getAll();
            logger.info("role list has " + roleList.size() + " entries");
            assertEquals("Add did not work: ", numberOfRoles + 1, roleList.size());
        }

        @Test
        public void testModifyRoleName() throws Exception {
            role = roleList.get(roleList.size() - 1); // retrieve most recent addition to table
            int id = role.getId();
            role.setUsername("Trump");
            logger.info(role.toString());
            logger.info("Updated role ID = " + dao.modify(role));
            //dao.modify(role);
            role = dao.get(id);
            assertEquals("role table name not modified", "Trump", role.getUsername());
            roleList = dao.getAll();
            assertEquals("Modify added an entry!", numberOfRoles, roleList.size());
        }

        private void justAdd() {
            role = new entity.Role("Donald", "Commander");
            logger.info("New role is " + role.toString());
            dao.add(role);
        }

        @Test
        public void testRemove() throws Exception {
            role = roleList.get(roleList.size() - 1); // retrieve most recent addition to table
            int id = role.getId();
            dao.remove(id);
            roleList = dao.getAll();
            assertEquals("remove did not work: ", numberOfRoles - 1, roleList.size());
        }


        @After
        public void cleanup() throws Exception {
            roleList = dao.getAll();

            for (entity.Role role : roleList) {
                String thisName = role.getUsername();
                if (thisName.equalsIgnoreCase("Donald")) {
                    dao.remove(role.getId());
                } else if (thisName.equalsIgnoreCase("Trump")) {
                    dao.remove(role.getId());
                }
            }
        }
    }

