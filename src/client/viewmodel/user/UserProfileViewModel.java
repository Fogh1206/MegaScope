package client.viewmodel.user;

import client.model.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.NewRegisteredUser;
import shared.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserProfileViewModel
{
  private StringProperty currentUsername = new SimpleStringProperty();
  private StringProperty currentFirstname = new SimpleStringProperty();
  private StringProperty currentLastname = new SimpleStringProperty();
  private StringProperty currentPhoneNumber = new SimpleStringProperty();

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

  public void save()
  {

    if (newFirstName == null)
    {
      newFirstName.setValue(currentFirstname.getValue());
    }
    else if (newLastName == null)
    {
      newLastName.setValue(currentLastname.getValue());
    }

    else if (newUsername == null)
    {
      newPassword.setValue(currentUsername.getValue());
    }

    else if (newPhoneNumber == null)
    {
      newPhoneNumber.setValue(currentPhoneNumber.getValue());
    }

    else
      saveAccount();

  }

  public void saveAccount()
  {

    model.saveNewInfo(
        new NewRegisteredUser(newFirstName.get(), newLastName.get(),
            newUsername.get(), newPassword.get(), newPhoneNumber.get()));
    defaultsValue();

  }

  public void defaultsValue()
  {
    newFirstName.setValue("");
    newLastName.setValue("");
    newUsername.setValue("");
    newPassword.setValue("");
    confirmPassword.setValue("");

  }

  public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {

    support.addPropertyChangeListener(name, listener);

  }

  public void updateCurrentInfo(NewRegisteredUser userLoggedIn)
  {
    currentFirstname.setValue(userLoggedIn.getFirstName());
    newFirstName.setValue(currentFirstname.getValue());
    currentLastname.setValue(userLoggedIn.getLastName());
    currentUsername.setValue(userLoggedIn.getUsername());
    currentPhoneNumber.setValue(userLoggedIn.getPhoneNumber());
  }
}
