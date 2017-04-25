package resistance;

import entity.PropertyManager;

//import org.apache.log4j.Logger;

public class UserEntryDAO extends EntryDAO {
    //private final Logger logger = Logger.getLogger(this.getClass());
    final static private PropertyManager propertyManager = new PropertyManager();
    final static private int fieldLength = Integer.valueOf(propertyManager.getProperty("dbNameLength"));

    /**
     * Determines whether a session has an authenticated user
     *
     * @param user_id The id to be authenticated
     * @return whether the user could be authenticated
     */
    public boolean authenticated(Integer user_id) {
        boolean aok = false;
        if (user_id != null) {
            if (user_id > 0) aok = true;
        }
        return aok;
    }


    /**
     * Determines whether a set of credentials is valid
     *
     * @param user_name The first part of the credential set
     * @param user_pass The other part of the credential set
     * @return The unique identifier or, if the credentials are not valid, 0
     */
 /*   public int verifyCredentials(String user_name, String user_pass) throws HibernateException {
        int user_id = 0;
            List<User> users = (List<User>) getAll();
            for (User user : users) {
                if (user_name.equalsIgnoreCase(user.getUser_name()) &&
                        (user_pass.equalsIgnoreCase(user.getUser_pass()))) {
                    user_id = user.getId();
                }
            }


        return user_id;
    }

    /**
     * Fetches the system IDs of the users
     *
     * @return The IDs of the other users
     */
 /*   public ArrayList<Integer> getUserIds() throws HibernateException {
        ArrayList<Integer> users = new ArrayList<>();

            for (User user : getAll()) {
                Integer userID = user.getId();
                users.add(userID);
            }

        return users;
    }

    /**
     * Fetches the Names of the users other than the current user
     *
     * @param identifier The system ID of the current user
     * @return The Names of the other users
     */
 /*   public ArrayList<String> getOtherUserNames(int identifier) {
        ArrayList<String> otherUsers = new ArrayList<>();

        try {
            for (User user : getAll()) {
                Integer userID = user.getId();
                if (identifier != userID) {
                    otherUsers.add(user.getName());
                }
            }
        } catch (HibernateException hbe) {
            return null;
        }
        return otherUsers;
    }

    /**
     * Retrieves the system ID of a user with a given name
     *
     * @param name The name of the user
     * @return The system ID of a user with that name or zero if it fails
     */
/*    public int getIdByName(String name) throws HibernateException {
            List<User> users = getAll();
            for (User user : users) {
                Integer userID = user.getId();
                if (name.equalsIgnoreCase(user.getName())) {
                    return userID;
                }
            }

        return 0;
    }

    /**
     * Retrieves the ID of the administrator user
     *
     * @return The system ID of the administrator user
     */
 /*   public  int getAdminId() throws HibernateException {
        String role = "administrator";

            List<User> users = getAll();
            for (User user : users) {
                if (role.equalsIgnoreCase(user.getRole_name())) return user.getId();
            }
        return 0;
    }

    /**
     * Retrieves a user's personal name
     *
     * @param userId The unique identifier
     * @return The personal name
     */
 /*   public String getName(int userId) throws HibernateException {
            User user = get(userId);
            if (user == null) {
                return null;
            }
            return user.getName();
    }

    /**
     * Retrieves a user's assigned role
     *
     * @param userId The unique identifier
     * @return The assigned role value
     */
 /*   public String determineRole(int userId) throws HibernateException {
            User user = get(userId);
            return user.getRole_name();
    }

    /**
     * Updates a user's information in both the users and user_roles tables
     *
     * @param identifier The unique identifier
     * @param userName   The username part of the login credential
     * @param name       The name of the person
     * @param password   The password part of the login credential
     * @param rolename   The assigned role of the user
     * @return The unique identifier of the updated user record
     */
 /*   public int modify(int identifier, String userName, String name, String password, String rolename) throws HibernateException {
            User user = get(identifier);

            if (user == null) {
                return 0;
            }
            if ((checkUsername(userName)) &&(checkUsername(name))) { // userName is alright to use
                user.setUser_name(userName);
                user.setName(name);
                if (checkPassword(password)) {
                    user.setUser_pass(password);
                    if (checkRoleName(rolename)) {
                        user.setRole_name(rolename);
                        modify(user);
                    }
                }
            }

        return 0;
    }






    /**
     * Performs simple checks for an acceptable username
     *
     * @param username the proposed username
     * @return whether the username is acceptable
     */
 /*   private static boolean checkUsername(String username) {
        return ((2 < username.length() && (fieldLength >= username.length()))); // Require at least 3 characters in username
    }

    /**
     * Performs simple checks for an acceptable password
     *
     * @param password the proposed password
     * @return whether the password is acceptable
     */
/*    private static boolean checkPassword(String password) {
        return ((7 < password.length()) && (fieldLength >= password.length())); // Require at least 8 characters in password
    }

    /**
     * Determines whether a role name is valid
     *
     * @param rolename The role name to be tested
     * @return Whether or not the role name is valid
     */
 /*   private static boolean checkRoleName(String rolename) {
        ArrayList<String> definedRoles = new ArrayList<>();
        definedRoles.add("administrator");
        definedRoles.add("registered-user");
        definedRoles.add("guest");

        return (definedRoles.contains(rolename));
    }
    /**
     * Removes a user
     *
     * @param id ID of user to be removed
     */
 /*   public void remove(int id) throws HibernateException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }


    Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }
    */
}
