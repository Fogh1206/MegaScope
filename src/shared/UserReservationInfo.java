package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class UserReservationInfo implements Serializable
{

  /**
   * Instance field
   */
  private int reservation_id;
  private String name;
  private String time_show;
  private String date_show;
  private int seat_id;

  /**
   * Constructor
   *
   * @param reservation_id
   * @param name
   * @param time_show
   * @param date_show
   * @param seat_id
   */
  public UserReservationInfo(int reservation_id, String name, String time_show,
      String date_show, int seat_id)
  {
    this.reservation_id = reservation_id;
    this.name = name;
    this.time_show = time_show;
    this.date_show = date_show;
    this.seat_id = seat_id;
  }

  /**
   * Get method for reservation id
   *
   * @return reservation_id
   */
  public int getReservation_id()
  {
    return reservation_id;
  }

  /**
   * Get method for name
   *
   * @return name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get method for time of show
   *
   * @return time_show
   */
  public String getTime_show()
  {
    return time_show;
  }

  /**
   * Get method for date of show
   *
   * @return date_show
   */
  public String getDate_show()
  {
    return date_show;
  }

  /***
   * Get method for seat id
   * @return seat_id
   */
  public int getSeat_id()
  {
    return seat_id;
  }

}
