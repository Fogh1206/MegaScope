package shared;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable {

    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<>();
    }

    public void add(User user){
        users.add(user);
    }

    public User get(int index){
        return users.get(index);
    }

    public void remove(User user){
        users.remove(user);
    }

    public int size(){
        return users.size();
    }

    public String toString(){
        String rtnString = "Users = {\n\t";
        for(int i = 0 ; i < users.size() ; i++){
            rtnString += users.get(i).getUsername() + ", \n\t";
        }
        rtnString += "\n}";
        return  rtnString;
    }


}
