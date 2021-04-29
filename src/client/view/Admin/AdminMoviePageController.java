package client.view.Admin;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.admin.AdminViewModelMovie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.Movie;

public class AdminMoviePageController
{

  @FXML public TableColumn<String, Movie> titleColumn;
  @FXML public TableColumn<String, Movie> releaseColumn;
  @FXML public TableColumn<String, Movie> actorsColumn;
  @FXML public TableColumn<String, Movie> descriptionColumn;
  @FXML public TableColumn<String, Movie> timeColumn;
  @FXML public TableColumn<String, Movie> DateColumn;
  @FXML private TableView<Movie> adminMovieTable;
  private AdminViewModelMovie adminViewModelMovie;
  private UserModel userModel;
  private ViewHandler viewHandler;

  public void init(AdminViewModelMovie adminViewModelMovie,
      ViewHandler viewHandler)
  {
    this.adminViewModelMovie = adminViewModelMovie;
    this.viewHandler = viewHandler;
    this.adminViewModelMovie = adminViewModelMovie;

    adminMovieTable.itemsProperty()
        .bindBidirectional(adminViewModelMovie.observableItemsProperty());
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    releaseColumn
        .setCellValueFactory(new PropertyValueFactory<>("dateofrelease"));
    actorsColumn.setCellValueFactory(new PropertyValueFactory<>("mainactors"));
    descriptionColumn
        .setCellValueFactory(new PropertyValueFactory<>("description"));

    //Not completed
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

  public void addMovie(ActionEvent actionEvent)
  {

  }
}

