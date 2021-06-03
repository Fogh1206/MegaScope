package shared.MovieShow;

import shared.MovieShow.MovieShow;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieShowsList implements Serializable
{
  /**
   * Instance field with a arraylist of MovieShow
   */
  private ArrayList<MovieShow> movieShows;

  /**
   * No argument constructor
   */
  public MovieShowsList()
  {
    movieShows = new ArrayList<>();
  }

  /**
   * Method adds a movieShow to the arraylist
   *
   * @param movieShow
   */
  public void addShow(MovieShow movieShow)
  {
    movieShows.add(movieShow);
  }

  /**
   * Method removes a movieShow from the arraylist
   *
   * @param movieShow
   */
  public void removeShow(MovieShow movieShow)
  {
    movieShows.remove(movieShow);
  }

  /**
   * Method gets the index of the arraylist
   *
   * @param index
   * @return
   */
  public MovieShow get(int index)
  {
    return movieShows.get(index);
  }

  public int getSize()
  {
    return movieShows.size();
  }
}
