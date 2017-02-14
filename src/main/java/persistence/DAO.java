package persistence;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
@ToString

/**
 * Created by peter on 2/14/2017.
 */
public class DAO {
    private final Logger logger = Logger.getLogger(this.getClass());
    private Session session;
    private Transaction transaction;
    private Object object;
    private List<Object> objects;

    /* constructors */
    public DAO() {
    }

    /* return a list of objects */
    List<Object> getAll() throws HibernateException {
        setSession(SessionFactoryProvider.getSessionFactory().openSession());
        objects = session.createCriteria(Object.class).list();
        session.close();
        return objects;
    }

    /* Get a single object for the given id */
    Object get(int id) throws HibernateException {
        setSession(SessionFactoryProvider.getSessionFactory().openSession());
        object = session.get(Object.class, id);
        session.close();
        return object;
    }

    /* save a new object */
    int add(Object object) throws HibernateException {
        setSession(SessionFactoryProvider.getSessionFactory().openSession());
        transaction = session.beginTransaction();
        int id = (Integer) session.save(object);
        transaction.commit();
        session.close();
        return id;
    }

    /* Remove an object specified by id */
    void remove(int id) throws HibernateException {
        setSession(SessionFactoryProvider.getSessionFactory().openSession());
        transaction = session.beginTransaction();
        object = session.get(Object.class, id);
        session.delete(object);
        transaction.commit();
        session.close();
    }
}
