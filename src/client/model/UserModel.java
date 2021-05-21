package client.model;

import shared.*;

public interface UserModel extends PropertyChangeSubject {
    void register(User user);

    void login(String username, String password);

    void deactivateClient();

    void getMovies();

    void saveNewInfo(User user);

    void getUsers();

    void addMovie(Show show);

    void editMovie(Show show);

    void removeMovie(Show show);

    void getReservation(Show show);

    void confirmSeats(ReservationList reservationList);

    void getUserReservations(User user);

    void cancelReservation(UserReservationInfo reservation_id);

    void adminConfirmSeats(ReservationList reservationList);
}
