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
import java.util.Arrays;

public class Model implements UserModel
{
  private Client client;
  private NewRegisteredUser loggedUser;
  private PropertyChangeSupport support;

  public Model(Client client)
  {
    this.client = client;
    support = new PropertyChangeSupport(this);
    if (true)
    {
      System.out.println("Dodano");
      client.addPropertyChangeListener(EventType.GETMOVIES_RESULT.toString(),
          this::onGetMoviesResult);
      System.out.println("Skonczono dodawac");
    }

    client.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(),
        this::onLoginResult);

    client.addPropertyChangeListener(EventType.REGISTER_RESULT.toString(),
        this::onRegisterResult);

    client.addPropertyChangeListener(EventType.GETUSER_RESULT.toString(),
            this::onGetUserResult);

  }

  private void onGetUserResult(PropertyChangeEvent event) {

    ArrayList<NewRegisteredUser> list = (ArrayList<NewRegisteredUser>) event.getNewValue();
    support.firePropertyChange("Users Result", null, list);
    System.out.println(list.get(0));

  }

  @Override
  public void getUsers() {
    client.getUsers();
  }

  private void onGetMoviesResult(PropertyChangeEvent event)
  {

    ArrayList<Movie> list = (ArrayList<Movie>) event.getNewValue();


    support.firePropertyChange("Movie Result", null, list);


  }

  private void onLoginResult(PropertyChangeEvent event)
  {
    NewRegisteredUser loginResult = (NewRegisteredUser) event.getNewValue();
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

  @Override public void saveNewInfo(NewRegisteredUser user)
  {
    client.saveNewInfo(user);
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
    loggedUser = new NewRegisteredUser(username, password);
    client.login(loggedUser);

  }

  @Override public void deactivateClient()
  {
    client.deactivateClient();
  }

  @Override public void getMovies()
  {
    client.getMovies();
  }
}
