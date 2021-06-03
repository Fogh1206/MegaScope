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

    /**
     * One-argument constructor.
     *
     * @param client
     */
    public Model(ClientImpl client) {
        this.client = client;
        support = new PropertyChangeSupport(this);

        client.addPropertyChangeListener(EventType.GETMOVIES_RESULT.toString(), this::onGetMoviesResult);

        client.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::onLoginResult);

        client.addPropertyChangeListener(EventType.REGISTER_RESULT.toString(), this::onRegisterResult);
        client.addPropertyChangeListener(EventType.REGISTERFAIL_RESULT.toString(), this::onFailedRegister);

        client.addPropertyChangeListener(EventType.ADDMOVIE_RESULT.toString(), this::onMoviesChanged);
        client.addPropertyChangeListener(EventType.EDITMOVIE_RESULT.toString(), this::onMoviesChanged);
        client.addPropertyChangeListener(EventType.REMOVEMOVIE_RESULT.toString(), this::onMoviesChanged);

        client.addPropertyChangeListener(EventType.GETRESERVATIONS_RESULT.toString(), this::onGetReservation);
        client.addPropertyChangeListener(EventType.RESERVEMOVIE_RESULT.toString(), this::onReserveShow);

        client.addPropertyChangeListener(EventType.SAVENEWINFO_RESULT.toString(), this::onNewInfo);
        client.addPropertyChangeListener(EventType.SAVENEWINFOFAIL_RESULT.toString(), this::onFailedSaveNewInfo);

        client.addPropertyChangeListener(EventType.GETUSERRESERVATIONS_RESULT.toString(), this::onGetUserReservations);
        client.addPropertyChangeListener(EventType.REMOVERESERVATION_RESULT.toString(), this::onGetUserReservations);

        client.addPropertyChangeListener(EventType.ADMINBLOCKSEATS_RESULT.toString(), this::onGetAdminSeats);
        client.addPropertyChangeListener(EventType.GETADMINSEATS_RESULT.toString(), this::onGetAdminSeats);

        client.addPropertyChangeListener(EventType.GETUSER_RESULT.toString(), this::onGetUserResult);
        client.addPropertyChangeListener(EventType.CHANGEUSERSTATUS_RESULT.toString(), this::onGetUserResult);
        client.addPropertyChangeListener(EventType.LOGINFAIL_RESULT.toString(), this::onLoginFail);

        client.addPropertyChangeListener(EventType.GETMOVIESFORADD_RESULT.toString(), this::onGetMoviesForAddResult);
    }

    private void onGetMoviesForAddResult(PropertyChangeEvent event) {
        System.out.println("Model: onGetMoviesForAddResult");
        MovieShowsList list = (MovieShowsList) event.getNewValue();
        support.firePropertyChange(EventType.GETMOVIESFORADD_RESULT.toString(), null, list);
    }

    /**
     * Event that is received from server with updated {@link SeatList} object of available and unavailable seats.
     *
     * @param event
     */
    private void onGetAdminSeats(PropertyChangeEvent event) {
        System.out.println("Model: onGetAdminSeats");
        SeatList list = (SeatList) event.getNewValue();
        support.firePropertyChange(EventType.GETADMINSEATS_RESULT.toString(), null, list);
    }

    /**
     * Event that tells {@link client.viewmodel.login.LoginViewModel} the user has inserted wrong login input.
     *
     * @param event
     */
    private void onLoginFail(PropertyChangeEvent event) {
        System.out.println("Model: onLoginFail");
        support.firePropertyChange(EventType.LOGINFAIL_RESULT.toString(), null, null);
    }


    /**
     * Event that tells {@link client.viewmodel.user.UserProfileViewModel} the username is already occupied.
     *
     * @param event
     */
    private void onFailedSaveNewInfo(PropertyChangeEvent event) {
        System.out.println("Model: onFailedSaveNewInfo");
        support.firePropertyChange(EventType.SAVENEWINFOFAIL_RESULT.toString(), null, null);
    }

    /**
     * Event that tells {@link client.viewmodel.registration.RegisterViewModel} the username is already occupied.
     *
     * @param propertyChangeEvent
     */
    private void onFailedRegister(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("Model: onFailedRegister");
        support.firePropertyChange(EventType.REGISTERFAIL_RESULT.toString(), null, null);
    }

    /**
     * Event that returns updated {@link User} to the {@link client.viewmodel.user.UserProfileViewModel}
     *
     * @param propertyChangeEvent
     */
    private void onNewInfo(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("Model: onNewInfo");
        User user = (User) propertyChangeEvent.getNewValue();
        support.firePropertyChange(EventType.SAVENEWINFO_RESULT.toString(), null, user);
    }

    /**
     * Event that returns a {@link ReservationList} object from server with updated reservations.
     *
     * @param propertyChangeEvent
     */
    private void onReserveShow(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("Model: onReserveShow");
        ReservationList reservations = (ReservationList) propertyChangeEvent.getNewValue();
        ArrayList<String> idList = new ArrayList<>();
        for (int i = 0; i < reservations.size(); i++) {
            idList.add(String.valueOf(reservations.get(i).getSeat_no()));
        }
        if (!reservations.isFailed()) {
            support.firePropertyChange(EventType.GETRESERVATIONS_RESULT.toString(), null, idList);
        } else {
            support.firePropertyChange(EventType.GETRESERVATIONSFAIL_RESULT.toString(), null, idList);
        }
    }

    /**
     * Event returns updated {@link ArrayList<String>} with reservations from server.
     *
     * @param event
     */
    private void onGetReservation(PropertyChangeEvent event) {
        System.out.println("Model: onGetReservation");
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();
        support.firePropertyChange(EventType.GETRESERVATIONS_RESULT.toString(), null, list);
    }

    /**
     * Event returns updated {@link UserReservationInfoList} to
     * {@link client.viewmodel.frontPage.UserReservationViewModel} with updated reservations for the logged in user.
     *
     * @param event
     */
    private void onGetUserReservations(PropertyChangeEvent event) {
        System.out.println("Model: onGetUserReservations");
        UserReservationInfoList reservations = (UserReservationInfoList) event.getNewValue();
        support.firePropertyChange("Reservations result", null, reservations);
    }

    /**
     * Event returns updated {@link UserList} to the {@link client.viewmodel.admin}.
     *
     * @param event
     */
    private void onGetUserResult(PropertyChangeEvent event) {
        System.out.println("Model: onGetUserResult");
        UserList users = (UserList) event.getNewValue();
        support.firePropertyChange(EventType.GETUSER_RESULT.toString(), null, users);
    }

    /**
     * Event returns updated {@link MovieShowsList} to {@link client.viewmodel.frontPage.UserFrontPageViewModel}.
     *
     * @param event
     */
    private void onGetMoviesResult(PropertyChangeEvent event) {
        System.out.println("Model: onGetMoviesResult");
        MovieShowsList list = (MovieShowsList) event.getNewValue();
        System.out.println(list.getSize());
        support.firePropertyChange(EventType.GETMOVIES_RESULT.toString(), null, list);

    }

    /**
     * Event returns {@link User} to {@link client.viewmodel.login.LoginViewModel} with the successfully logged in {@link User}.
     *
     * @param event
     */
    private void onLoginResult(PropertyChangeEvent event) {
        System.out.println("Model: onLoginResult");
        User loginResult = (User) event.getNewValue();
        support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null, loginResult);
    }

    /**
     * Event returns {@link User} to {@link client.viewmodel.registration.RegisterViewModel} with the successfully registered {@link User}.
     *
     * @param event
     */
    private void onRegisterResult(PropertyChangeEvent event) {
        System.out.println("Model: onRegisterResult");
        User user = (User) event.getNewValue();
        support.firePropertyChange(EventType.REGISTER_RESULT.toString(), null, user);
    }

    /**
     * Event returns updated {@link MovieShowsList} to {@link client.viewmodel.frontPage.UserFrontPageViewModel}.
     *
     * @param event
     */
    private void onMoviesChanged(PropertyChangeEvent event) {
        System.out.println("Model: onMoviesChanged");
        MovieShowsList list = (MovieShowsList) event.getNewValue();
        support.firePropertyChange(EventType.GETMOVIES_RESULT.toString(), null, list);
    }

    @Override
    public void editMovie(MovieShow movieShow) {
        client.editMovie(movieShow);
    }

    @Override
    public void getUsers() {
        client.getUsers();
    }

    @Override
    public void addMovie(MovieShow movieShow) {
        client.addMovie(movieShow);
    }

    @Override
    public void removeMovie(MovieShow movieShow) {
        client.removeMovie(movieShow);
    }

    @Override
    public void getReservation(MovieShow movieShow) {
        client.getReservation(movieShow);
    }

    @Override
    public void confirmSeats(ReservationList reservationList) {
        client.confirmSeats(reservationList);
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
        client.login(new User(username, password));
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

    @Override
    public void getMoviesForAdd() {
        client.getMoviesForAdd();
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }
}
