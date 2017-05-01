package butlers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import entity.User;
import persistence.SharedDAO;

//import org.apache.log4j.Logger;

/**
 * Update a user in the database table
 * Created by peter on 2/27/2017.
 */
@WebServlet(
        name = "SharePlaylist",
        urlPatterns = { "/SharePlaylist" }
)
public class SharePlaylist extends HttpServlet {
    //private final Logger logger = Logger.getLogger(this.getClass());
    private final SharedDAO sharedDAO = new SharedDAO();

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
            HttpSession session = request.getSession();

            if (((User) session.getAttribute("user")).getId() < 1) {
                Navigator.redirect(response, "LogOut");
            } else {
                int userID = (Integer) session.getAttribute("userID");
                int playlist_id = (Integer) session.getAttribute("listID");
                if (request.getParameter("Share") != null) {
                    sharedDAO.share(playlist_id, userID);
                } else if (request.getParameter("UnShare") != null) {
                    sharedDAO.delete(sharedDAO.read(sharedDAO.find(playlist_id, userID)));
                }

                session.setAttribute("potentialSharees", sharedDAO.notSharing(playlist_id, userID));
                session.setAttribute("currentSharees", sharedDAO.sharing(playlist_id));
                Navigator.redirect(response, "/manageAPlaylist");
            }
        }
    }
