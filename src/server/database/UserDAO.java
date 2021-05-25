package server.database;

import shared.*;

import java.util.ArrayList;

public interface UserDAO {
    User validateUser(int id, String username, String password);

    User registerUser(User user);

    ArrayList<UserReservationInfo> getUserReservation(User user);

    UserList getAllUsers();

    ShowsList getAllMovies();

    User saveNewInfo(User user);

    ShowsList addMovie(Show show);

    ShowsList removeMovie(Show show);

    ShowsList editMovie(Show show);

    ArrayList<String> getReservations(Show show);

    ReservationList reserveMovie(ReservationList list);


    ArrayList<UserReservationInfo> cancelReservation(UserReservationInfo userReservationInfo);

    SeatList adminConfirmSeats(SeatList arg);

    SeatList getAdminSeats();

    UserList changeUserStatus(User user);
}
