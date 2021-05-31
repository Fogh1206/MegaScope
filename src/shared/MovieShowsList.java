package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieShowsList implements Serializable {
    private ArrayList<MovieShow> movieShows;

    public MovieShowsList() {
        movieShows = new ArrayList<>();
    }

    public void addShow(MovieShow movieShow) {
        movieShows.add(movieShow);
    }

    public void removeShow(MovieShow movieShow) {
        movieShows.remove(movieShow);
    }

    public MovieShow get(int index) {
        return movieShows.get(index);
    }

    public int getSize() {
        return movieShows.size();
    }
}
