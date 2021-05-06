package client.view.frontPage;

import client.view.CustomTextFieldTableCell;
import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.Movie;
import shared.NewRegisteredUser;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;

public class FrontPageController
{

  @FXML private TextField searchBar;
  @FXML private DatePicker datePick;
  @FXML private Button myProfileButton;
  @FXML private HBox UserHBox;
  @FXML private AnchorPane SearchBox;
  @FXML private Label LabelHAHA;
  @FXML private VBox Profile;

  @FXML private Label usernameLabel;
  @FXML private Button loginButton;
  @FXML private Button cinemaHallButton;
  @FXML private Button manageUsersButton;

  @FXML private HBox adminContainer;

  @FXML private TableView<Movie> movieTableView;
  @FXML private TableColumn<Object, String> movieTitleCol;
  @FXML private TableColumn<Object, String> mainactorsCol;
  @FXML private TableColumn<Object, String> timeCol;
  @FXML private TableColumn<Object, String> DateCol;
  @FXML private TableColumn<Object, String> dateOfReleaseCol;
  @FXML private TableColumn<Object, String> descriptionCol;

  private UserFrontPageViewModel userFrontPageViewModel;
  private ViewHandler viewHandler;
  private Movie movie;

  private NewRegisteredUser userLoggedIn;

  public void init(UserFrontPageViewModel frontPage, ViewHandler viewHandler,
      NewRegisteredUser userLoggedIn)
  {

    adminContainer.setVisible(false);
    adminContainer.setDisable(true);
    manageUsersButton.setVisible(false);
    manageUsersButton.setDisable(true);

    this.userFrontPageViewModel = frontPage;
    userFrontPageViewModel.getMovies();

    this.viewHandler = viewHandler;
    this.userLoggedIn = userLoggedIn;
    if (userLoggedIn != null)
    {
      if(userLoggedIn.getUserType().equals("ADMIN")){
        manageUsersButton.setVisible(true);
        manageUsersButton.setDisable(false);
        adminContainer.setVisible(true);
        adminContainer.setDisable(false);
      }

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

    datePick.setDayCellFactory(picker -> new DateCell()
    {
      public void updateItem(LocalDate date, boolean empty)
      {
        super.updateItem(date, empty);
        setDisable(empty || date.compareTo(LocalDate.now()) < 1);
      }
    });

    datePick.valueProperty()
        .bindBidirectional(userFrontPageViewModel.getValue());

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
    userFrontPageViewModel.getMovies();
    //  userFrontPageViewModel.search();
  }

  public void onDatePick(ActionEvent actionEvent)
  {
    if (datePick.getValue() != null)
    {
      System.out.println("Hey");
      userFrontPageViewModel.getMovies();
    }
    //    userFrontPageViewModel.onDatePick();
  }

  public void onBookMovieButton(ActionEvent actionEvent)
  {
    viewHandler.showCinemaHallPage(userLoggedIn, movie);
  }


  public void setSelected(MouseEvent mouseEvent)
  {
    if (movieTableView.getSelectionModel().getSelectedItem() != null)
    {
      int index = movieTableView.getSelectionModel().getSelectedIndex();

      System.out.println(movieTableView.getItems().get(index));

      movie = movieTableView.getItems().get(index);
    }

  }

  public void onAddMovie(ActionEvent actionEvent){

    Stage popupWindow = new Stage();
    popupWindow.initModality(Modality.APPLICATION_MODAL);


    if(movie != null){
      userFrontPageViewModel.addMovie(movie);
    }
  }

  public void onEditMovie(ActionEvent actionEvent){

  }

  public void onRemoveMovie(ActionEvent actionEvent){

  }



}
