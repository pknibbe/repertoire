package edu.matc.JLA.persistence;

import org.hibernate.Session;
import java.io.Serializable;

/**
 * A generic DAO interface for CRUD operations.
 * @param <T>
 * @param <PK>
 * @see https://www.ibm.com/developerworks/library/j-genericdao/
 */
public interface GenericDaoable <T, PK extends Serializable> {

    /**
     * Persist the newInstance object into database
     */
    PK create(T newInstance);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     */
    T read(PK id);

    /**
     * Save changes made to a persistent object.
     */
    void update(T transientObject);

    /**
     * Remove an object from persistent storage in the database
     */
    void delete(T persistentObject);

    Session getSession();

}