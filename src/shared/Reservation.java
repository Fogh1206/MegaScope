package shared;

import java.io.Serializable;

public class Reservation implements Serializable {

    private int reservation_id;
    private int seat_no;
    private Movie movie;
    private NewRegisteredUser user;

    public Reservation(int reservation_id, int seat_no, Movie movie, NewRegisteredUser user) {
        this.reservation_id = reservation_id;
        this.seat_no = seat_no;
        this.movie = movie;
        this.user = user;
    }


    public int getReservation_id() {
        return reservation_id;
    }

    public int getSeat_no() {
        return seat_no;
    }

    public Movie getMovie() {
        return movie;
    }

    public NewRegisteredUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", seat_no=" + seat_no +
                ", movie=" + movie +
                ", user=" + user +
                '}';
    }
}
