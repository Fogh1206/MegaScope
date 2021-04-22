package client.viewmodel.login;

import client.model.UserModel;
import client.view.ViewHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;

public class LoginViewModel {

    private StringProperty username;
    private StringProperty password;
    private StringProperty loginResult;

    private UserModel model;
    private ViewHandler viewHandler;

    public LoginViewModel(UserModel model) {
        this.model=model;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        loginResult = new SimpleStringProperty();
        model.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::onLogin);
    }

    private void onLogin(PropertyChangeEvent event) {
        String result = (String) event.getNewValue();
        Platform.runLater(() -> {
            loginResult.set(result);
        });
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }


    public StringProperty loginResultProperty() {
        return loginResult;
    }

    public void defaultFields()
    {
        username.set("");
        password.set("");
        loginResult.set("");
    }
    public void login() {
        System.out.println(username.get());
        System.out.println(password.get());
        model.login(username.get(), password.get());
      //  loginResult.set("OK");

    }
}
