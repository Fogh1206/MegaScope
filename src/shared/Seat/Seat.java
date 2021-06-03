package shared.Seat;

import java.io.Serializable;

public class Seat implements Serializable {
    /**
     * Instance field
     */
    private int id;
    private boolean disabled;

    /**
     * Constructor with 2 arguments
     *
     * @param id
     * @param disabled
     */
    public Seat(int id, boolean disabled) {
        this.id = id;
        this.disabled = disabled;
    }

    /**
     * Get method for id
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Get method for seat id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method to check if the seats are disabled
     *
     * @return
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Set method to disable seats
     *
     * @param disabled
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * To string method
     *
     * @return
     */
    public String toString() {
        return id + " " + disabled;
    }
}
