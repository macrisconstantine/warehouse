package warehouse;

/**
 * User class used to store individual user information.
 */
public class User implements java.io.Serializable {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;

    /**
     * Default user constructor builds a default user any time the program loads.
     */
    public User()
    {
        username = "John";
        password = "default";
        firstName = "Johnathan";
        lastName = "Smith";
        email = "SmithJohn@yahoo.com";
        phone = "1234567890";
    }

    /**
     * Overloaded constructor allows for direct creation of new users with all information.
     * @param username sets a user's username
     * @param password sets a user's password
     * @param firstName sets user's first name
     * @param lastName sets user's last name
     * @param email sets user's email address
     * @param phone sets user's phone number
     */
    public User(String username, String password, String firstName, String lastName, String email, String phone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    // accesses username while maintaining encapsulation
    public String getUsername() {
        return username;
    }

    // accesses password while maintaining encapsulation
    public String getPassword() {
        return password;
    }

    @Override
    // toString used only for debugging
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
