package client.view.frontPage;

import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import shared.Movie;
import shared.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class FrontPageController
{

  public VBox Profile;
  private UserFrontPageViewModel userFrontPageViewModel;
  private ViewHandler viewHandler;

  private User userLoggedIn;

  @FXML private Label usernameLabel;
  @FXML private Button loginButton;

  @FXML private TableView<Movie> movieTableView;
  @FXML private TableColumn<String, Movie> movieTitleCol;
  @FXML private TableColumn<String, Movie> dateOfReleaseCol;

  public void init(UserFrontPageViewModel frontPage, ViewHandler viewHandler,
      User userLoggedIn)
  {

    this.userFrontPageViewModel = frontPage;
    userFrontPageViewModel.getMovies();
    this.viewHandler = viewHandler;
    this.userLoggedIn = userLoggedIn;
    if (userLoggedIn != null)
    {
      loginButton.setText("Log Out");
    }
    else
    {
      loginButton.setText("Log In");
    }
    movieTableView.itemsProperty()
        .bindBidirectional(userFrontPageViewModel.observableItemsProperty());

    usernameLabel.textProperty()
        .bindBidirectional(userFrontPageViewModel.usernameProperty());

    movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateOfReleaseCol
        .setCellValueFactory(new PropertyValueFactory<>("dateOfRelease"));

    userFrontPageViewModel.addPropertyChangeListener("Update", this::update);
    movieTableView.setItems(userFrontPageViewModel.getItems());
    setSelectedMovie();
  }

  private void update(PropertyChangeEvent event)
  {
    System.out.println("Upadate Movies");
    movieTableView.setItems(userFrontPageViewModel.getItems());
  }

  public void onLoginButton(ActionEvent event)
  {
    if (userLoggedIn != null)
    {
      System.out.println(userLoggedIn.getUsername());
    }
    else
    {
      viewHandler.openLoginView();
    }
  }

  public void onBookMovieButton()
  {
    userFrontPageViewModel.getMovies();
    //    if (dateOfReleaseCol.isVisible())
    //    {
    //      dateOfReleaseCol.setVisible(false);
    //      movieTitleCol.setMaxWidth(150);
    //      movieTableView.setMaxWidth(150);
    //      Profile.setVisible(true);
    //      Profile.setMaxWidth(550);
    //      movieTableView.setItems(userFrontPageViewModel.getItems());
    //    }
    //    else
    //    {
    //      dateOfReleaseCol.setVisible(true);
    //      movieTableView.setMaxWidth(600);
    //      movieTitleCol.setMinWidth(300);
    //      Profile.setVisible(false);
    //      Profile.setMaxWidth(0);
    //    }
  }

  /**
   * Sets the selectedMovie.
   */
  private void setSelectedMovie()
  {
    movieTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (movieTableView.getSelectionModel().getSelectedItem() != null)
            {
              int index = movieTableView.getSelectionModel().getSelectedIndex();
              System.out.println(movieTableView.getItems().get(index));
            }
          }
        });
  }

  public void StupidAction(ActionEvent actionEvent)
  {
    System.out.println("STupid");
  }
}
