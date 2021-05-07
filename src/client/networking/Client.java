package client.networking;

import shared.Movie;
import shared.NewRegisteredUser;
import shared.PropertyChangeSubject;
import shared.Request;

public interface Client extends PropertyChangeSubject
{
  void registerUser(NewRegisteredUser userToCreate);
  void login(NewRegisteredUser user);
  void getMovies();
  void saveNewInfo(NewRegisteredUser user);
  boolean isRunning();
  void receive(Request req);
  void deactivateClient();
  void getUsers();
  void addMovie(Movie movie);
  void editMovie(Movie movie);
  void removeMovie(Movie movie);

}
