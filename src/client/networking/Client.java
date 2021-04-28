package client.networking;

import shared.NewRegisteredUser;
import shared.PropertyChangeSubject;
import shared.Request;
import shared.User;



public interface Client extends PropertyChangeSubject {
    void registerUser(NewRegisteredUser userToCreate);

    void login(User user);
    void getMovies();

  boolean isRunning();
  void receive(Request req);
  void deactivateClient();

}
