package client.model;

import client.networking.Client;
import shared.Movie;
import shared.NewRegisteredUser;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Model implements UserModel
{
  private Client client;
  private User loggedUser;
  private PropertyChangeSupport support;

  public Model(Client client)
  {
    this.client = client;
    support = new PropertyChangeSupport(this);
    client.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(),
        this::onLoginResult);

    client.addPropertyChangeListener(EventType.GETMOVIES_RESULT.toString(),
        this::onGetMoviesResult);

    client.addPropertyChangeListener(EventType.REGISTER_RESULT.toString(),
        this::onRegisterResult);

  }

  private void onGetMoviesResult(PropertyChangeEvent event)
  {
    System.out.println(4);
    ArrayList<Movie> list = (ArrayList<Movie>) event.getNewValue();
    System.out.println("Size"+list.size());
    System.out.println( list.get(0).getName());
    System.out.println( list.get(1).getName());
    System.out.println( list.get(2).getName());
    System.out.println(5);
    try
    {
      support
          .firePropertyChange(EventType.GETMOVIES_RESULT.toString(), null, list);
    }
    catch (Exception e)
    {
      System.out.println("starge days");
    }

    support
        .firePropertyChange(EventType.GETMOVIES_RESULT.toString(), null, list);
    System.out.println(5.5);
  }

  private void onLoginResult(PropertyChangeEvent event)
  {
    User loginResult = (User) event.getNewValue();
    support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null,
        loginResult);

  }

  private void onRegisterResult(PropertyChangeEvent event)
  {
    String registerResult = (String) event.getNewValue();
    System.out.println(registerResult);
  }

  @Override public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
  }

  @Override public void removePropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(name, listener);
  }

  @Override public void register(NewRegisteredUser user)
  {

    client.registerUser(user);
    System.out.println(
        "Register :>>>> " + "username : " + user.getUsername() + " password:  "
            + user.getPassword());
  }

  @Override public void login(String username, String password)
  {
    loggedUser = new User(username, password);
    client.login(loggedUser);

  }

  @Override public void deactivateClient()
  {
    client.deactivateClient();
  }
}
