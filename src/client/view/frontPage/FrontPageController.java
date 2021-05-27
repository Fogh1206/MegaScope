package client.view.frontPage;

import client.view.CustomTextFieldTableCell;
import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import shared.Show;
import shared.User;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

/**
 * A class to control the frontpage GUI
 */

public class FrontPageController {

    /**
     * Instance field
     */
    @FXML
    private TextField searchBar;
    @FXML
    private DatePicker datePick;
    @FXML
    private Button myProfileButton;
    @FXML
    private Label UsernameLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button myShowsButton;
    @FXML
    private Button manageUsersButton;
    @FXML
    private Button bookButton;
    @FXML
    private HBox adminContainer;
    @FXML
    private TableView<Show> movieTableView;
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
    @FXML
    private ImageView logoView;

    private UserFrontPageViewModel userFrontPageViewModel;
    private ViewHandler viewHandler;
    private Show selectedShow;
    private User userLoggedIn;

    /**
     * Initialising FrontPageController fields and binding values
     *
     * @param frontPage
     * @param viewHandler
     * @param userLoggedIn
     */
    public void init(UserFrontPageViewModel frontPage, ViewHandler viewHandler, User userLoggedIn) {
        System.out.println("Init");
        this.viewHandler = viewHandler;
        this.userFrontPageViewModel = frontPage;
        userFrontPageViewModel.getMovies();

        adminContainer.setVisible(false);
        adminContainer.setDisable(true);
        manageUsersButton.setVisible(false);
        manageUsersButton.setDisable(true);

        datePick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 1);
            }
        });

        datePick.valueProperty().bindBidirectional(userFrontPageViewModel.getValue());
        searchBar.textProperty().bindBidirectional(userFrontPageViewModel.searchPhraseProperty());
        movieTableView.itemsProperty().bindBidirectional(userFrontPageViewModel.observableItemsProperty());

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

        this.userLoggedIn = userLoggedIn;
        if (userLoggedIn != null) {
            myShowsButton.setVisible(true);
            UsernameLabel.setVisible(true);
            myProfileButton.setVisible(true);
            UsernameLabel.setText("Logged in as " + userLoggedIn.getUsername());
            loginButton.setText("Log Out");

            if (userLoggedIn.getUserType().equals("ADMIN")) {
                manageUsersButton.setVisible(true);
                manageUsersButton.setDisable(false);
                adminContainer.setVisible(true);
                adminContainer.setDisable(false);
                myShowsButton.setVisible(false);
                bookButton.setText("Manage Seats");
            }
        } else {
            UsernameLabel.setVisible(false);
            myProfileButton.setVisible(false);
            myShowsButton.setVisible(false);
            loginButton.setText("Log In");
            bookButton.setVisible(false);
        }
        try {
            File logoFile = new File("images/logo.png");
            Image logo = new Image(logoFile.toURI().toString());
            logoView.setImage(logo);
        } catch (NullPointerException e) {
            System.out.println("image problem");
        }
    }

    /**
     * Method opens the login page if the user is not logged in. If the user is
     * logged in it will logout.
     */
    public void onLoginButton() {
        if (userLoggedIn != null) {
            viewHandler.showFrontPage(null);
        } else {
            viewHandler.openLoginView(userLoggedIn);
        }
    }

    /**
     * Method opens user profile if the user is logged in.
     */
    @FXML
    public void goToMyProfile() {
        if (userLoggedIn != null) {
            System.out.println(userLoggedIn.getUsername());
            viewHandler.openUserProfile(userLoggedIn);
        }
    }

    /**
     * Searching for movies
     */
    public void Search() {
        userFrontPageViewModel.getMovies();
    }

    /**
     * Method for showing movies for the chosen date
     */
    @FXML
    public void onDatePick() {
        LocalDate date = datePick.getValue();
        System.err.println("Selected date: " + date);
        userFrontPageViewModel.getMovies();
    }

    /**
     * Opens cinemahall page for users
     */
    public void onBookMovieButton() {
        if (userLoggedIn != null && selectedShow != null) {
            viewHandler.openCinemaHallPage(userLoggedIn, selectedShow);
        } else if (userLoggedIn.getUserType().equals("ADMIN")) {
            viewHandler.openCinemaHallPage(userLoggedIn, null);
        }
    }

    /**
     * Method for setting selected show from the front page
     */
    public void setSelected() {
        if (movieTableView.getSelectionModel().getSelectedItem() != null) {
            int index = movieTableView.getSelectionModel().getSelectedIndex();
            selectedShow = movieTableView.getItems().get(index);
            userFrontPageViewModel.selectedMovie(movieTableView.getItems().get(index));
        }
    }

    /**
     * Method opens manage users if admin is logged in
     */
    public void onManageUsers() {
        viewHandler.openAdminUsersPage(userLoggedIn);
    }

    /**
     * Method opens add movie page if admin is logged in
     */
    public void onAddMovie() {
        viewHandler.openAddMovieView();
    }

    /**
     * Method opens edit movie page if admin is logged in
     */
    public void onEditMovie() {
        if (selectedShow != null) {
            viewHandler.openEditMovie(selectedShow);
        }
    }

    /**
     * Method opens an alert box and removes movie from the front page.
     */
    public void onRemoveMovie() {
        if (selectedShow != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You are about to delete a movie from the database");
            alert.setContentText(
                    "Are you sure you want to delete the movie [" + selectedShow.getName()
                            + "] from the movie database?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                userFrontPageViewModel.removeMovie();
            }
        } else {
            System.out.println("no movie");
        }
    }

    /**
     * Method opens User reservation page if the user is logged in
     */
    public void onMyShowsButton() {
        viewHandler.showUserReservationPage(userLoggedIn);
    }

    /***
     * The enter key will execute the search method
     * @param keyEvent
     */
    public void onEnter(KeyEvent keyEvent) {

        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Search();
        }
    }
}
