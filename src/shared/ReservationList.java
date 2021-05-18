package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ReservationList implements Serializable {

    private ArrayList<Reservation> reservations;

    public ReservationList(){
        reservations = new ArrayList<>();
    }

    public void add(Reservation reservation){
        reservations.add(reservation);
    }

    public Reservation get(int index){
        return reservations.get(index);
    }

    public void remove(Reservation reservation){
        reservations.remove(reservation);
    }

    public int size(){
        return reservations.size();
    }

}
