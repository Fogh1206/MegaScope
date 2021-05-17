package client.model;

import client.networking.ClientImpl;
import shared.Reservation;
import shared.Show;
import shared.NewRegisteredUser;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model implements UserModel {
    private ClientImpl client;
    private NewRegisteredUser loggedUser;
    private PropertyChangeSupport support;

    public Model(ClientImpl client) {
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
        client.addPropertyChangeListener(EventType.GETRESERVATIONS_RESULT.toString(),
                this::onGetReservation);
        client.addPropertyChangeListener(EventType.RESERVEMOVIE_RESULT.toString(),
            this::onReserveShow);

    }

    private void onReserveShow(PropertyChangeEvent propertyChangeEvent)
    {

        ArrayList<Reservation> reservations = (ArrayList<Reservation>) propertyChangeEvent.getNewValue();
        ArrayList<String> whatever = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++)
        {
            whatever.add(String.valueOf(reservations.get(i).getSeat_no()));
        }
        support.firePropertyChange(EventType.GETRESERVATIONS_RESULT.toString(), null, whatever);
        // support.firePropertyChange(EventType.RESERVEMOVIE_RESULT.toString(),null,reservations);
    }

    private void onGetReservation(PropertyChangeEvent event) {
        System.out.println("Model: onGetReservation");
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();
        support.firePropertyChange(EventType.GETRESERVATIONS_RESULT.toString(), null, list);
    }

    private void onGetUserResult(PropertyChangeEvent event) {
        ArrayList<NewRegisteredUser> list = (ArrayList<NewRegisteredUser>) event.getNewValue();
        support.firePropertyChange("Users Result", null, list);
    }

    private void onGetMoviesResult(PropertyChangeEvent event) {
        ArrayList<Show> list = (ArrayList<Show>) event.getNewValue();
        support.firePropertyChange("Movie Result", null, list);
    }

    private void onLoginResult(PropertyChangeEvent event) {
        NewRegisteredUser loginResult = (NewRegisteredUser) event.getNewValue();
        support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null,
                loginResult);
    }

    private void onRegisterResult(PropertyChangeEvent event) {
        NewRegisteredUser user = (NewRegisteredUser) event.getNewValue();
        support.firePropertyChange(EventType.REGISTER_RESULT.toString(), null, user);

    }

    private void onMoviesChanged(PropertyChangeEvent event) {
        ArrayList<Show> list = (ArrayList<Show>) event.getNewValue();
        support.firePropertyChange("Movie Result", null, list);
    }

    @Override
    public void editMovie(Show show) {
        client.editMovie(show);
    }

    @Override
    public void getUsers() {
        client.getUsers();
    }

    @Override
    public void addMovie(Show show) {
        System.out.println("Added movie : " + show.toString());
        client.addMovie(show);
    }


    @Override
    public void removeMovie(Show show) {
        client.removeMovie(show);
    }

    @Override
    public void getReservation(Show show) {
        client.getReservation(show);
    }

    @Override public void confirmSeats(ArrayList<Reservation> reservationList)
    {
        client.confirmSeats(reservationList);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }

    @Override
    public void saveNewInfo(NewRegisteredUser user) {
        client.saveNewInfo(user);
    }


    @Override
    public void register(NewRegisteredUser user) {
        client.registerUser(user);
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
