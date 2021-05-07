package client.model;

import client.networking.Client;
import shared.Movie;
import shared.NewRegisteredUser;
import shared.Request;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model implements UserModel {
    private Client client;
    private NewRegisteredUser loggedUser;
    private PropertyChangeSupport support;

    public Model(Client client) {
        this.client = client;
        support = new PropertyChangeSupport(this);

        client.addPropertyChangeListener(EventType.GETMOVIES_RESULT.toString(),
                this::onGetMoviesResult);
        client.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(),
                this::onLoginResult);
        client.addPropertyChangeListener(EventType.REGISTER_RESULT.toString(),
                this::onRegisterResult);
        client.addPropertyChangeListener(EventType.GETUSER_RESULT.toString(),
                this::onGetUserResult);
        client.addPropertyChangeListener(EventType.ADDMOVIE_RESULT.toString(),
                this::onMoviesChanged);
        client.addPropertyChangeListener(EventType.EDITMOVIE_RESULT.toString(),
                this::onMoviesChanged);
        client.addPropertyChangeListener(EventType.REMOVEMOVIE_RESULT.toString(),
                this::onMoviesChanged);

    }

    private void onGetUserResult(PropertyChangeEvent event) {
        ArrayList<NewRegisteredUser> list = (ArrayList<NewRegisteredUser>) event
                .getNewValue();
        support.firePropertyChange("Users Result", null, list);
        System.out.println(list.get(0));
    }

    @Override
    public void getUsers() {
        client.getUsers();
    }

    @Override
    public void addMovie(Movie movie) {
        System.out.println("Added movie : " + movie.toString());
        client.addMovie(movie);
    }

    private void onGetMoviesResult(PropertyChangeEvent event) {
        ArrayList<Movie> list = (ArrayList<Movie>) event.getNewValue();
        support.firePropertyChange("Movie Result", null, list);
    }

    private void onLoginResult(PropertyChangeEvent event) {
        NewRegisteredUser loginResult = (NewRegisteredUser) event.getNewValue();
        support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null,
                loginResult);
    }

    private void onRegisterResult(PropertyChangeEvent event) {
        String registerResult = (String) event.getNewValue();
        System.out.println(registerResult);
    }

    private void onMoviesChanged(PropertyChangeEvent event) {

        ArrayList<Movie> list = (ArrayList<Movie>) event.getNewValue();
        support.firePropertyChange("Movie Result", null, list);

    }

    @Override
    public void removeMovie(Movie movie) {
        client.removeMovie(movie);
    }

    @Override
    public void addPropertyChangeListener(String name,
                                          PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name,
                                             PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }

    @Override
    public void saveNewInfo(NewRegisteredUser user) {
        client.saveNewInfo(user);
    }

    @Override
    public void saveNewInfo(Movie movie) {
        client.saveMovieInfo(movie);
    }

    @Override
    public void register(NewRegisteredUser user) {

        client.registerUser(user);
        System.out.println(
                "Register :>>>> " + "username : " + user.getUsername() + " password:  "
                        + user.getPassword());
    }

    @Override
    public void login(String username, String password) {
        loggedUser = new NewRegisteredUser(username, password);
        client.login(loggedUser);
    }

    @Override
    public void deactivateClient() {
        client.deactivateClient();
    }

    @Override
    public void getMovies() {
        client.getMovies();
    }
}
