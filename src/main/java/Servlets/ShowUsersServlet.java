package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import entity.User;
import org.apache.log4j.Logger;
import persistence.UserDAO;

/**
 * Get the full list of users to populate the user management page
 * Created by peter on 2/8/2017.
 */
@WebServlet(
        name = "ShowUsers",
        urlPatterns = { "/ShowUsers" }
)public class ShowUsersServlet  extends HttpServlet {
    final Logger logger = Logger.getLogger(this.getClass());

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
        UserDAO userdao = new UserDAO();
        List<User> users = userdao.getAll();

        ServletContext servletContext = getServletContext();

        request.setAttribute("users", userdao.getAll());
        String currentMessage = (String) request.getAttribute("SessionMessage");
        if (currentMessage == null) {
            request.setAttribute("SessionMessage", "");
        } else
            request.setAttribute("SessionMessage", currentMessage);

        String url = "/Accounts.jsp";

        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}