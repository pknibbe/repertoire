package butlers;

import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import engines.PlaylistManager;


/**
 * Remove a playlist from the database
 * Created by Peter Knibbe on 2/8/17.
 */

@WebServlet(
        name = "DeletePlaylist",
        urlPatterns = { "/DeletePlaylist" }
)
public class DeletePlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistManager manager = new PlaylistManager();

    /**
     *  Handles HTTP POST requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            logger.info("Parameter " + parameterName + " is " + request.getParameter(parameterName));
            if (parameterName.equalsIgnoreCase("Delete")) {
                manager.remove((Integer) servletContext.getAttribute("listID"));
                servletContext.setAttribute("message", "List Deleted");
                logger.info("removed playlist " + servletContext.getAttribute("listID"));
            } else if (parameterName.equalsIgnoreCase("Cancel")) {
                logger.info("Did not remove playlist " + servletContext.getAttribute("listID"));
                servletContext.setAttribute("message", "List Not Deleted");
            }
        }
        String url = "/ShowPlayLists";

        logger.info("sending redirect to " + url);
        response.sendRedirect(url);
    }
}