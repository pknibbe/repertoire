package engines;

import javax.servlet.ServletContext;

/**
 * Simple check that servlet context has gone through login
 * Created by peter on 3/8/2017.
 */
public class Authentication {

    public boolean authenticated(ServletContext servletContext) {
        boolean aok = java.lang.Boolean.FALSE;
        Integer userID = (Integer) servletContext.getAttribute("user_id");
        if (userID != null) {
            if (userID > 0) aok = java.lang.Boolean.TRUE;
        }
        return aok;
    }

}
