package persistence;

import org.hibernate.Session;

import java.io.Serializable;

/**
 * Manages database table contents via Hibernate
 * @param <T> The object type corresponding to a database table
 * @param <PK> The primary key for a database table entry
 */
public class GenericDAO<T, PK extends Serializable>
        implements GenericDaoable<T, PK> {

    private Class<T> type;

    public GenericDAO() {}

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    /**
     * Adds an entry to a table
     * @param o the object corresponding to the new entry
     * @return the primary key to for the new entry
     */
    public PK create(T o) {
        Session session = getSession();
        session.beginTransaction();
        PK id = (PK) session.save(o);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    /**
     * Retrieves a table entry
     * @param id the primary key for the entry
     * @return the table entry
     */
    public T read(PK id) {
        Session session = getSession();
        session.beginTransaction();
        T o = (T) session.get(type, id);
        session.getTransaction().commit();
        session.close();
        return o;
    }

    /**
     * Updates a table entry
     * @param o the updated object to place in the table
     */
    public void update(T o) {
        Session session = getSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Deletes a table entry
     * @param o the entry to delete
     */
    public void delete(T o) {
        Session session = getSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
    }

    public Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

}
