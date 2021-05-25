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

    public void set(Seat seat){
        System.out.println("Mijarmo");
        int j=0;
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getId()==seat.getId()){
                seats.set(i,seat) ;
                System.out.println("Setting");
                j++;
            }
        }
        if (j==0){
            System.out.println("Adding");
            add(seat);
        }
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