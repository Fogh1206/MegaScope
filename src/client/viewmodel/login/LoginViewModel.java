package client.viewmodel.login;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import shared.Movie;
import shared.NewRegisteredUser;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

public class LoginViewModel
{

  private StringProperty username;
  private StringProperty password;
  private StringProperty loginResult;
  private BooleanProperty adminResult;
  private PropertyChangeSupport support;

  private UserModel model;

  public LoginViewModel(UserModel model)
  {
    this.model = model;
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    loginResult = new SimpleStringProperty();
    support = new PropertyChangeSupport(this);

    model.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::onLogin);
  }

  private void onLogin(PropertyChangeEvent event)
  {
    NewRegisteredUser result = (NewRegisteredUser) event.getNewValue();
    System.out.println("Kappa");
    if (result == null)
    {
      System.out.println("Kappa");
    }
    if (result != null) {
      Platform.runLater(() -> {
        loginResult.set("Correct password");
        support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null,
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
