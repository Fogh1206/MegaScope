package client.view.Admin;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.admin.AdminViewModelMovie;
import client.viewmodel.admin.AdminViewModelUsers;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;

public class AdminUsersPageController
{
    private AdminViewModelUsers adminViewModelUsers;
    private UserModel userModel;
    private ViewHandler viewHandler;

  public void init(AdminViewModelUsers adminViewModelUsers, ViewHandler viewHandler)
  {
    this.adminViewModelUsers = adminViewModelUsers;
    this.viewHandler = viewHandler;
  }

  @FXML private void banUsers(ActionEvent event)
  {

  }

  @FXML private void manageMovies(ActionEvent event)
  {

  }
}
