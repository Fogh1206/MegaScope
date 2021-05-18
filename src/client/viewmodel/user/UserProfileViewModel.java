package client.viewmodel.user;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserProfileViewModel {
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

    public UserProfileViewModel(UserModel userModel) {
        this.model = userModel;
        support = new PropertyChangeSupport(this);

        userModel.addPropertyChangeListener(EventType.SAVENEWINFO_RESULT.toString(),
                this::onSavedInfo);
    }

    private void onSavedInfo(PropertyChangeEvent event) {

        User result = (User) event.getNewValue();
        if (result != null) {
            Platform.runLater(() -> {
                support.firePropertyChange(EventType.SAVENEWINFO_RESULT.toString(), null, event.getNewValue());
            });
        }
    }

    public void addPropertyChangeListener(String name,PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    public void saveAccount(User userLoggedIn) {
        if ((newPassword.isNotEmpty()).getValue() && newPassword.get()
                .equals(confirmPassword.get())) {
            System.out.println(newPassword);
            System.out.println(confirmPassword);
                        User user = new User(userLoggedIn.getId(),
                    newFirstName.get(), newLastName.get(), newUsername.get(),
                    newPassword.get(), newPhoneNumber.get(), currentUsertype.get(),
                    banned.get());
            model.saveNewInfo(user);
            System.out.println(newUsername.get());
            updateCurrentInfo(user);

        } else {
            System.out.println(
                    "password dont match or you dont want to change the password");
            User user = new User(userLoggedIn.getId(),
                    newFirstName.get(), newLastName.get(), newUsername.get(),
                    userLoggedIn.getPassword(), newPhoneNumber.get(),
                    currentUsertype.get(), banned.get());
            model.saveNewInfo(user);
            System.out.println(newUsername.get());
            updateCurrentInfo(user);
            System.out.println(userLoggedIn.getPassword());
        }
    }


    public void updateCurrentInfo(User userLoggedIn) {
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

    public StringProperty newPhoneNumberProperty() {
        return newPhoneNumber;
    }

    public StringProperty currentUsernameProperty() {
        return currentUsername;
    }

    public StringProperty currentFirstnameProperty() {
        return currentFirstname;
    }

    public StringProperty currentLastnameProperty() {
        return currentLastname;
    }

    public StringProperty currentPhoneNumberProperty() {
        return currentPhoneNumber;
    }

    public StringProperty currentUsertypeProperty() {
        return currentUsertype;
    }

    public StringProperty newFirstNameProperty() {
        return newFirstName;
    }

    public StringProperty newLastNameProperty() {
        return newLastName;
    }

    public StringProperty newUsernameProperty() {
        return newUsername;
    }

    public StringProperty newPasswordProperty() {
        return newPassword;
    }

    public StringProperty confirmPasswordProperty() {
        return confirmPassword;
    }

    public BooleanProperty bannedProperty() {
        return banned;
    }
}
