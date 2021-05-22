package shared;

import java.io.Serializable;

public class Seat implements Serializable {
    private int id;
    private boolean disabled;

    public Seat(int id, boolean disabled) {
        this.id = id;
        this.disabled = disabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String toString(){
        return id+" "+disabled;
    }
}
