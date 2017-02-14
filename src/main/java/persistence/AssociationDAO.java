package persistence;

import entity.Association;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by peter on 2/14/2017.
 */
public class AssociationDAO {

        private final Logger logger = Logger.getLogger(this.getClass());
        Session session;
        Transaction transaction;
        Association association;
        List<Association> associations;

        /** Return a list of all associations
         *
         * @return All associations
         */
        public List<Association> getAll() throws HibernateException {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            associations = session.createCriteria(Association.class).list();
            session.close();
            return associations;
        }

        /** Get a single association for the given id
         *
         * @param id association's id
         * @return association
         */
        public Association get(int id) throws HibernateException {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            association = (Association) session.get(Association.class, id);
            session.close();
            return association;
        }

        /** save a new association
         * @param association
         * @return id the id of the inserted record
         */
        public int add(Association association) throws HibernateException {
            Session session = SessionFactoryProvider.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            logger.info("Saving association " + association.getId());
            logger.info(association.toString());
            int id = (Integer) session.save(association);
            transaction.commit();
            session.close();
            return id;
        }

        /** modify a association record
         * @param updatedAssociation the version of the association with the new information
         * @return id the id of the updated record
         */
        public int modify(Association updatedAssociation) throws HibernateException {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            logger.info("Updating association " + updatedAssociation.getId());
            logger.info(updatedAssociation.toString());
            Association sessionAssociation = (Association) session.get(Association.class, updatedAssociation.getId());
            sessionAssociation.setLeftTableKey(updatedAssociation.getLeftTableKey());
            sessionAssociation.setLeftTableName(updatedAssociation.getLeftTableName());
            sessionAssociation.setRightTableKey(updatedAssociation.getRightTableKey());
            sessionAssociation.setRightTableName(updatedAssociation.getRightTableName());
            logger.info("Updating association " + sessionAssociation.getId());
            logger.info(sessionAssociation.toString());
            Association resultantAssociation = (Association) session.merge(sessionAssociation);
            logger.info("Updated association " + resultantAssociation.toString());
            transaction.commit();
            session.close();
            return resultantAssociation.getId();
        }

        /**
         * Removes a association
         *
         * @param id ID of association to be removed
         */
        public void remove(int id) throws HibernateException {
            logger.info("In dao.remove with id = " + id);
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Association association = (Association) session.get(Association.class, id);
            logger.info("In dao. remove with association " + association.toString());
            session.delete(association);
            transaction.commit();
            session.close();
        }
    }

