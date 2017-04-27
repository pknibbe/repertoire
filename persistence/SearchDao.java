package edu.matc.JLA.persistence;

import edu.matc.JLA.entity.Search;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geedog on 4/17/17.
 */
public class SearchDao extends GenericDao<Search, Integer> {

    private final Logger log = Logger.getLogger(this.getClass());

    public SearchDao() {
        super(Search.class);
    }

    /** Return a list of all searches
     *
     * @return All searches
     */
    public List<Search> getAllSearches() {
        log.debug("****** SearchDao.getAllSearches()... entering...");
        List<Search> searches = new ArrayList<Search>();
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        searches = session.createCriteria(Search.class).list();
        session.close();
        log.debug("****** SearchDao.getAllSearches()... leaving...");
        return searches;
    }

}
