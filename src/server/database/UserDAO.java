package server.database;

import shared.MovieShow.MovieShow;
import shared.MovieShow.MovieShowsList;
import shared.Reservation.ReservationList;
import shared.Seat.SeatList;
import shared.User.User;
import shared.User.UserList;
import shared.UserReservationInfo.UserReservationInfo;
import shared.UserReservationInfo.UserReservationInfoList;

import java.util.ArrayList;

public interface UserDAO {
    User validateUser(String username, String password);

    User registerUser(User user);

    UserReservationInfoList getUserReservation(User user);

    UserList getAllUsers();

    MovieShowsList getAllMovies();

    User saveNewInfo(User user);

    MovieShowsList addMovie(MovieShow movieShow);

    MovieShowsList removeMovie(MovieShow movieShow);

    MovieShowsList editMovie(MovieShow movieShow);

    ArrayList<String> getReservations(MovieShow movieShow);

    ReservationList reserveMovie(ReservationList list);

    UserReservationInfoList cancelReservation(UserReservationInfo userReservationInfo);

    SeatList adminConfirmSeats(SeatList arg);

    SeatList getAdminSeats();

    UserList changeUserStatus(User user);

    MovieShowsList getAllMoviesUnique();
}
