package client.view.frontPage;

import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;
import client.viewmodel.login.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.File;

public class FrontPageController {

    private UserFrontPageViewModel userFrontPageViewModel;
    private ViewHandler viewHandler;

    @FXML private Label usernameLabel;
    @FXML private Button loginButton;
    @FXML private Button logoutButton;



  public void init(UserFrontPageViewModel frontPage,ViewHandler viewHandler)
  {

    this.userFrontPageViewModel = frontPage;
    this.viewHandler = viewHandler;

    usernameLabel.textProperty().bindBidirectional(userFrontPageViewModel.usernameProperty());

  }

    public void onLoginButton(ActionEvent event) {
      viewHandler.openLoginView();
    }


}
