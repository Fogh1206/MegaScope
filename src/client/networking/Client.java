package client.networking;

import shared.NewRegisteredUser;
import shared.PropertyChangeSubject;
import shared.User;



public interface Client extends PropertyChangeSubject {
    void registerUser(NewRegisteredUser userToCreate);

    void login(User user);
    void getMovies();

}
