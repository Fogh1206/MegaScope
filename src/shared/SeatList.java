package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class SeatList implements Serializable {

    private ArrayList<Seat> seats;

    public SeatList() {
        seats = new ArrayList<>();
    }

    public void add(Seat seat) {
        seats.add(seat);
    }

    public Seat get(int index) {
        return seats.get(index);
    }

    public void remove(Seat seat) {
        seats.remove(seat);
    }

    public int size() {
        return seats.size();
    }
}