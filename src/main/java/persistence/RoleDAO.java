package persistence;

import entity.Role;
//import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoleDAO {
    
    //private final Logger logger = Logger.getLogger(this.getClass());
    private Session session;
    private Transaction transaction;

    /** Return a list of all roles
         *
         * @return All roles
         */
        public List<Role> getAll() throws HibernateException {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            List<Role> roles = session.createCriteria(Role.class).list();
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
            Role role = (Role) session.get(Role.class, id);
            session.close();
            return role;
        }

        /** save a new role
         * @param role The role to save
         * @return id the id of the inserted record
         */
        public int add(Role role) throws HibernateException {
            Session session = SessionFactoryProvider.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
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
            Role sessionRole = (Role) session.get(Role.class, updatedRole.getId());
            sessionRole.setUser_name(updatedRole.getUser_name());
            sessionRole.setRole_name(updatedRole.getRole_name());
            Role resultantRole = (Role) session.merge(sessionRole);
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
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Role role = (Role) session.get(Role.class, id);
            session.delete(role);
            transaction.commit();
            session.close();
        }
    }


