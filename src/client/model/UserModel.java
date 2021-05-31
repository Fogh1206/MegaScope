package client.model;

import shared.*;

public interface UserModel extends PropertyChangeSubject {
    void register(User user);

    void login(String username, String password);

    void deactivateClient();

    void getMovies();

    void saveNewInfo(User user);

    void getUsers();

    void addMovie(MovieShow movieShow);

    void editMovie(MovieShow movieShow);

    void removeMovie(MovieShow movieShow);

    void getReservation(MovieShow movieShow);

    void confirmSeats(ReservationList reservationList);

    void getUserReservations(User user);

    void cancelReservation(UserReservationInfo reservation_id);

    void adminConfirmSeats(SeatList seatsID);

    void getAdminSeats();

    void changeUserStatus(User user);

    void getMoviesForAdd();
}
