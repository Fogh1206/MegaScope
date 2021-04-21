package client.view.Admin;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.admin.AdminViewModelMovie;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;

public  class AdminMoviePageController
{

  private AdminViewModelMovie adminViewModelMovie;
  private UserModel userModel;
  private ViewHandler viewHandler;

  public void init(AdminViewModelMovie adminViewModelMovie, ViewHandler viewHandler)
  {
    this.adminViewModelMovie = adminViewModelMovie;
    this.viewHandler = viewHandler;
  }

  @FXML private void addMovie(ActionEvent event)
  {

  }

  @FXML private void editMovie(ActionEvent event)
  {

  }

  @FXML private void removeMovie(ActionEvent event)
  {

  }

  @FXML private void manageUsers(ActionEvent event)
  {

  }



}

