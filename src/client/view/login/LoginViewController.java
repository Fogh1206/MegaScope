package client.view.login;

import client.view.ViewHandler;
import client.viewmodel.login.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.User.User;
import shared.util.EventType;

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


    /**
     * Method which is used to setup the controller.
     * Used as an constructor except it has to be called manually.
     *
     * @param loginViewModel
     * @param viewHandler
     * @param userLoggedIn
     */
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
            e.printStackTrace();
        }
        loginViewModel.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
    }

    /**
     * Method which happens when a user has logged in, server calls this method.
     * Method changes the scene of the stage to the frontpage.
     *
     * @param event is the user which will be logged into the frontpage.
     */

    private void newLogin(PropertyChangeEvent event) {

        User temp = (User) event.getNewValue();
        if (temp != null) {
            userLoggedIn = temp;
            loginViewModel.removePropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
            viewHandler.showFrontPage(userLoggedIn);
        }
    }


    /**
     * Method connected to FXML, so when button Login is pressed this method will run.
     * Method calls the login method from correspondent ViewModel and then sets the TextField objects content to nothing
     */
    public void onLoginAction() {
        loginViewModel.login();
    }


    /**
     * Method connected to FXML, so when button Cancel is pressed this method will run.
     */
    public void onCancelAction() {
        viewHandler.close();
    }

    /**
     * Method connected to FXML, so when button Register is pressed this method will run.
     * Method changes scene from the Login scene to the Register scene.
     */
    public void onRegisterAction() {
        loginViewModel.defaultFields();
        loginViewModel.removePropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
        viewHandler.openRegisterView();
    }

    /**
     * Method connected to FXML, so when button FrontPage is pressed this method will run.
     * Method changed scene from Login scene to FrontPage scene
     */
    public void frontPageButton() {
        loginViewModel.removePropertyChangeListener(EventType.LOGIN_RESULT.toString(), this::newLogin);
        viewHandler.showFrontPage(null);
    }


}
