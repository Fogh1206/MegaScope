package client.view.frontPage;

import client.view.CustomTextFieldTableCell;
import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import shared.Movie;
import shared.User;

import java.beans.PropertyChangeEvent;

public class FrontPageController
{

  @FXML private VBox Profile;


  private UserFrontPageViewModel userFrontPageViewModel;
  private ViewHandler viewHandler;

  private User userLoggedIn;

  @FXML private Label usernameLabel;
  @FXML private Button loginButton;

  @FXML private TableView<Movie> movieTableView;
  @FXML private TableColumn<Object, String> movieTitleCol;
  @FXML private TableColumn<Object, String> mainactorsCol;
  @FXML private TableColumn<Object, String> timeCol;
  @FXML private TableColumn<Object, String> DateCol;
  @FXML private TableColumn<Object, String> dateOfReleaseCol;
  @FXML private TableColumn<Object, String> descriptionCol;

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
    dateOfReleaseCol.setCellValueFactory(new PropertyValueFactory<>("dateOfRelease"));
    mainactorsCol.setCellValueFactory(new PropertyValueFactory<>("mainActors"));
    descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("timeOfShow"));
    DateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfShow"));

    movieTitleCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    mainactorsCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    timeCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    DateCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    dateOfReleaseCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());
    descriptionCol.setCellFactory(CustomTextFieldTableCell.forTableColumn());

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
