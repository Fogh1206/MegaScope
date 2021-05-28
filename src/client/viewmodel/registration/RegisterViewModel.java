package client.viewmodel.registration;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RegisterViewModel {
    private StringProperty firstName, lastName, username, password, confirmPassword, registrationMessageLabel, confirmPasswordLabel, phoneNumber;
    private UserModel model;
    private PropertyChangeSupport support;

    /**
     * Constructor for RegisterViewModel
     * @param userModel
     */
    public RegisterViewModel(UserModel userModel) {
        this.model = userModel;
        support = new PropertyChangeSupport(this);
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        confirmPassword = new SimpleStringProperty();
        registrationMessageLabel = new SimpleStringProperty();
        confirmPasswordLabel = new SimpleStringProperty();
        phoneNumber = new SimpleStringProperty();

        userModel.addPropertyChangeListener(EventType.REGISTER_RESULT.toString(), this::onRegister);
        userModel.addPropertyChangeListener(EventType.REGISTERFAIL_RESULT.toString(), this::onRegisterFail);
    }


    /**
     * Method to run when user successfully registers into the database.
     * @param event
     */
    public void onRegister(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            registrationMessageLabel.setValue("Successful");
            support.firePropertyChange(EventType.REGISTER_RESULT.toString(), null,
                    event.getNewValue());
        });
    }

    /**
     * Method to run when user is unsuccessfully registers because the username is already in use.
     * @param event
     */
    public void onRegisterFail(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            registrationMessageLabel.setValue("Username already exist");
        });
    }

    /**
     * Method to return a boolean, if user inputs are valid then return true. Otherwise return false.
     * @param user
     * @return
     */
    public boolean isValidInput(User user){
        if(!(user.getFirstName() == null) || "".equals(user.getFirstName())){
            return false;
        } else if(!(user.getLastName() == null) || "".equals(user.getLastName())){
            return false;
        } else if(user.getUsername() == null || "".equals(user.getUsername())){
            return false;
        } else if(user.getPassword() == null || "".equals(user.getPassword())) {
            return false;
        } else if(user.getPassword().length() < 3 || user.getPassword().length() > 15){
            if(user.getPassword().length() < 3){
                throw new IllegalArgumentException("Password is too short");
            } else if(user.getPassword().length() > 15) {
                throw new IllegalArgumentException("Password is too long");
            }
            return false;
        } else if(!(user.getPhoneNumber() == null) || "".equals(user.getPhoneNumber())){
            return false;
        } else {
            return true;
        }
    }

    public void register() {
        if (firstName.get() == null || "".equals(firstName.get())) {
            registrationMessageLabel.setValue("Please input your first name");
        } else if (lastName.get() == null || "".equals(lastName.get())) {
            registrationMessageLabel.setValue("Please input your last name");
        } else if (phoneNumber.get() == null || "".equals(phoneNumber.get())) {
            registrationMessageLabel.setValue("Please input your phone number");
        } else if (username.get() == null || "".equals(username.get())) {
            registrationMessageLabel.setValue("Please input your username");
        } else if (password.get() == null || "".equals(password.get())) {
            registrationMessageLabel.setValue("Please input your password");
        } else if (password.get().length() < 3 || password.get().length() > 15) {
            registrationMessageLabel.setValue("Password needs to be between 3 and 15 characters");
        } else if (confirmPassword.get() == null || "".equals(confirmPassword.get())) {
            registrationMessageLabel.setValue("Please input your password confirmation");
        } else {
            if (confirmPassword.get() != null) {
                if (!confirmPassword.get().equals(password.get())) {
                    confirmPasswordLabel.setValue("The password don't match");
                } else {
                    registerUserAccount();
                }
            }
        }
    }

    /**
     * Send user details to model with the method register(User user).
     */
    public void registerUserAccount() {
        model.register(new User(firstName.get(), lastName.get(), username.get(),
                password.get(), phoneNumber.get(), "USER", false));
        defaultFields();
    }

    public void clearMessages() {
        registrationMessageLabel.setValue("");
    }

    public void defaultFields() {
        firstName.setValue("");
        lastName.setValue("");
        phoneNumber.setValue("");
        username.setValue("");
        password.setValue("");
        confirmPassword.setValue("");
        confirmPasswordLabel.setValue("");
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        if (support.getPropertyChangeListeners(name) == null) {
            support.addPropertyChangeListener(name, listener);
        }
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty confirmPasswordProperty() {
        return confirmPassword;
    }

    public StringProperty registrationMessageLabelProperty() {
        return registrationMessageLabel;
    }

    public StringProperty confirmPasswordLabelProperty() {
        return confirmPasswordLabel;
    }


}
