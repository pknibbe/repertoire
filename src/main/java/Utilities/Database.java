package Utilities;

import java.util.*;
import java.util.Properties;
import java.sql.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.apache.log4j.*;

/**
 * Configurable class to implement a database connection
 * Could be made to implement an interface, but that does not seem necessary at this time
 *
 * @author Peter Knibbe
 * @since December 8, 2016
 */
public class Database {

    final Logger logger = Logger.getLogger(this.getClass());
    private java.sql.Connection connection;
    private Properties properties;
    private HttpSession session;

    /**
     * default constructor
     */
    public Database() {
        this.session = null;
    }

    /**
     * properties-based constructor
     *
     * @param properties for the Utilities.Database class
     */
    public Database(Properties properties) {
        this();
        this.properties = properties;

        connect();
    }

    public Database(HttpSession session) {
        super();
        this.session = session;
        properties = (Properties) session.getAttribute("Properties");

        connect();

    }

    /**
     * make connection to database
     */
    private void connect () {

        if (connection == null) {
            String databasePropertiesPath = properties.getProperty("database");
            MyProperties myProperties = new MyProperties(databasePropertiesPath);
            if (myProperties != null) {
                Properties dbProperties = myProperties.getProperties();

                try {
                    Class.forName(dbProperties.getProperty("driver"));
                } catch (ClassNotFoundException classNotFound) {
                    logger.error("PWK:Utilities.Database Cannot find database driver ", classNotFound);
                }

                try {
                    connection = DriverManager.getConnection(dbProperties.getProperty("url"),
                            dbProperties.getProperty("username"),
                            dbProperties.getProperty("password"));
                } catch (SQLException sqlException) {
                    logger.error("PWK:Utilities.Database Error in connecting to database ", sqlException);
                } catch (Exception exception) {
                    logger.error("PWK:Utilities.Database General Error", exception);
                }
            }
        }
    }

    /**
     * disconnect from database
     */
    public void disconnect() {

        try {
        connection.close();
        } catch (SQLException sqlException) {
            logger.error("PWK:Utilities.Database Error in disconnecting from database ", sqlException);
        } catch (Exception exception) {
            logger.error("PWK:Utilities.Database General Error");
        } finally {
            connection = null;
        }
    }

    /**
     * query the database
     * @param queryString The query to use
     * @return resultSet The query results
     */
    public ResultSet query(String queryString) {

        connect();
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                System.out.println("PWK:Utilities.Database executing " + queryString);
                return statement.executeQuery(queryString);
            }
            else {
                logger.error("PWK:Utilities.Database connection is null");
                return null;
            }
        } catch (SQLException sqlException) {
            logger.error("PWK:Utilities.Database Error in connecting to database ", sqlException);
        } catch (Exception exception) {
            logger.error("PWK:Utilities.Database General Error", exception);
        }
        return null;
    }

    /**
     * update the database
     * @param updateString The update to use
     * @return The number of updated table rows
     */
    public int update(String updateString) {

        connect();
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                return statement.executeUpdate(updateString);
            }
            else {
                logger.error("PWK:Utilities.Database connection is null");
                return 0;
            }
        } catch (SQLException sqlException) {
            System.err.println(java.text.MessageFormat.format("PWK:Utilities.Database Error in connecting to database {0}", sqlException));
            sqlException.printStackTrace();
        } catch (Exception exception) {
            System.err.println("PWK:Utilities.Database General Error");
            exception.printStackTrace();
        }
        return 0;
    }
}

