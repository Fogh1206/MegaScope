package client.viewmodel.login;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.User;
import shared.util.EventType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class LoginViewModel
{

  private StringProperty username;
  private StringProperty password;
  private StringProperty loginResult;
  private BooleanProperty adminResult;
  private PropertyChangeSupport support;

  private UserModel model;

  public LoginViewModel(UserModel userModel)
  {
    this.model = userModel;
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    loginResult = new SimpleStringProperty();
    support = new PropertyChangeSupport(this);

    model.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::onLogin);
  }

  private void onLogin(PropertyChangeEvent event)
  {
    User result = (User) event.getNewValue();

    if (result == null)
    {
      System.out.println("Null login");
    }
    if (result != null) {
      Platform.runLater(() -> {
        loginResult.set("Correct password");
        support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null,
            event.getNewValue());
      });
    }
  }

  public void clearMessages(){
    loginResult.set("");
  }


  public void defaultFields()
  {
    username.set("");
    password.set("");
    loginResult.set("");
  }

  public void login()
  {
    model.login(username.get(), password.get());
  }

  public void addPropertyChangeListener(String name,PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
  }

  public StringProperty usernameProperty()
  {
    return username;
  }

  public StringProperty passwordProperty()
  {
    return password;
  }

  public StringProperty loginResultProperty()
  {
    return loginResult;
  }

}
