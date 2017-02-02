package DAO;

import Beans.Credential;
import Utilities.SessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2/1/2017.
 */
public class DAOCredential {

    private final Logger logger = Logger.getLogger(this.getClass());

    /** Return a list of all credential
     *
     * @return All credential
     */
    public List<Credential> getAllCredentials() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return session.createCriteria(Credential.class).list();
    }

    /** Get a single Credential for the given id
     *
     * @param id Credential's id
     * @return Credential
     */
    public Credential getCredential(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return (Credential) session.get(Credential.class, id);
    }

    /** save or update Credential
     * @param Credential
     * @return id of the inserted Credential
     */
    public void save(Credential Credential) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        logger.info(session.save(Credential));
        transaction.commit();
    }


    /**
     * Removes a credential
     *
     * @param credential user to be removed
     */
    public void remove(Credential credential) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(credential);
        transaction.commit();
    }
}


