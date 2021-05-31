package client.networking;

import shared.*;

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
