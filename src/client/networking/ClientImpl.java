package client.networking;

import shared.*;

import java.util.ArrayList;

public interface ClientImpl extends PropertyChangeSubject
{
  void registerUser(User userToCreate);
  void login(User user);
  void getMovies();
  void saveNewInfo(User user);
  boolean isRunning();
  void receive(Request req);
  void deactivateClient();
  void getUsers();
  void addMovie(Show show);
  void editMovie(Show show);
  void removeMovie(Show show);

  void getReservation(Show show);
  void confirmSeats(ReservationList reservationList);
}
