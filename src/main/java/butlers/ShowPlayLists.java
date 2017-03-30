package butlers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

import engines.UserManager;
import engines.PlaylistManager;
import entity.*;
import persistence.*;
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

        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        UserManager userManager = new UserManager();
        String url;

        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {
            ArrayList<PresentablePlaylist> presentables = new ArrayList<>();
            Playlist playlist;
            PlaylistDAO playlistDAO = new PlaylistDAO();
            UserDAO userDAO = new UserDAO();

            int listOwnerID;

            PlaylistManager playlistManager = new PlaylistManager();
            ArrayList<Integer> listIDs = playlistManager.getIDs((Integer) session.getAttribute("user_id"));
            for (int index : listIDs) {
                playlist = playlistDAO.get(index);
                listOwnerID = playlist.getOwner_id();
                presentables.add(new PresentablePlaylist(index,
                                                         playlist.getName(),
                                                         listOwnerID,
                                                         userDAO.get(listOwnerID).getName()));
            }
            session.setAttribute("playlists", presentables);
            url = "/playlists.jsp";

        } else {
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
