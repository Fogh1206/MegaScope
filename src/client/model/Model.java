package client.model;

import client.networking.Client;
import shared.NewRegisteredUser;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
    System.out.println((String) event.getNewValue());
  }

  private void onLoginResult(PropertyChangeEvent event)
  {
    String loginResult = (String) event.getNewValue();
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
}
