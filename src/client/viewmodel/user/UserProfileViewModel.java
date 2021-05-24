package client.viewmodel.user;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.*;
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
    private BooleanProperty vipCheck = new SimpleBooleanProperty();
    private StringProperty saveInfoLabel = new SimpleStringProperty();

    private UserModel model;
    private PropertyChangeSupport support;

    public UserProfileViewModel(UserModel userModel) {
        this.model = userModel;
        support = new PropertyChangeSupport(this);

        userModel.addPropertyChangeListener(EventType.SAVENEWINFO_RESULT.toString(), this::onSavedInfo);
        userModel.addPropertyChangeListener(EventType.SAVENEWINFOFAIL_RESULT.toString(), this::onSaveFail);
    }

    private void onSaveFail(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            saveInfoLabel.setValue("Username already exist");
        });
    }

    private void onSavedInfo(PropertyChangeEvent event) {
        System.out.println("hi");
        User result = (User) event.getNewValue();
        if (result != null) {
            Platform.runLater(() -> {
                support.firePropertyChange(EventType.SAVENEWINFO_RESULT.toString(), null, event.getNewValue());
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

    public void saveAccount(User userLoggedIn) {
        if (vipCheck.getValue()) {
            currentUsertype.setValue("VIP");
        } else {
            currentUsertype.setValue("USER");
        }
        if ((newPassword.isNotEmpty()).getValue() && newPassword.get()
                .equals(confirmPassword.get())) {
            if (newPassword.get().length() < 3 || newPassword.get().length() > 15) {
                saveInfoLabel.setValue("Password needs to be between 3 and 15 characters");
            } else {
                System.out.println(newPassword);
                System.out.println(confirmPassword);
                User user = new User(userLoggedIn.getId(), newFirstName.get(), newLastName.get(), newUsername.get(),
                        newPassword.get(), newPhoneNumber.get(), currentUsertype.get(), banned.get());
                model.saveNewInfo(user);
                System.out.println(newUsername.get());
             //   updateCurrentInfo(user);
            }

        } else {
            System.out.println("password dont match or you dont want to change the password");
            System.out.println("Current " + currentUsertype.get());
            System.out.println("Prop" + vipCheck.getValue());
            User user = new User(userLoggedIn.getId(),
                    newFirstName.get(), newLastName.get(), newUsername.get(),
                    userLoggedIn.getPassword(), newPhoneNumber.get(),
                    currentUsertype.get(), banned.get());
            model.saveNewInfo(user);
            System.out.println(newUsername.get());
          //  updateCurrentInfo(user);
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
        vipCheck.set(userLoggedIn.getUserType().equals("VIP"));
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

    public StringProperty saveInfoLabelProperty() {
        return saveInfoLabel;
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    public Property<Boolean> vipCheckProperty() {
        return vipCheck;
    }

    public void clearMessages() {
        saveInfoLabel.setValue("");
    }
}
