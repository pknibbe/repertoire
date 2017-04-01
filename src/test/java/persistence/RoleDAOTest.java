package persistence;

import entity.Role;
//import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoleDAOTest {


    private RoleDAO dao;
    private entity.Role role;
    private int numberOfRoles;
    private List<Role> roleList;
    //final Logger logger = Logger.getLogger(this.getClass());

        @Before
        public void setup() throws Exception {
            dao = new RoleDAO();
            roleList = dao.getAll();
            justAdd(); // make sure table is not empty for purpose of test
            roleList = dao.getAll();
            numberOfRoles = roleList.size();
        }

        @Test
        public void testGetAll() throws Exception {
            boolean found = false;

            for (entity.Role role : roleList) {
                String thisName = role.getUser_name();
                if (thisName.equalsIgnoreCase("Donald")) found = true;
            }
            assertTrue("The expected table name was not found: ", found);
        }

        @Test
        public void testGet() throws Exception {
            role = roleList.get(roleList.size() - 1); // retrieve most recent addition to table
            int id = role.getId(); // get the id of the most recent addition
            role = dao.get(id); // get the role by id
            assertEquals("Table names don't match", "Donald", role.getUser_name());
        }

        @Test
        public void testAdd() throws Exception {
            role = new entity.Role("Barak", "Sage");
            dao.add(role);            roleList = dao.getAll();
            assertEquals("Add did not work: ", numberOfRoles + 1, roleList.size());
        }

        @Test
        public void testModifyRoleName() throws Exception {
            role = roleList.get(roleList.size() - 1); // retrieve most recent addition to table
            int id = role.getId();
            role.setUser_name("Trump");
            dao.modify(role);
            role = dao.get(id);
            assertEquals("role table name not modified", "Trump", role.getUser_name());
            roleList = dao.getAll();
            assertEquals("Modify added an entry!", numberOfRoles, roleList.size());
        }

        private void justAdd() {
            role = new entity.Role("Donald", "Commander");
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
                String thisName = role.getUser_name();
                if (thisName.equalsIgnoreCase("Donald")) {
                    dao.remove(role.getId());
                } else if (thisName.equalsIgnoreCase("Barak")) {
                    dao.remove(role.getId());
                } else if (thisName.equalsIgnoreCase("Trump")) {
                    dao.remove(role.getId());
                }
            }
        }
    }

