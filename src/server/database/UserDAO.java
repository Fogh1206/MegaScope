package server.database;

import shared.NewRegisteredUser;
import shared.User;

import java.util.ArrayList;

public interface UserDAO {
    boolean validateUser(String username,String password);
    NewRegisteredUser createUser( String firstName, String lastName, String username, String password,String phoneNumber,String birthday);
    ArrayList<User> getAllUsers();
}
