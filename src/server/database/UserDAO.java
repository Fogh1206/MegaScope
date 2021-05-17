package server.database;

import shared.Show;
import shared.NewRegisteredUser;
import shared.Reservation;

import java.util.ArrayList;

public interface UserDAO {
    NewRegisteredUser validateUser(int id, String username, String password);

    NewRegisteredUser createUser(NewRegisteredUser user);

    ArrayList<NewRegisteredUser> getAllUsers();

    ArrayList<Show> getAllMovies();

    NewRegisteredUser saveNewInfo(NewRegisteredUser user);

    ArrayList<Show> addMovie(Show show);

    ArrayList<Show> removeMovie(Show show);

    ArrayList<Show> editMovie(Show show);

    ArrayList<String> getReservations(Show show);

    ArrayList<Reservation> reserveMovie(ArrayList<Reservation> list);


}
