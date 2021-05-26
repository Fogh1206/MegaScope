package client.viewmodel.user;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserProfileViewModel
{
  /**
   * Instance field and creating new objects to string properties
   */
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
  private BooleanProperty vipCheck = new SimpleBooleanProperty();
  private StringProperty saveInfoLabel = new SimpleStringProperty();

  private UserModel model;
  private PropertyChangeSupport support;

  /**
   * Constructor
   * Adds listeners
   *
   * @param userModel
   */

  public UserProfileViewModel(UserModel userModel)
  {
    this.model = userModel;
    support = new PropertyChangeSupport(this);

    userModel.addPropertyChangeListener(EventType.SAVENEWINFO_RESULT.toString(),
        this::onSavedInfo);
    userModel
        .addPropertyChangeListener(EventType.SAVENEWINFOFAIL_RESULT.toString(),
            this::onSaveFail);
  }

  /**
   * Void method displays message in a label when saving changes fail
   *
   * @param event
   */
  private void onSaveFail(PropertyChangeEvent event)
  {
    Platform.runLater(() -> {
      saveInfoLabel.setValue("Username already exist");
    });
  }

  /**
   * Void method sets new values to given changes and saves them
   *
   * @param event
   */
  private void onSavedInfo(PropertyChangeEvent event)
  {
    System.out.println("hi");
    User result = (User) event.getNewValue();
    if (result != null)
    {
      Platform.runLater(() -> {
        support
            .firePropertyChange(EventType.SAVENEWINFO_RESULT.toString(), null,
                event.getNewValue());
        currentFirstname.setValue(result.getFirstName());
        currentLastname.setValue(result.getLastName());
        currentUsertype.setValue(result.getUserType());
        currentUsername.setValue(result.getUsername());
        currentPhoneNumber.setValue(result.getPhoneNumber());
        currentUsertype.setValue(result.getUserType());
        saveInfoLabel.setValue("Successful");
      });
    }
  }

  /**
   * Void method for saving account after changes
   *
   * @param userLoggedIn
   */
  public void saveAccount(User userLoggedIn)
  {
    if (vipCheck.getValue())
    {
      currentUsertype.setValue("VIP");
    }
    else
    {
      currentUsertype.setValue("USER");
    }
    if ((newPassword.isNotEmpty()).getValue() && newPassword.get()
        .equals(confirmPassword.get()))
    {
      if (newPassword.get().length() < 3 || newPassword.get().length() > 15)
      {
        saveInfoLabel
            .setValue("Password needs to be between 3 and 15 characters");
      }
      else
      {
        System.out.println(newPassword);
        System.out.println(confirmPassword);
        User user = new User(userLoggedIn.getId(), newFirstName.get(),
            newLastName.get(), newUsername.get(), newPassword.get(),
            newPhoneNumber.get(), currentUsertype.get(), banned.get());
        model.saveNewInfo(user);
        System.out.println(newUsername.get());
      }

    }
    else if (!(newPassword.get().equals(confirmPassword.get())) && newPassword
        .isNotEmpty().getValue() && confirmPassword.isNotEmpty().getValue())
    {
      saveInfoLabel.setValue("Passwords do not match");
    }
    else
    {
      System.out.println(
          "password dont match or you dont want to change the password");
      System.out.println("Current " + currentUsertype.get());
      System.out.println("Prop" + vipCheck.getValue());
      User user = new User(userLoggedIn.getId(), newFirstName.get(),
          newLastName.get(), newUsername.get(), userLoggedIn.getPassword(),
          newPhoneNumber.get(), currentUsertype.get(), banned.get());
      model.saveNewInfo(user);
      System.out.println(newUsername.get());
      System.out.println(userLoggedIn.getPassword());
    }
  }

  /**
   * Void method updates new information
   *
   * @param userLoggedIn
   */
  public void updateCurrentInfo(User userLoggedIn)
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
    vipCheck.set(userLoggedIn.getUserType().equals("VIP"));
  }

  /**
   * String property for new phone number
   *
   * @return newPhoneNumber
   */
  public StringProperty newPhoneNumberProperty()
  {
    return newPhoneNumber;
  }

  /**
   * String property for current username
   *
   * @return currentUsername
   */
  public StringProperty currentUsernameProperty()
  {
    return currentUsername;
  }

  /**
   * String property for current firstname
   *
   * @return currentFirstName
   */
  public StringProperty currentFirstnameProperty()
  {
    return currentFirstname;
  }

  /**
   * String property for current lastname
   *
   * @return currentLastName
   */
  public StringProperty currentLastnameProperty()
  {
    return currentLastname;
  }

  /**
   * String property for current phone number
   *
   * @return currentPhoneNumber
   */
  public StringProperty currentPhoneNumberProperty()
  {
    return currentPhoneNumber;
  }

  /**
   * String property for current user type
   *
   * @return currentUserType
   */
  public StringProperty currentUsertypeProperty()
  {
    return currentUsertype;
  }

  /**
   * String property for new first name
   *
   * @return newFirstName
   */
  public StringProperty newFirstNameProperty()
  {
    return newFirstName;
  }

  /**
   * String property for new lastname
   *
   * @return newLastName
   */
  public StringProperty newLastNameProperty()
  {
    return newLastName;
  }

  /**
   * String property for new username
   *
   * @return newUsername
   */
  public StringProperty newUsernameProperty()
  {
    return newUsername;
  }

  /**
   * String property for new password
   *
   * @return newPassword
   */
  public StringProperty newPasswordProperty()
  {
    return newPassword;
  }

  /**
   * String property for confirm password
   *
   * @return confirmPassword
   */
  public StringProperty confirmPasswordProperty()
  {
    return confirmPassword;
  }

  /**
   * String property for save info label
   *
   * @return saveInfoLabel
   */
  public StringProperty saveInfoLabelProperty()
  {
    return saveInfoLabel;
  }

  /**
   * Void method for adding a listener
   *
   * @param name
   * @param listener
   */
  public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
  }

  /**
   * Boolean method to check if the user is VIP
   *
   * @return
   */
  public Property<Boolean> vipCheckProperty()
  {
    return vipCheck;
  }

  /**
   * Void method to clear message in the saveInfoLabel
   */
  public void clearMessages()
  {
    saveInfoLabel.setValue("");
  }
}
