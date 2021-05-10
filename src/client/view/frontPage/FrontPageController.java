package client.view.frontPage;

import client.view.CustomTextFieldTableCell;
import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import shared.Movie;
import shared.NewRegisteredUser;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.Optional;

public class FrontPageController {

    @FXML
    private TextField searchBar;
    @FXML
    private DatePicker datePick;
    @FXML
    private Button myProfileButton;
    @FXML
    private HBox UserHBox;
    @FXML
    private AnchorPane SearchBox;
    @FXML
    private Label UsernameLabel;
    @FXML
    private VBox Profile;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button cinemaHallButton;
    @FXML
    private Button manageUsersButton;

    @FXML
    private HBox adminContainer;

    @FXML
    private TableView<Movie> movieTableView;
    @FXML
    private TableColumn<Object, String> movieTitleCol;
    @FXML
    private TableColumn<Object, String> mainactorsCol;
    @FXML
    private TableColumn<Object, String> timeCol;
    @FXML
    private TableColumn<Object, String> DateCol;
    @FXML
    private TableColumn<Object, String> dateOfReleaseCol;
    @FXML
    private TableColumn<Object, String> descriptionCol;

    private UserFrontPageViewModel userFrontPageViewModel;
    private ViewHandler viewHandler;
    private Movie selectedMovie;
    private NewRegisteredUser userLoggedIn;

    public void init(UserFrontPageViewModel frontPage, ViewHandler viewHandler,
                     NewRegisteredUser userLoggedIn) {

        adminContainer.setVisible(false);
        adminContainer.setDisable(true);
        manageUsersButton.setVisible(false);
        manageUsersButton.setDisable(true);

        this.userFrontPageViewModel = frontPage;
        userFrontPageViewModel.getMovies();

        this.viewHandler = viewHandler;
        this.userLoggedIn = userLoggedIn;
        if (userLoggedIn != null) {
            if (userLoggedIn.getUserType().equals("ADMIN")) {
                manageUsersButton.setVisible(true);
                manageUsersButton.setDisable(false);
                adminContainer.setVisible(true);
                adminContainer.setDisable(false);
            }

            UsernameLabel.setVisible(true);
            myProfileButton.setVisible(true);
            UsernameLabel.setText("Logged in as " + userLoggedIn.getUsername());
            loginButton.setText("Log Out");

        } else {
            UsernameLabel.setVisible(false);
            myProfileButton.setVisible(false);
            loginButton.setText("Log In");
        }

        datePick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
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

    //    userFrontPageViewModel.addPropertyChangeListener("Update", this::update);


        setSelectedMovie();

    }

    private void update(PropertyChangeEvent event) {
        System.out.println("Update Movies");
        movieTableView.setItems(userFrontPageViewModel.getItems());
    }

    public void onLoginButton() {
        if (userLoggedIn != null) {
            viewHandler.showFrontPage(null);
        } else {
            viewHandler.openLoginView(userLoggedIn);
        }
    }

    /**
     * Sets the selectedMovie.
     */
    private void setSelectedMovie() {
        movieTableView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener() {
                    public void changed(ObservableValue observableValue, Object oldValue,
                                        Object newValue) {
                        if (movieTableView.getSelectionModel().getSelectedItem() != null) {
                            int index = movieTableView.getSelectionModel().getSelectedIndex();

                            System.out.println(movieTableView.getItems().get(index));
                        }
                    }
                });
    }

    @FXML
    public void goToMyProfile() {
        if (userLoggedIn != null) {
            System.out.println(userLoggedIn.getUsername());
            System.out.println(userLoggedIn.getId());
            viewHandler.openUserProfile(userLoggedIn);
        }
    }

    public void Search() {
        userFrontPageViewModel.getMovies();
    }

    public void onDatePick() {
        if (datePick.getValue() != null) {
            System.out.println("Hey");
            userFrontPageViewModel.getMovies();
        }
    }

    public void onBookMovieButton() {
        viewHandler.openCinemaHallPage(userLoggedIn, selectedMovie);
    }


    public void setSelected() {
        if (movieTableView.getSelectionModel().getSelectedItem() != null) {
            int index = movieTableView.getSelectionModel().getSelectedIndex();
            selectedMovie = movieTableView.getItems().get(index);
            userFrontPageViewModel.selectedMovie(movieTableView.getItems().get(index));
        }
    }

    public void onManageUsers() {
        viewHandler.openAdminUsersPage(userLoggedIn);
    }

    public void onAddMovie() {
        Optional<Movie> movie = viewHandler.openAddMovieWindow().showAndWait();

        if (movie.isPresent()) {
            userFrontPageViewModel.addMovie(movie.get());
        }
    }

    public void onEditMovie() {
        if (userFrontPageViewModel.getSelectedMovie() != null) {
            Optional<Movie> movie = viewHandler.openEditMovieWindow(userFrontPageViewModel.getSelectedMovie()).showAndWait();
            if (movie.isPresent()) {
                userFrontPageViewModel.editMovie(movie.get());
            }
        }
    }

    public void onRemoveMovie() {
        if (selectedMovie != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You are about to delete a movie from the database");
            alert.setContentText("Are you sure you want to delete the movie [" + selectedMovie.getName() + "] from the movie database?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                userFrontPageViewModel.removeMovie();
            }
        } else {
            System.out.println("no movie");
        }
    }
}
