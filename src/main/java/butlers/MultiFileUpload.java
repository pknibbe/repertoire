package butlers;

import entity.Song;
import entity.User;
import persistence.SongDAO;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import persistence.PlaylistDAO;

/**
 * Handle request from manageAPlaylist jsp
 * This can be a) request to upload a new song
 *             b) request to delete a song
 * Created by peter on 3/17/2017.
 */
@WebServlet(
        name = "MultiFileUpload",
        urlPatterns = { "/MultiFileUpload" }
)
@MultipartConfig
public class MultiFileUpload extends HttpServlet {
    private final SongDAO songDAO = new SongDAO();
    private final PlaylistDAO playlistDAO = new PlaylistDAO();

    private final Logger logger = Logger.getLogger(this.getClass());
    private String messageContent;


    /**
     * Handles HTTP POST requests.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a Servlet failure
     * @throws IOException      if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (((User) session.getAttribute("user")).getId() < 1) { // Guest users may not create, delete, or edit playlists
            response.sendRedirect("LogOut");
        } else {
            int listID = (Integer) (session.getAttribute("listID"));

            logger.debug("Session attribute listID is " + listID);
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                String path1 = part.getSubmittedFileName();
                logger.info("Request Part submitted file name is " + path1);
                if (path1 != null) {
                    processRequest(part, part.getSubmittedFileName(), listID);
                }
            }

            try {
                session.setAttribute("songs", songDAO.getAllThese(listID));
                session.setAttribute("message", messageContent);
                response.sendRedirect("manageAPlaylist.jsp");
            } catch (Exception e) {
                logger.error("Serious error caught. Logging the user out.", e);
                session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
                response.sendRedirect("error.jsp");
            }
        }
    }

    private void processRequest(Part part, String filePath, int playListId) throws IOException{
        String repo = songDAO.getRepository();

        //multiple cases
        // Case 1: Song is already in database under this listID.
        if (songDAO.alreadyThere(filePath, playListId)) { //Don't need to do anything
            messageContent = "Song already there";
        }
        // Case 2: Song is already in database under a different listID
        else if (songDAO.exists(filePath)) { // still need to add it to playlist
            songDAO.create(new Song(filePath, playlistDAO.read(playListId)));
            messageContent = "Song added";
        } else {
            // Case 3: Song is totally new to database. This is reflected below
            boolean success = writeFile(repo + filePath, part, filePath);
            if (success) { // Add new song to database
                songDAO.create(new Song(filePath, playlistDAO.read(playListId)));
                messageContent = "Song added";
            } else {
                messageContent = "Unable to add song";
                logger.info("Song addition failed");
            }
        }
    }
/*
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
*/
    private boolean writeFile(String path, Part filePart, String fileName) throws IOException {
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

        }
        return success;
    }

}
