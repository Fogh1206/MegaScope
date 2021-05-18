package server.database;

import shared.*;

import java.util.ArrayList;

public interface UserDAO {
    User validateUser(int id, String username, String password);

    User createUser(User user);

    UserList getAllUsers();

    ArrayList<Show> getAllMovies();

    User saveNewInfo(User user);

    ArrayList<Show> addMovie(Show show);

    ArrayList<Show> removeMovie(Show show);

    ArrayList<Show> editMovie(Show show);

    ArrayList<String> getReservations(Show show);

    ReservationList reserveMovie(ReservationList list);


}
