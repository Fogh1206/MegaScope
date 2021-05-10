package client.viewmodel.registration;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.NewRegisteredUser;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RegisterViewModel {
    private StringProperty firstName, lastName, username, password, confirmPassword, registrationMessageLabel, confirmPasswordLabel, phoneNumber;
    private UserModel model;
    private PropertyChangeSupport support;

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
        userModel.addPropertyChangeListener(EventType.REGISTER_RESULT.toString(),
                this::onRegister);
    }

    private void onRegister(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            support.firePropertyChange(EventType.REGISTER_RESULT.toString(), null,
                    event.getNewValue());
        });
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
        } else if (confirmPassword.get() == null || "".equals(confirmPassword.get())) {
            registrationMessageLabel.setValue("Please input your password confirmation");
        }
        if (confirmPassword.get() != null) {
            if (!confirmPassword.get().equals(password.get())) {
                confirmPasswordLabel.setValue("The password don't match");
            } else {
                registerUserAccount();

            }
        }
    }

    public void registerUserAccount() {
        model.register(
                new NewRegisteredUser(firstName.get(), lastName.get(), username.get(),
                        password.get(), phoneNumber.get(), "USER", false));
        defaultFields();
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

    public void addPropertyChangeListener(String name,
                                          PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }
}
