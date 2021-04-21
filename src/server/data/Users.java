package server.data;

import shared.User;

import java.util.ArrayList;
import java.util.List;

public class Users implements UserInt{
    private List<User> users;

    public Users() {
        this.users = new ArrayList<>();
        users.add(new User("dorin", "123"));

    }

    @Override
    public String acceptUser(User user) {
        User u = findUser(user);
        if(u == null) return "User not found";
        if(!u.getPassword().equals(user.getPassword())) return "Incorrect password";
        return "OK";
    }

    private User findUser(User user) {
        for (User u : users) {
            if(u.getUsername().equals(user.getUsername())) {
                return u;
            }
        }
        return null;
    }
}
