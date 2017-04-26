package resistance;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EntryDAO {

    /** Return a list of all entries
     *
     * @return All entries
     */
    public List<Entry> getAll() throws HibernateException {
        Session session = persistence.SessionFactoryProvider.getSessionFactory().openSession();
        List<Entry> users = session.createCriteria(Entry.class).list();
        session.close();
        return users;
    }

    /** Get a single entries for the given id
     *
     * @param id entries's id
     * @return User the entry
     */
    public Entry get(int id) throws HibernateException {
        Session session = persistence.SessionFactoryProvider.getSessionFactory().openSession();
        Entry user = (Entry) session.get(Entry.class, id);
        session.close();
        return user;
    }

    /** save a new user
     * @param entry the record to add to the table
     * @return id the id of the inserted record
     */
    public int add(Entry entry) throws HibernateException {
        Session session = persistence.SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(entry);
        transaction.commit();
        session.close();
        return id;
    }

    /** modify a record
     * @param entry the version of the entry with the new information
     * @return id the id of the updated record
     */
    public int modify(Entry entry) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entry);
        transaction.commit();
        session.close();
        return entry.getId();
    }

    /**
     * Fetches the system IDs of the users
     *
     * @return The IDs of the other users
     */
    public ArrayList<Integer> getIds() throws HibernateException {
        ArrayList<Integer> users = new ArrayList<>();

            for (Entry user : getAll()) {
                Integer userID = user.getId();
                users.add(userID);
            }

        return users;
    }


    /**
     * Retrieves the system ID of a user with a given name
     *
     * @param name The name of the user
     * @return The system ID of a user with that name or zero if it fails
     */
    public int getIdByName(String name) throws HibernateException {
            List<Entry> users = getAll();
            for (Entry user : users) {
                Integer userID = user.getId();
                if (name.equalsIgnoreCase(user.getName())) {
                    return userID;
                }
            }

        return 0;
    }


    /**
     * Retrieves a user's personal name
     *
     * @param userId The unique identifier
     * @return The personal name
     */
    public String getName(int userId) throws HibernateException {
            Entry user = get(userId);
            if (user == null) {
                return null;
            }
            return user.getName();
    }
    /**
     * Removes an entry
     *
     * @param id ID of entry to be removed
     */
    public void remove(int id) throws HibernateException {
        Session session = persistence.SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Entry user = (Entry) session.get(Entry.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }


    Session getSession() {
        return persistence.SessionFactoryProvider.getSessionFactory().openSession();
    }
}
