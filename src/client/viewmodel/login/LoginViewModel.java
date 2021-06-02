package client.viewmodel.login;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.PropertyChangeSubject;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class LoginViewModel implements PropertyChangeSubject {

    private StringProperty username;
    private StringProperty password;
    private StringProperty loginResult;
    private BooleanProperty adminResult;
    private PropertyChangeSupport support;

    private UserModel model;

    /**
     * Constructor for LoginViewModel
     *
     * @param userModel
     */

    public LoginViewModel(UserModel userModel) {
        this.model = userModel;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        loginResult = new SimpleStringProperty();
        support = new PropertyChangeSupport(this);

        model.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::onLogin);
        model.addPropertyChangeListener(EventType.LOGINFAIL_RESULT.toString(), this::onLoginFail);

    }

    /**
     * Method occurring if the server detects the user is banned in the database.
     *
     * @param event
     */
    private void onLoginFail(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            loginResult.setValue("You are banned by the administration");
        });
    }

    /**
     * Method occurring if the server successfully detects the user in the system and grants access.
     *
     * @param event
     */
    private void onLogin(PropertyChangeEvent event) {
        User result = (User) event.getNewValue();
        if (result == null) {
            Platform.runLater(() -> {
                System.out.println("Null login");
                loginResult.set("Wrong username or password");
            });
        }
        if (result != null) {
            Platform.runLater(() -> {
                loginResult.set("Correct password");
                support.firePropertyChange(EventType.LOGIN_RESULT.toString(), null,
                        event.getNewValue());
            });
        }
    }

    /**
     * Method to clear label showing login progress.
     */
    public void clearMessages() {
        loginResult.set("");
    }


    /**
     * Method to empty login input fields.
     */
    public void defaultFields() {
        username.set("");
        password.set("");
        loginResult.set("");
    }

    /**
     * Method used by {@link client.view.login.LoginViewController} which links with a method in {@link client.model.Model}
     */
    public void login() {
        if (username.get() == null || password.get() == null || username.get().equals("") || password.get().equals("")) {
            loginResult.set("Please input you login information");
        } else {
            model.login(username.get(), password.get());
            defaultFields();
        }
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(support.getPropertyChangeListeners()[0]);
    }

    /**
     * Return String from the FXML username TextField
     *
     * @return
     */
    public StringProperty usernameProperty() {
        return username;
    }

    /**
     * Return String from the FXML password TextField
     *
     * @return
     */
    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * Return String from the FXML login result TextField
     *
     * @return
     */
    public StringProperty loginResultProperty() {
        return loginResult;
    }

}
