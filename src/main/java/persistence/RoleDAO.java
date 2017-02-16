package persistence;

import entity.Role;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by peter on 2/15/2017.
 */
public class RoleDAO {
    
    private final Logger logger = Logger.getLogger(this.getClass());
    Session session;
    Transaction transaction;
    Role role;
    List<Role> roles;

        /** Return a list of all roles
         *
         * @return All roles
         */
        public List<Role> getAll() throws HibernateException {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            roles = session.createCriteria(Role.class).list();
            session.close();
            return roles;
        }

        /** Get a single role for the given id
         *
         * @param id role's id
         * @return role
         */
        public Role get(int id) throws HibernateException {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            role = (Role) session.get(Role.class, id);
            session.close();
            return role;
        }

        /** save a new role
         * @param role
         * @return id the id of the inserted record
         */
        public int add(Role role) throws HibernateException {
            Session session = SessionFactoryProvider.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            logger.info("Saving role " + role.getId());
            logger.info(role.toString());
            int id = (Integer) session.save(role);
            transaction.commit();
            session.close();
            return id;
        }

        /** modify a role record
         * @param updatedRole the version of the role with the new information
         * @return id the id of the updated record
         */
        public int modify(Role updatedRole) throws HibernateException {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            logger.info("Updating role " + updatedRole.getId());
            logger.info(updatedRole.toString());
            Role sessionRole = (Role) session.get(Role.class, updatedRole.getId());
            sessionRole.setUsername(updatedRole.getUsername());
            sessionRole.setRolename(updatedRole.getRolename());
            logger.info("Updating role " + sessionRole.getId());
            logger.info(sessionRole.toString());
            Role resultantRole = (Role) session.merge(sessionRole);
            logger.info("Updated role " + resultantRole.toString());
            transaction.commit();
            session.close();
            return resultantRole.getId();
        }

        /**
         * Removes a role
         *
         * @param id ID of role to be removed
         */
        public void remove(int id) throws HibernateException {
            logger.info("In dao.remove with id = " + id);
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Role role = (Role) session.get(Role.class, id);
            logger.info("In dao. remove with role " + role.toString());
            session.delete(role);
            transaction.commit();
            session.close();
        }
    }


