package client.viewmodel.user;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.NewRegisteredUser;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserProfileViewModel
{
  private StringProperty currentUsername = new SimpleStringProperty();
  private StringProperty currentFirstname = new SimpleStringProperty();
  private StringProperty currentLastname = new SimpleStringProperty();
  private StringProperty currentPhoneNumber = new SimpleStringProperty();
  private StringProperty currentUsertype = new SimpleStringProperty();
  private BooleanProperty banned = new SimpleBooleanProperty();

  private StringProperty newFirstName = new SimpleStringProperty();
  private StringProperty newLastName = new SimpleStringProperty();
  private StringProperty newPhoneNumber = new SimpleStringProperty();
  private StringProperty newUsername = new SimpleStringProperty();
  private StringProperty newPassword = new SimpleStringProperty();
  private StringProperty confirmPassword = new SimpleStringProperty();
  private UserModel model;
  private PropertyChangeSupport support;

  public UserProfileViewModel(UserModel model)
  {
    this.model = model;
    support = new PropertyChangeSupport(this);

    model.addPropertyChangeListener(EventType.SAVENEWINFO_RESULT.toString(),
        this::onSavedInfo);
  }

  public BooleanProperty bannedProperty()
  {
    return banned;
  }

  private void onSavedInfo(PropertyChangeEvent event)
  {

    NewRegisteredUser result = (NewRegisteredUser) event.getNewValue();
    if (result != null)
    {
      Platform.runLater(() -> {

        support
            .firePropertyChange(EventType.SAVENEWINFO_RESULT.toString(), null,
                event.getNewValue());
      });
    }

  }

  public StringProperty newPhoneNumberProperty()
  {
    return newPhoneNumber;
  }

  public StringProperty currentUsernameProperty()
  {
    return currentUsername;
  }

  public StringProperty currentFirstnameProperty()
  {
    return currentFirstname;
  }

  public StringProperty currentLastnameProperty()
  {
    return currentLastname;
  }

  public StringProperty currentPhoneNumberProperty()
  {
    return currentPhoneNumber;
  }

  public StringProperty currentUsertypeProperty()
  {
    return currentUsertype;
  }

  public StringProperty newFirstNameProperty()
  {
    return newFirstName;
  }

  public StringProperty newLastNameProperty()
  {
    return newLastName;
  }

  public StringProperty newUsernameProperty()
  {
    return newUsername;
  }

  public StringProperty newPasswordProperty()
  {
    return newPassword;
  }

  public StringProperty confirmPasswordProperty()
  {
    return confirmPassword;
  }

  public void save(NewRegisteredUser userLoggedIn)
  {

    saveAccount(userLoggedIn);
  }

  public void saveAccount(NewRegisteredUser userLoggedIn)
  {


    if ((newPassword.isNotEmpty()).getValue() && newPassword.get()
        .equals(confirmPassword.get()))
    {

      System.out.println(newPassword);

      System.out.println(confirmPassword);

      System.out.println(5);
      NewRegisteredUser user = new NewRegisteredUser(userLoggedIn.getId(),
          newFirstName.get(), newLastName.get(), newUsername.get(),
          newPassword.get(), newPhoneNumber.get(), currentUsertype.get(),
          banned.get());
      model.saveNewInfo(user);
      System.out.println(newUsername.get());
      updateCurrentInfo(user);

    }
    else
    {
      System.out.println(
          "password dont match or you dont want to change the password");
      NewRegisteredUser user = new NewRegisteredUser(userLoggedIn.getId(),
          newFirstName.get(), newLastName.get(), newUsername.get(),
          userLoggedIn.getPassword(), newPhoneNumber.get(),
          currentUsertype.get(), banned.get());
      model.saveNewInfo(user);
      System.out.println(newUsername.get());
      updateCurrentInfo(user);
      System.out.println(userLoggedIn.getPassword());
    }
  }


  public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {

    support.addPropertyChangeListener(name, listener);

  }

  public void updateCurrentInfo(NewRegisteredUser userLoggedIn)
  {

    currentFirstname.setValue(userLoggedIn.getFirstName());

    currentLastname.setValue(userLoggedIn.getLastName());
    currentUsername.setValue(userLoggedIn.getUsername());
    currentPhoneNumber.setValue(userLoggedIn.getPhoneNumber());
    currentUsertype.setValue(userLoggedIn.getUserType());

    newFirstName.setValue(currentFirstname.getValue());

    newLastName.setValue(currentLastname.getValue());

    newUsername.setValue(currentUsername.getValue());

    newPhoneNumber.setValue(currentPhoneNumber.getValue());

  }
}
