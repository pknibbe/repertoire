package butlers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.*;

import engines.Authentication;
import persistence.PlaylistDAO;
/**
 * Provides access to Music Play Lists
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowPlayLists",
        urlPatterns = { "/ShowPlayLists" })
public class ShowPlayLists extends HttpServlet {

    /**
     *  Handles HTTP GET requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlaylistDAO dao = new PlaylistDAO();

        ServletContext servletContext = getServletContext();
        Authentication authentication = new Authentication();
        String url;

        if (authentication.authenticated(servletContext)) {
        servletContext.setAttribute("message", "");
        request.setAttribute("playlists", dao.getAll());
        url = "/playlists.jsp";


        } else {
            servletContext.setAttribute("message", "user not authenticated");
            url = "index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
