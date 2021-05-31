package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class UserReservationInfoList implements Serializable {

    private ArrayList<UserReservationInfo> userReservationInfos;

    public UserReservationInfoList()
    {
        userReservationInfos=new ArrayList<>();
    }

    public void add(UserReservationInfo userReservationInfo)
    {
        userReservationInfos.add(userReservationInfo);
    }

    public UserReservationInfo get(int index)
    {
        return userReservationInfos.get(index);
    }

    public int getSize()
    {
        return userReservationInfos.size();
    }




}
