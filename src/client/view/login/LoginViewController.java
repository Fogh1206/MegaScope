package client.view.login;

import client.view.ViewHandler;
import client.viewmodel.login.LoginViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import shared.User;

import shared.util.EventType;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;

public class LoginViewController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView logoView;
    @FXML
    private Label loginText;

    private User userLoggedIn;
    private LoginViewModel loginViewModel;
    private ViewHandler viewHandler;


    public void init(LoginViewModel loginViewModel, ViewHandler viewHandler, User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
        this.loginViewModel = loginViewModel;
        loginViewModel.clearMessages();
        this.viewHandler = viewHandler;
        usernameTextField.textProperty().bindBidirectional(loginViewModel.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(loginViewModel.passwordProperty());
        loginText.textProperty().bindBidirectional(loginViewModel.loginResultProperty());
        try {
            File imageFIle = new File("images/cinema.jpg");
            Image image = new Image(imageFIle.toURI().toString());
            imageView.setImage(image);

            File logoFile = new File("images/logo.png");
            Image logo = new Image(logoFile.toURI().toString());
            logoView.setImage(logo);
        } catch (NullPointerException e) {
            System.out.println("image problem");
        }
        loginViewModel.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
    }

    private void newLogin(PropertyChangeEvent event) {
        System.out.println("Karamba");
        User temp = (User) event.getNewValue();
        if (temp != null) {
            userLoggedIn = temp;
            loginViewModel.removePropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
            viewHandler.showFrontPage(userLoggedIn);
        }
    }


    public void onLoginAction() {
        if (userLoggedIn != null) {
        }
        loginViewModel.login();
        loginViewModel.defaultFields();
    }


    public void onCancelAction() {
        viewHandler.close();
        System.out.println("Cancelled");
    }

    public void onRegisterAction() {
        loginViewModel.defaultFields();
        loginViewModel.removePropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
        viewHandler.openRegisterView();
    }

    public void frontPageButton() {
        loginViewModel.removePropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
        viewHandler.showFrontPage(null);
    }


}
