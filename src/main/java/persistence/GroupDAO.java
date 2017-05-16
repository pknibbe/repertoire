package persistence;

import entity.Group;
import entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class GroupDAO extends GenericDAO<Group, Integer> {
    private final UserDAO userDAO = new UserDAO();

    public GroupDAO() { super(Group.class); }

    /** Return a list of all groups
     *
     * @return All Groups
     */
    public List<Group> getAll() throws HibernateException {
        Session session = getSession();
        List<Group> groups = session.createCriteria(Group.class).list();
        session.close();
        return groups;
    }

    public Group getGroupByName(String name) throws HibernateException
    {
        Session session = getSession();
        Query query = session.createQuery("FROM Group G WHERE G.name = :name");
        query.setParameter("name", name);
        List<Group> groups = query.list();
        session.close();
        if (groups == null) return null;
        if (groups.size() < 1) return null;
        return groups.get(0);
    }

    /**
     * Removes a groups after making safety checks for privilege and use status
     * @param groupID The system ID of the playlist
     * @param user_id The user trying to delete the playlist
     * @return whether the remove succeeded
     */
    public boolean remove(int groupID, int user_id) throws HibernateException
    {
        Group group = read(groupID);
        if (userDAO.getAdminId() != user_id) return false; // Non-admin can't delete a group
        if (isUsed(groupID)) return false; // Don't delete a group that is in use
        delete(group);
        return true;
    }

    public boolean isUsed(int groupID) {
        boolean used = false;
        for (User user : userDAO.getAll()) {
            if (user.getGroup().getId() == groupID) used = true;
        }
        return used;
    }

}
