package persistence;

import entity.PropertyManager;
import entity.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

public class RoleDAO {
    //private final Logger logger = Logger.getLogger(this.getClass());
    final static private PropertyManager propertyManager = new PropertyManager();
    final static private int fieldLength = Integer.valueOf(propertyManager.getProperty("dbNameLength"));
    /** Return a list of all roles
     *
     * @return All roles
     */
    public List<Role> getAll() throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Role> Roles = session.createCriteria(Role.class).list();
        session.close();
        return Roles;
    }

    /** Get a single Role for the given id
     *
     * @param id Role's id
     * @return Role
     */
    public Role get(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Role Role = (Role) session.get(Role.class, id);
        session.close();
        return Role;
    }

    /** save a new Role
     * @param Role the record to add to the Roles table
     * @return id the id of the inserted record
     */
    public int add(Role Role) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(Role);
        transaction.commit();
        session.close();
        return id;
    }

    /** modify a Role record
     * @param updatedRole the version of the Role with the new information
     * @return id the id of the updated record
     */
    public int modify(Role updatedRole) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Role sessionRole = (Role) session.get(Role.class, updatedRole.getId());
        sessionRole.setRole_name(updatedRole.getRole_name());
        sessionRole.setRole_name(updatedRole.getRole_name());
        Role resultantRole = (Role) session.merge(sessionRole);
        transaction.commit();
        session.close();
        return resultantRole.getId();
    }


    /**
     * Performs simple checks for an acceptable Rolename
     *
     * @param Rolename the proposed Rolename
     * @return whether the Rolename is acceptable
     */
    private static boolean checkRolename(String Rolename) {
        return ((2 < Rolename.length() && (fieldLength >= Rolename.length()))); // Require at least 3 characters in Rolename
    }

    /**
     * Determines whether a role name is valid
     *
     * @param rolename The role name to be tested
     * @return Whether or not the role name is valid
     */
    private static boolean checkRoleName(String rolename) {
        ArrayList<String> definedRoles = new ArrayList<>();
        definedRoles.add("administrator");
        definedRoles.add("registered-Role");

        return (definedRoles.contains(rolename));
    }
    /**
     * Removes a Role
     *
     * @param id ID of Role to be removed
     */
    public void remove(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Role Role = (Role) session.get(Role.class, id);
        session.delete(Role);
        transaction.commit();
        session.close();
    }
}
