package shared.Reservation;

import java.io.Serializable;

public class Reservation implements Serializable
{

  /**
   * Instance field
   */
  private int reservation_id;
  private int seat_no;
  private int show_id;
  private int user_id;

  /**
   * Constructor with 4 arguments
   *
   * @param reservation_id
   * @param seat_no
   * @param show_id
   * @param user_id
   */
  public Reservation(int reservation_id, int seat_no, int show_id, int user_id)
  {
    this.reservation_id = reservation_id;
    this.seat_no = seat_no;
    this.show_id = show_id;
    this.user_id = user_id;
  }

  /**
   * Constructor with 3 arguments
   *
   * @param seat_no
   * @param show_id
   * @param user_id
   */
  public Reservation(int seat_no, int show_id, int user_id)
  {
    this.seat_no = seat_no;
    this.show_id = show_id;
    this.user_id = user_id;
  }

  /**
   * Constructor with 2 arguments
   *
   * @param seat_no
   * @param user_id
   */
  public Reservation(int seat_no, int user_id)
  {
    this.seat_no = seat_no;
    this.user_id = user_id;
  }

  /**
   * Get method for reservation id
   *
   * @return
   */
  public int getReservation_id()
  {
    return reservation_id;
  }

  /**
   * Get method for seat number
   *
   * @return
   */
  public int getSeat_no()
  {
    return seat_no;
  }

  /**
   * Get method for show id
   *
   * @return
   */
  public int getShow_id()
  {
    return show_id;
  }

  /**
   * Get method for user id
   *
   * @return
   */
  public int getUser_id()
  {
    return user_id;
  }

  /**
   * To string method
   *
   * @return
   */
  @Override public String toString()
  {
    return "Reservation{" + "reservation_id=" + reservation_id + ", seat_no="
        + seat_no + ", show=" + show_id + ", user=" + user_id + '}';
  }
}
