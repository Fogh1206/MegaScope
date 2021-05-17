package shared;

import java.io.Serializable;

public class Reservation implements Serializable {

    private int reservation_id;
    private int seat_no;
    private int show_id;
    private int user_id;

    public Reservation(int reservation_id, int seat_no, int show_id, int user_id) {
        this.reservation_id = reservation_id;
        this.seat_no = seat_no;
        this.show_id = show_id;
        this.user_id = user_id;
    }

    public Reservation(int seat_no, int show_id, int user_id)
    {
        this.seat_no = seat_no;
        this.show_id = show_id;
        this.user_id = user_id;
    }



    public int getReservation_id() {
        return reservation_id;
    }

    public int getSeat_no() {
        return seat_no;
    }


    public int getShow_id() {
        return show_id;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", seat_no=" + seat_no +
                ", show=" + show_id +
                ", user=" + user_id +
                '}';
    }
}
