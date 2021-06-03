package client.networking;

import shared.*;
import shared.MovieShow.MovieShow;
import shared.Reservation.ReservationList;
import shared.Seat.SeatList;
import shared.User.User;
import shared.UserReservationInfo.UserReservationInfo;

public interface ClientImpl extends PropertyChangeSubject {
    void registerUser(User userToCreate);

    void login(User user);

    void getMovies();

    void saveNewInfo(User user);

    void getUserReservations(User user);

    boolean isRunning();

    void receive(Request req);

    void deactivateClient();

    void getUsers();

    void addMovie(MovieShow movieShow);

    void editMovie(MovieShow movieShow);

    void removeMovie(MovieShow movieShow);

    void getReservation(MovieShow movieShow);

    void confirmSeats(ReservationList reservationList);

    void cancelReservation(UserReservationInfo userReservationInfo);

    void adminConfirmSeats(SeatList seatList);

    void getAdminSeats();

    void changeUserStatus(User user);

    void getMoviesForAdd();
}
