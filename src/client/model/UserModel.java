package client.model;

import shared.Reservation;
import shared.Show;
import shared.NewRegisteredUser;
import shared.PropertyChangeSubject;

import java.util.ArrayList;

public interface UserModel extends PropertyChangeSubject {
    void register(NewRegisteredUser user);
    void login(String username, String password);
    void deactivateClient();
    void getMovies();
    void saveNewInfo(NewRegisteredUser user);
    void getUsers();
    void addMovie(Show show);
    void editMovie(Show show);
    void removeMovie(Show show);
    void getReservation(Show show);
  void confirmSeats(ArrayList<Reservation> reservationList);
}
