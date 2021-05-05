package server.database;

import shared.Movie;
import shared.NewRegisteredUser;
import shared.User;

import java.util.ArrayList;

public interface UserDAO {
    NewRegisteredUser validateUser(int id,String username,String password);
    NewRegisteredUser createUser( String firstName, String lastName, String username, String password,String phoneNumber);
    ArrayList<User> getAllUsers();
    ArrayList<Movie> getAllMovies();
    NewRegisteredUser saveNewInfo(int id,String firstName, String lastName, String username, String password,String phoneNumber);
    boolean isAdmin(int id);
}
