package client.model;

import shared.Movie;
import shared.NewRegisteredUser;
import shared.PropertyChangeSubject;

public interface UserModel extends PropertyChangeSubject {
    void register(NewRegisteredUser user);
    void login(String username, String password);
    void deactivateClient();
    void getMovies();
    void saveNewInfo(NewRegisteredUser user);
    void getUsers();
    void addMovie(Movie movie);

}
