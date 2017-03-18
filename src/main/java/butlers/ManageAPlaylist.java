package butlers;
import java.nio.file.*;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import engines.UserManager;
import entity.Song;
import persistence.SongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Handle request from manageAPlaylist jsp
 * This can be a) request to upload a new song
 *             b) request to delete a song
 * Created by peter on 3/17/2017.
 */
@WebServlet(
        name = "ManageAPlaylist",
        urlPatterns = { "/ManageAPlaylist" }
)
@MultipartConfig
public class ManageAPlaylist extends HttpServlet {

    // class instance variables
    private final Logger logger = Logger.getLogger(this.getClass());
    private final SongDAO songDAO = new SongDAO();
    private final UserManager userManager = new UserManager();
    private HttpSession session;
    private String url;
    private Song song;
    private int songID;
    private final ServletContext servletContext = getServletContext();
    private RequestDispatcher dispatcher;


    /**
     * Handles HTTP POST requests.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a Servlet failure
     * @throws IOException      if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();

        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {

            songID = Integer.valueOf(request.getParameter("songID"));
            if (songID == 0) { // request to upload a new song
                final Part filePart = request.getPart("file");
                final PrintWriter writer = response.getWriter();

                logger.info("In upload section");
                // Create path components to save the file
                String path = "../Data/";
                final String fileName = getFileName(filePart);
                path += fileName;
                boolean success = writeFile(path, filePart, fileName, writer);
                if (success) { // Add new song to database
                    int listID = (Integer) (session.getAttribute("listID"));
                    song = new Song(path, listID );
                    songDAO.add(song);
                    logger.info("Added song record to the database");
                    session.setAttribute("message", "Song Added");
                } else {
                    session.setAttribute("message", "Unable to add song");
                    logger.info("Song addition failed");
                }

                url = "/manageAPlayList.jsp";
            } else { // must be delete
                logger.info("In delete section");
                session.setAttribute("songToDelete", songDAO.get(songID).getLocation());
                session.setAttribute("songID", songID);
                url = "/deleteSongConfirmation.jsp";
            }
        } else { // user not authenticated
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }

        dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
/*        logger.info("Sending redirect to " + url);
        response.sendRedirect(url); */
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        logger.info( "Part Header = " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private boolean writeFile(String path, Part filePart, String fileName, PrintWriter writer) throws IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        boolean success = true;
        logger.info("in writeFile");

        try {
            logger.info("in try block. about to open " + path);
            Path relPath = Paths.get(path);
            logger.info("File " + fileName + " being uploaded to " + relPath.toAbsolutePath());
            out = new FileOutputStream(new File(path));
            logger.info("FileOutputStream successfully opened");
            filecontent = filePart.getInputStream();
            logger.info("InputStream successfully opened");

            int read;
            final byte[] bytes = new byte[1024];


            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            logger.info("File content copied");
        } catch (FileNotFoundException fne) {
            logger.error( "Problems during file upload. Error: " + fne.getMessage());
            success = false;
        } finally {
            if (out != null) {
            out.close();
            }
            logger.info("closed out");
            if (filecontent != null) {
                filecontent.close();
            }
            logger.info("closed filecontent");

            if (writer != null) {
                writer.close();
            }
            logger.info("closed writer");
        }
        return success;
    }
}
