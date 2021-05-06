package client.view.Admin;

import client.model.UserModel;
import client.view.CustomTextFieldTableCell;
import client.view.ViewHandler;

import client.viewmodel.admin.AdminViewModelUsers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import shared.NewRegisteredUser;

import java.beans.PropertyChangeEvent;

public class AdminUsersPageController
{
 @FXML private Button banButton;
  private AdminViewModelUsers adminViewModelUsers;
  private UserModel userModel;
  private ViewHandler viewHandler;
  @FXML public TextField searchBar;
  @FXML private TableView<NewRegisteredUser> userTableView;
  @FXML private TableColumn<Object, String> usernameCol;
  @FXML private TableColumn<Object, String> firstNameCol;
  @FXML private TableColumn<Object, String> lastNameCol;
  @FXML private TableColumn<Object, String> phoneNoCol;

  private NewRegisteredUser userLoggedIn;

  public void init(AdminViewModelUsers adminViewModelUsers,
      ViewHandler viewHandler, NewRegisteredUser userLoggedIn)
  {
    this.adminViewModelUsers = adminViewModelUsers;
    this.viewHandler = viewHandler;
    this.userLoggedIn = userLoggedIn;
    adminViewModelUsers.getUsers();
    userTableView.itemsProperty()
        .bindBidirectional(adminViewModelUsers.observableItemsProperty());
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
    firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    searchBar.textProperty()
        .bindBidirectional(adminViewModelUsers.searchPhraseProperty());

    adminViewModelUsers.addPropertyChangeListener("Update", this::update);
    userTableView.setItems(adminViewModelUsers.getItems());
//    setSelectedUser();
    banButton.textProperty().bindBidirectional(adminViewModelUsers.banButtonProperty());

  }

  private void update(PropertyChangeEvent event)
  {
    System.out.println("Upadate Users");
    userTableView.setItems(adminViewModelUsers.getItems());
  }

  public void onBanAction(javafx.event.ActionEvent event)
  {

    adminViewModelUsers.manageUsers();


  }

  public void onBackAction(javafx.event.ActionEvent event)
  {
    viewHandler.showFrontPage(userLoggedIn);
  }



  public void Search(ActionEvent event)
  {
    adminViewModelUsers.search();
  }

  public void selectUser(MouseEvent mouseEvent)
  {

      if (userTableView.getSelectionModel().getSelectedItem() != null)
    {
        int index = userTableView.getSelectionModel().getSelectedIndex();
        if (userTableView.getItems().get(index).getBanned())
        {
            banButton.setText("Unban");
        }
        else banButton.setText("Ban");


      System.out.println("From new" + userTableView.getItems().get(index));
      adminViewModelUsers.selectedUserToModel(userTableView.getItems().get(index));
    }
  }
}
