package client.networking;

import shared.*;

import java.util.ArrayList;

public interface ClientImpl extends PropertyChangeSubject
{
  void registerUser(NewRegisteredUser userToCreate);
  void login(NewRegisteredUser user);
  void getMovies();
  void saveNewInfo(NewRegisteredUser user);
  boolean isRunning();
  void receive(Request req);
  void deactivateClient();
  void getUsers();
  void addMovie(Show show);
  void editMovie(Show show);
  void removeMovie(Show show);

  void getReservation(Show show);
  void confirmSeats(ArrayList<Reservation> reservationList);
}
