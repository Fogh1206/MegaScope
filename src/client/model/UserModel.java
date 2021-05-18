package client.model;

import shared.*;

import java.util.ArrayList;

public interface UserModel extends PropertyChangeSubject {
    void register(User user);
    void login(String username, String password);
    void deactivateClient();
    void getMovies();
    void saveNewInfo(User user);
    void getUsers();
    void addMovie(Show show);
    void editMovie(Show show);
    void removeMovie(Show show);
    void getReservation(Show show);
  void confirmSeats(ReservationList reservationList);
}
