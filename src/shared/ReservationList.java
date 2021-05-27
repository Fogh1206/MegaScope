package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ReservationList implements Serializable {

    private boolean failed;
    private ArrayList<Reservation> reservations;

    public ReservationList(){
        this.failed = false;
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

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}
