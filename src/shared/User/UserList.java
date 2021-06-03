package shared.User;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable {

    /**
     * Instance field
     */
    private ArrayList<User> users;

    /**
     * No argument constructor
     */
    public UserList() {
        users = new ArrayList<>();
    }

    /**
     * Adds user to arraylist users
     *
     * @param user
     */
    public void add(User user) {
        users.add(user);
    }

    /**
     * Get method for the index of users
     *
     * @param index
     * @return
     */
    public User get(int index) {
        return users.get(index);
    }

    /**
     * Method removes a user from arraylist users
     *
     * @param user
     */
    public void remove(User user) {
        users.remove(user);
    }

    /**
     * Method for getting the size of the arraylist
     *
     * @return users.size()
     */
    public int size() {
        return users.size();
    }

    /**
     * To string method
     *
     * @return
     */
    public String toString() {
        String rtnString = "Users = {\n\t";
        for (int i = 0; i < users.size(); i++) {
            rtnString += users.get(i).getUsername() + ", \n\t";
        }
        rtnString += "\n}";
        return rtnString;
    }

}
