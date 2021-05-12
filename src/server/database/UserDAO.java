package server.database;

import shared.Movie;
import shared.NewRegisteredUser;
import shared.Reservation;

import java.util.ArrayList;

public interface UserDAO {
    NewRegisteredUser validateUser(int id, String username, String password);

    NewRegisteredUser createUser(NewRegisteredUser user);

    ArrayList<NewRegisteredUser> getAllUsers();

    ArrayList<Movie> getAllMovies();

    NewRegisteredUser saveNewInfo(NewRegisteredUser user);

    ArrayList<Movie> addMovie(Movie movie);

    ArrayList<Movie> removeMovie(Movie movie);

    ArrayList<Movie> editMovie(Movie movie);

    ArrayList<String> getReservations(Movie movie);

    Reservation reserveMovie(Reservation reservation);
}
