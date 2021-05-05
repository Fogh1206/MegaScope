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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import shared.Movie;
import shared.NewRegisteredUser;
import shared.User;

import java.beans.PropertyChangeEvent;

public class FrontPageController
{

  @FXML public TextField searchBar;
  @FXML private Button myProfileButton;
  @FXML private HBox UserHBox;
  @FXML private AnchorPane SearchBox;
  @FXML private Label LabelHAHA;
  @FXML private VBox Profile;

  private UserFrontPageViewModel userFrontPageViewModel;
  private ViewHandler viewHandler;

  private NewRegisteredUser userLoggedIn;

  @FXML private Label usernameLabel;
  @FXML private Button loginButton;
  @FXML private Button cinemaHallButton;

  @FXML private TableView<Movie> movieTableView;
  @FXML private TableColumn<Object, String> movieTitleCol;
  @FXML private TableColumn<Object, String> mainactorsCol;
  @FXML private TableColumn<Object, String> timeCol;
  @FXML private TableColumn<Object, String> DateCol;
  @FXML private TableColumn<Object, String> dateOfReleaseCol;
  @FXML private TableColumn<Object, String> descriptionCol;
  @FXML private DatePicker datePicker;

  public void init(UserFrontPageViewModel frontPage, ViewHandler viewHandler,
      NewRegisteredUser userLoggedIn)
  {
    this.userFrontPageViewModel = frontPage;
    userFrontPageViewModel.getMovies();
    this.viewHandler = viewHandler;
    this.userLoggedIn = userLoggedIn;
    if (userLoggedIn != null)
    {
      SearchBox.setMinHeight(90);
      UserHBox.setMaxHeight(50);
      LabelHAHA.setVisible(true);
      myProfileButton.setVisible(true);
      LabelHAHA.setText("Logged in as " + userLoggedIn.getUsername());
      loginButton.setText("Log Out");
    }
    else
    {
      LabelHAHA.setVisible(false);
      myProfileButton.setVisible(false);
      UserHBox.setMaxHeight(1);
      SearchBox.setMinHeight(140);
      loginButton.setText("Log In");
    }

    searchBar.textProperty()
        .bindBidirectional(userFrontPageViewModel.searchPhraseProperty());
    movieTableView.itemsProperty()
        .bindBidirectional(userFrontPageViewModel.observableItemsProperty());

    movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateOfReleaseCol
        .setCellValueFactory(new PropertyValueFactory<>("dateOfRelease"));
    mainactorsCol.setCellValueFactory(new PropertyValueFactory<>("mainActors"));
    descriptionCol
        .setCellValueFactory(new PropertyValueFactory<>("description"));
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
      viewHandler.showFrontPage(null);
    }
    else
    {
      viewHandler.openLoginView(userLoggedIn);
    }
  }

  public void onBookMovieButton()
  {
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

  @FXML public void goToMyProfile()
  {
    if (userLoggedIn != null)
    {
      System.out.println(userLoggedIn.getUsername());
      System.out.println(userLoggedIn.getId());
      viewHandler.showUserProfile(userLoggedIn);
    }
  }

  public void Search(ActionEvent actionEvent)
  {
    userFrontPageViewModel.search();
  }
}
