package butlers;

import engines.PlaylistManager;
import engines.SongManager;
import engines.UserManager;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    private final UserManager userManager = new UserManager();
    private final Logger logger = Logger.getLogger(this.getClass());
    private final SongManager songManager = new SongManager();
    private final PlaylistManager playlistManager = new PlaylistManager();
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
        String url = "/index.jsp";

        logger.debug("This doPost is called");
        logger.debug("The user ID is " + session.getAttribute("user_id"));
        logger.debug("Request URI is " + request.getRequestURI());

        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {
            url = "ShowAPlaylist";
            logger.info("Session attribute listID is " + session.getAttribute("listID"));
            int listID = (Integer) (session.getAttribute("listID"));
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                String path1 = part.getSubmittedFileName();
                logger.info("Request Part submitted file name is " + path1);
                if (path1 != null) {
                    String path = songManager.getRepository() + part.getSubmittedFileName();
                    processRequest(part, path, listID);
                }
            }
        } else { // user not authenticated
            messageContent = "user not authenticated";
        }
        session.setAttribute("message", messageContent);
        response.sendRedirect(url);
    }

    private void processRequest(Part part, String filePath, int playListId) throws IOException{

        //multiple cases
        // Case 1: Song is already in database under this listID.
        if (playlistManager.alreadyThere(filePath, playListId)) { //Don't need to do anything
            messageContent = "Song already there";
        }
        // Case 2: Song is already in database under a different listID
        if (songManager.exists(filePath)) { // still need to add it to playlist
            songManager.add(filePath, playListId );
            messageContent = "Song added";
        }
        // Case 3: Song is totally new to database. This is reflected below
        boolean success = writeFile(filePath, part, filePath);
        if (success) { // Add new song to database
            songManager.add(filePath, playListId );
            messageContent = "Song added";
        } else {
            messageContent = "Unable to add song";
            logger.info("Song addition failed");
        }
        logger.info("About to forward to ShowAPlaylist");
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
