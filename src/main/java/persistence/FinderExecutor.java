package persistence;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Iterator;

import org.hibernate.ScrollableResults;

/**
 * Created by peter on 2/11/2017.
 */
public interface FinderExecutor<T>
{
    /**
     * Execute a finder method with the appropriate arguments
     */
    List<T> executeFinder(Method method, Object[] queryArgs);

    Iterator<T> iterateFinder(Method method, Object[] queryArgs);

    ScrollableResults scrollFinder(Method method, Object[] queryArgs);
}
