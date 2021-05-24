package client.model;

import client.networking.ClientImpl;
import shared.*;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model implements UserModel {
    private ClientImpl client;
    private User loggedUser;
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
        client.addPropertyChangeListener(EventType.SAVENEWINFO_RESULT.toString(),
                this::onNewInfo);
        client.addPropertyChangeListener(EventType.REGISTERFAIL_RESULT.toString(),
                this::onFailedRegister);
        client.addPropertyChangeListener(EventType.GETUSERRESERVATIONS_RESULT.toString(),
                this::onGetUserReservations);
        client.addPropertyChangeListener(EventType.REMOVERESERVATION_RESULT.toString(),
                this::onGetUserReservations);
        client.addPropertyChangeListener(EventType.SAVENEWINFOFAIL_RESULT.toString(),
                this::onFailedSaveNewInfo);
        client.addPropertyChangeListener(EventType.ADMINBLOCKSEATS_RESULT.toString(),
                this::onGetAdminSeats);
        client.addPropertyChangeListener(EventType.GETADMINSEATS_RESULT.toString(),
                this::onGetAdminSeats);
        client.addPropertyChangeListener(EventType.GETUSER_RESULT.toString(),
                this::onGetUserResult);
        client.addPropertyChangeListener(EventType.CHANGEUSERSTATUS_RESULT.toString(),
                this::onGetUserResult);
    }

    private void onGetAdminSeats(PropertyChangeEvent event) {
        System.out.println("Model: onGetAdminSeats");
        SeatList list = (SeatList) event.getNewValue();
        support.firePropertyChange(EventType.GETADMINSEATS_RESULT.toString(), null, list);
    }


    private void onFailedSaveNewInfo(PropertyChangeEvent event) {
        support.firePropertyChange(EventType.SAVENEWINFOFAIL_RESULT.toString(), null, null);
    }

    private void onFailedRegister(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(EventType.REGISTERFAIL_RESULT.toString(), null, null);
    }

    private void onNewInfo(PropertyChangeEvent propertyChangeEvent) {
        User user = (User) propertyChangeEvent.getNewValue();
        System.out.println(user);
        support.firePropertyChange(EventType.SAVENEWINFO_RESULT.toString(), null, user);
    }

    private void onReserveShow(PropertyChangeEvent propertyChangeEvent) {

        ReservationList reservations = (ReservationList) propertyChangeEvent.getNewValue();
        ArrayList<String> whatever = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++) {
            whatever.add(String.valueOf(reservations.get(i).getSeat_no()));
        }
        support.firePropertyChange(EventType.GETRESERVATIONS_RESULT.toString(), null, whatever);
    }

    private void onGetReservation(PropertyChangeEvent event) {
        System.out.println("Model: onGetReservation");
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();
        support.firePropertyChange(EventType.GETRESERVATIONS_RESULT.toString(), null, list);
    }

    private void onGetUserReservations(PropertyChangeEvent event) {
        System.out.println("Model: onGetUserReservations");
        ArrayList<UserReservationInfo> reservations = (ArrayList<UserReservationInfo>) event.getNewValue();
        support.firePropertyChange("Reservations result", null, reservations);
    }

    private void onGetUserResult(PropertyChangeEvent event) {
        UserList users = (UserList) event.getNewValue();
        support.firePropertyChange("Users Result", null, users);
    }

    private void onGetMoviesResult(PropertyChangeEvent event) {
        ArrayList<Show> list = (ArrayList<Show>) event.getNewValue();
        support.firePropertyChange("Movie Result", null, list);
    }

    private void onLoginResult(PropertyChangeEvent event) {
        System.out.println("Karamba 2");
        User loginResult = (User) event.getNewValue();
        support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null,
                loginResult);
    }

    private void onRegisterResult(PropertyChangeEvent event) {
        User user = (User) event.getNewValue();
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

    @Override
    public void confirmSeats(ReservationList reservationList) {
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
    public void saveNewInfo(User user) {
        client.saveNewInfo(user);
    }

    @Override
    public void register(User user) {
        client.registerUser(user);
    }

    @Override
    public void login(String username, String password) {
        loggedUser = new User(username, password);
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

    @Override
    public void getUserReservations(User user) {
        client.getUserReservations(user);
    }

    @Override
    public void cancelReservation(UserReservationInfo userReservationInfo) {
        client.cancelReservation(userReservationInfo);
    }

    @Override
    public void adminConfirmSeats(SeatList seatList) {
        client.adminConfirmSeats(seatList);
    }

    @Override
    public void getAdminSeats() {
        client.getAdminSeats();
    }

    @Override
    public void changeUserStatus(User user) {
        client.changeUserStatus(user);
    }
}
