package client.networking;

import shared.NewRegisteredUser;
import shared.Request;
import shared.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class FakeClient implements Client {

    private List<User> users = new ArrayList<>();
    private PropertyChangeSupport support;

    public FakeClient()
    {
        users.add(new User("dorin","123"));
        support = new PropertyChangeSupport(this);
    }


    @Override
    public void registerUser(NewRegisteredUser userToCreate) {

    }

    @Override
    public void login(User user) {
        System.out.println("CLient: " + user);
        String result = "";
        boolean userFound = false;
        for (User u : users) {
            if(u.getUsername().equals(user.getUsername())) {
                if(u.getPassword().equals(user.getPassword())) {
                    result = "OK";
                } else {
                    result = "Incorrect password";
                }
                userFound = true;
            }
        }
        if(!userFound){
            result = "User not found";
        }
        support.firePropertyChange("Login Result", null, result);
    }

    @Override public void getMovies()
    {

    }

    @Override public boolean isRunning()
    {
        return false;
    }

    @Override public void receive(Request req)
    {

    }

    @Override public void deactivateClient()
    {

    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }
}
