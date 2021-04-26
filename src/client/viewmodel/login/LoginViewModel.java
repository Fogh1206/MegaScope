package client.viewmodel.login;

import client.model.UserModel;
import client.view.ViewHandler;
import javafx.application.Platform;
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
  private PropertyChangeSupport support;

  private UserModel model;

  public LoginViewModel(UserModel model)
  {
    this.model = model;
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    loginResult = new SimpleStringProperty();
    support = new PropertyChangeSupport(this);

    model.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(),
        this::onLogin);
  }

  private void onLogin(PropertyChangeEvent event)
  {
    User result = (User) event.getNewValue();
    if (result != null)
    {
      Platform.runLater(() -> {
        loginResult.set("Correct password");
        support.firePropertyChange("YOLO", null,
            event.getNewValue());
      });
    }

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

  public void defaultFields()
  {
    username.set("");
    password.set("");
    loginResult.set("");
  }

  public void login()
  {
    System.out.println(username.get());
    System.out.println(password.get());
    model.login(username.get(), password.get());
  }

  public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    System.out.println(874);
    support.addPropertyChangeListener(name, listener);

  }
}
