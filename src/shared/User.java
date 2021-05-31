package shared;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * Instance field
     */
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String userType;
    private boolean banned;

    /**
     * Constructor
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param phoneNumber
     * @param userType
     * @param banned
     */
    public User(int id, String firstName, String lastName, String username,
                String password, String phoneNumber, String userType, boolean banned) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.banned = banned;
    }

    /**
     * Constructor which sets the id to 0
     *
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param phoneNumber
     * @param userType
     * @param banned
     */
    public User(String firstName, String lastName, String username,
                String password, String phoneNumber, String userType, boolean banned) {

        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.banned = banned;
    }

    /**
     * Constructor with 2 arguments
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.id = 0;
        this.firstName = null;
        this.lastName = null;
        this.username = username;
        this.password = password;
        this.phoneNumber = null;
    }

    /**
     * Get method for banned
     *
     * @return banned
     */
    public boolean getBanned() {
        return banned;
    }

    /**
     * Get method for phone number
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get method for first name
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get method for last name
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get method for username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get method for password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get method for user type
     *
     * @return userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Get method for id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set method for banned
     *
     * @param banned
     */
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    /**
     * To string method
     *
     * @return
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\'' + ", username='" + username + '\''
                + ", password='" + password + '\'' + ", phoneNumber='" + phoneNumber
                + '\'' + ", userType='" + userType + '\'' + ", banned='" + banned + '\''
                + '}';
    }

    /**
     * Set method for phone number
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
