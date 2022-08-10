package warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Login class used to generate and store users and password/user credentials in a pseudo-database
 */
public class Login implements java.io.Serializable {
    // currUser used in main frame to welcome the currently signed-in user
    private String currUser;
    private String username;
    private String password;

    // hashmap provides an easy way to check usernames together with matching passwords
    private final HashMap<String, String> passwords = new HashMap<>();

    // record of users stored in an arraylist
    private final ArrayList<User> users = new ArrayList<>();

    /**
     * Login constructor without parameters creates and adds a default user, and stores the credentials
     */
    public Login() {
        User defaultUser = new User();
        username = defaultUser.getUsername();
        password = defaultUser.getPassword();
        users.add(defaultUser);
        passwords.put(username, password);
    }

    /**
     *
     * @param username allows new login to set the username directly via parameter
     * @param password allows new login to set the username directly via parameter
     */
    public Login(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    /**
     * this function checks if a user already has credentials set up, and if not, adds user to user list
     * @param u takes a user as parameter and adds the user to the list of users
     * @throws WarehouseException in the case that a user has already registered
     */
    public void addUsers(User u) throws WarehouseException {
        if (!passwords.containsKey(u.getUsername()))
        {
            users.add(u);
            // calls update passwords function to make sure all credential matches align with users
            updatePasswords();
        }
        // notifies user if he is already registered
        else throw new WarehouseException("User already registered in system.");
    }

    /**
     * if there are more users than passwords, missing credentials are created for users that do not have
     */
    public void updatePasswords()
    {
        if (passwords.size()<users.size()) {
            for (User u : users) {
                if (!passwords.containsKey(u.getUsername()))
                    passwords.put(u.getUsername(), u.getPassword());
            }
        }
    }

    /**
     * this function is used to check for whether a user is registered with credentials
     * @return true if currently set user has credentials and matching password
     */
    public boolean checkCredentials()
    {
        return passwords.containsKey(username) && Objects.equals(password, passwords.get(username));
    }

    public String getCurrUser() {
        return currUser;
    }

    public void setCurrUser(String currUser) {
        this.currUser = currUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumOfUsers()
    {
        return users.size();
    }

    @Override
    // simple toString method only for debugging purposes, not app functionality
    // (although in the future I might like to add user management functionality, which may utilize this)
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", \npassword='" + password + '\'' +
                ", \npasswords=" + passwords +
                ", \nusers=" + users +
                ", \ncurrUser=" + currUser+
                '}';
    }
}

