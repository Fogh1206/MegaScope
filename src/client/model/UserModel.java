package client.model;

import shared.*;
import shared.MovieShow.MovieShow;
import shared.Reservation.ReservationList;
import shared.Seat.SeatList;
import shared.User.User;
import shared.UserReservationInfo.UserReservationInfo;

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
