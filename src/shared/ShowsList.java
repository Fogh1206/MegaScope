package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowsList implements Serializable {
    private ArrayList<Show> shows;

    public ShowsList() {
        shows = new ArrayList<>();
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public void removeShow(Show show)

    {
        shows.remove(show);
    }

    public Show get(int index)
    {
        return shows.get(index);
    }

    public int getSize()
    {
        return shows.size();
    }


}
