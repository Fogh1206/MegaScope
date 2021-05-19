package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class UserReservationInfo implements Serializable {

    private int reservation_id;
    private String name;
    private String time_show;
    private String date_show;
    private int seat_id;

    public UserReservationInfo(int reservation_id, String name, String time_show, String date_show, int seat_id){
        this.reservation_id=reservation_id;
        this.name = name;
        this.time_show=time_show;
        this.date_show = date_show;
        this.seat_id = seat_id;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public String getName() {
        return name;
    }

    public String getTime_show() {
        return time_show;
    }

    public String getDate_show() {
        return date_show;
    }

    public int getSeat_id() {
        return seat_id;
    }

}
