package edu.matc.JLA.persistence;

import org.hibernate.Session;

import java.io.Serializable;

public class GenericDao <T, PK extends Serializable>
        implements GenericDaoable<T, PK> {

    private Class<T> type;

    public GenericDao() {}

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public PK create(T o) {
        Session session = getSession();
        session.beginTransaction();
        PK id = (PK) session.save(o);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public T read(PK id) {
        Session session = getSession();
        session.beginTransaction();
        T o = (T) session.get(type, id);
        session.getTransaction().commit();
        session.close();
        return o;
    }

    public void update(T o) {
        Session session = getSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
    }

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
