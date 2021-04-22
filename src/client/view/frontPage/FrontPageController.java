package client.view.frontPage;

import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.Movie;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class FrontPageController
{

  private UserFrontPageViewModel userFrontPageViewModel;
  private ViewHandler viewHandler;
  private PropertyChangeSupport support = new PropertyChangeSupport(this);

  @FXML private Label usernameLabel;
  @FXML private Button loginButton;
  @FXML private TableView<Movie> movieTableView;
  @FXML private TableColumn<String, Movie> movieTitleCol;
  @FXML private TableColumn<String, Movie> dateOfReleaseCol;

  public void init(UserFrontPageViewModel frontPage, ViewHandler viewHandler)
  {
    this.userFrontPageViewModel = frontPage;
    this.viewHandler = viewHandler;

    usernameLabel.textProperty()
        .bindBidirectional(userFrontPageViewModel.usernameProperty());

    movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateOfReleaseCol
        .setCellValueFactory(new PropertyValueFactory<>("dateOfRelease"));

    movieTableView.setItems(userFrontPageViewModel.getItems());
    System.out.println("369" + movieTableView.getItems().toString());

    userFrontPageViewModel
        .addPropertyChangeListener("Update",
            this::update);
  }

  private void update(PropertyChangeEvent event)
  {
    System.out.println("Last");
    movieTableView.setItems(userFrontPageViewModel.getItems());
  }

  public void onLoginButton(ActionEvent event)
  {
    viewHandler.openLoginView();
  }

  public void onBookMovieButton()
  {

  }


}
