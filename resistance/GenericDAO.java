package resistance;

import java.io.Serializable;
import org.hibernate.Session;
import persistence.SessionFactoryProvider;

/**
 * Created by peter on 4/24/2017.
 */
public class GenericDAO <T, PK extends Serializable> {
    private Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    public PK create(T o) {
        return (PK) getSession().save(o);
    }

    public T read(PK id) {
        return (T) getSession().get(type, id);
    }

    public void update(T o) {
        getSession().update(o);
    }

    public void delete(T o) {
        getSession().delete(o);
    }

    Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }
}
