package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class UserReservationInfoList implements Serializable
{

  /**
   * Instance field
   */
  private ArrayList<UserReservationInfo> userReservationInfos;

  /**
   * No argument constructor
   */
  public UserReservationInfoList()
  {
    userReservationInfos = new ArrayList<>();
  }

  /**
   * Method adds userReservationInfo to the arraylist
   *
   * @param userReservationInfo
   */
  public void add(UserReservationInfo userReservationInfo)
  {
    userReservationInfos.add(userReservationInfo);
  }

  /**
   * Method gets the index of the arraylist
   *
   * @param index
   * @return
   */
  public UserReservationInfo get(int index)
  {
    return userReservationInfos.get(index);
  }

  /**
   * Method gets the size of the arraylist
   *
   * @return
   */
  public int getSize()
  {
    return userReservationInfos.size();
  }

}
