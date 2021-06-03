package shared.Seat;

import java.io.Serializable;
import java.util.ArrayList;

public class SeatList implements Serializable
{

  /**
   * Instance field
   */
  private ArrayList<Seat> seats;

  /**
   * No argument constructor
   */
  public SeatList()
  {
    seats = new ArrayList<>();
  }

  /**
   * Method adds a seat to the arraylist
   *
   * @param seat
   */
  public void add(Seat seat)
  {
    seats.add(seat);
  }

  /**
   * Set method for seat
   *
   * @param seat
   */
  public void set(Seat seat)
  {
    int j = 0;
    for (int i = 0; i < seats.size(); i++)
    {
      if (seats.get(i).getId() == seat.getId())
      {
        seats.set(i, seat);
        j++;
      }
    }
    if (j == 0)
    {
      add(seat);
    }
  }

  /**
   * Get method for seat
   *
   * @param index
   * @return
   */
  public Seat get(int index)
  {
    return seats.get(index);
  }

  /**
   * Method removes seat from arraylist
   *
   * @param seat
   */
  public void remove(Seat seat)
  {
    seats.remove(seat);
  }

  /**
   * Method gets size of the arraylist
   *
   * @return
   */
  public int size()
  {
    return seats.size();
  }
}