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
import shared.Movie;
import shared.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class FrontPageController
{

  private UserFrontPageViewModel userFrontPageViewModel;
  private ViewHandler viewHandler;
  private PropertyChangeSupport support = new PropertyChangeSupport(this);
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
    this.viewHandler = viewHandler;
    this.userLoggedIn = userLoggedIn;
    if (userLoggedIn != null)
    {
      loginButton.setText("LogOut");
    }
    else
    {
      loginButton.setText("Log In");
    }

    usernameLabel.textProperty()
        .bindBidirectional(userFrontPageViewModel.usernameProperty());

    movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateOfReleaseCol
        .setCellValueFactory(new PropertyValueFactory<>("dateOfRelease"));

    movieTableView.setItems(userFrontPageViewModel.getItems());

    userFrontPageViewModel.addPropertyChangeListener("Update", this::update);

    setSelectedMovie();
  }

  private void update(PropertyChangeEvent event)
  {
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

}
