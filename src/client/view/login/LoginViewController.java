package client.view.login;

import client.view.ViewHandler;
import client.viewmodel.login.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.NewRegisteredUser;

import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.io.File;

public class LoginViewController
{

  @FXML private TextField usernameTextField;
  @FXML private TextField passwordTextField;

  @FXML private ImageView imageView;
  @FXML private Label loginText;
  @FXML private Button frontPageButton;
  @FXML private Button cinemaHallButton;
  private NewRegisteredUser userLoggedIn;

  private LoginViewModel loginViewModel;
  private ViewHandler viewHandler;

  public void init(LoginViewModel loginViewModel, ViewHandler viewHandler,
      NewRegisteredUser userLoggedIn)
  {
    this.userLoggedIn = userLoggedIn;
    this.loginViewModel = loginViewModel;
    this.viewHandler = viewHandler;
    usernameTextField.textProperty()
        .bindBidirectional(loginViewModel.usernameProperty());
    passwordTextField.textProperty()
        .bindBidirectional(loginViewModel.passwordProperty());
    loginText.textProperty()
        .bindBidirectional(loginViewModel.loginResultProperty());
    try
    {
      File imageFIle = new File("images/cinema.jpg");
      Image image = new Image(imageFIle.toURI().toString());
      imageView.setImage(image);

    }
    catch (NullPointerException e)
    {
      System.out.println("image probl");
    }

    loginViewModel.addPropertyChangeListener(EventType.LOGIN_RESULT.toString(),
        this::newLogin);
  }

  private void newLogin(PropertyChangeEvent event)
  {
    NewRegisteredUser temp = (NewRegisteredUser) event.getNewValue();
    System.out.println("What");
    if (temp != null)
    {
      userLoggedIn = temp;
      viewHandler.showFrontPage(temp);
    }
  }

  public NewRegisteredUser UserLoggedIn()
  {
    return userLoggedIn;
  }

  public void onLoginAction(ActionEvent actionEvent)
  {
    if (userLoggedIn != null)
    {
    }

    loginViewModel.login();
    loginViewModel.defaultFields();

  }

  public void onCancelAction()
  {
    viewHandler.close();
    System.out.println("Cancelled");
  }

  public void onRegisterAction()
  {
    loginViewModel.defaultFields();
    viewHandler.openRegisterView();
  }

  public void frontPageButton()
  {
    viewHandler.showFrontPage(null);
  }

  public void cinemaHallButton()
  {

  }

}
