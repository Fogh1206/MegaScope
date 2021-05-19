package shared;

import java.util.ArrayList;

public class UserReservationInfo {

    private int reservation_id;
    private String name;
    private String time_show;
    private String date_show;
    private int seat_id;

    public UserReservationInfo(ArrayList<String> details){
        reservation_id = Integer.valueOf(details.get(0));
        name = details.get(1);
        time_show = details.get(2);
        date_show = details.get(3);
        seat_id = Integer.valueOf(details.get(4));
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

    public String toString(){

        return reservation_id + " " + name + " " + time_show + " " + date_show + " " + seat_id + ":";

    }

}
