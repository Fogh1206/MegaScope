package shared.Reservation;

import java.io.Serializable;
import java.util.ArrayList;

public class ReservationList implements Serializable {

    /**
     * Instance field
     */
    private boolean failed;
    private ArrayList<Reservation> reservations;

    /**
     * No argument constructor
     */
    public ReservationList() {
        this.failed = false;
        reservations = new ArrayList<>();
    }

    /**
     * Method adds reservation to the arraylist
     *
     * @param reservation
     */
    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Method gets index from the arraylist
     *
     * @param index
     * @return
     */
    public Reservation get(int index) {
        return reservations.get(index);
    }

    /**
     * Method removes a reservation from the arraylist
     *
     * @param reservation
     */
    public void remove(Reservation reservation) {
        reservations.remove(reservation);
    }

    /**
     * Method for returning the size of the arraylist
     *
     * @return
     */
    public int size() {
        return reservations.size();
    }

    /**
     * Method to check if the reservation is failed
     *
     * @return
     */
    public boolean isFailed() {
        return failed;
    }

    /**
     * Set method for failed
     *
     * @param failed
     */
    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}
