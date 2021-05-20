package client.view.frontPage;

import client.view.CustomTextFieldTableCell;
import client.view.ViewHandler;
import client.viewmodel.frontPage.UserFrontPageViewModel;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import shared.Show;
import shared.User;

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

    private UserFrontPageViewModel userFrontPageViewModel;
    private ViewHandler viewHandler;
    private Show selectedShow;
    private User userLoggedIn;

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
        movieTableView.itemsProperty()
                .bindBidirectional(userFrontPageViewModel.observableItemsProperty());

        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfReleaseCol
                .setCellValueFactory(new PropertyValueFactory<>("dateOfRelease"));
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
    }

    public void onLoginButton() {
        if (userLoggedIn != null) {
            viewHandler.showFrontPage(null);
        } else {
            viewHandler.openLoginView(userLoggedIn);
        }
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
        System.out.println(searchBar.getText());
        userFrontPageViewModel.getMovies();
    }

    @FXML
    public void onDatePick() {
        System.out.println("Calling the datePicker");

            LocalDate date = datePick.getValue();
            System.err.println("Selected date: " + date);
            userFrontPageViewModel.getMovies();


//        System.out.println("Bad");
//        System.out.println( datePick.onActionProperty().toString(););
//
//        datePick.setOnAction(new EventHandler() {
//            public void handle(Event t) {
//                LocalDate date = datePick.getValue();
//                if (datePick.getValue() != null) {
//                    userFrontPageViewModel.getMovies();
//                    System.err.println("Selected date not null: " + date);
//                }
//                System.err.println("Selected date: " + date);
//            }
//        });

    }

    public void onBookMovieButton() {

        if (userLoggedIn != null && selectedShow != null) {
            viewHandler.openCinemaHallPage(userLoggedIn, selectedShow);
        }

    }

    public void setSelected() {
        if (movieTableView.getSelectionModel().getSelectedItem() != null) {
            int index = movieTableView.getSelectionModel().getSelectedIndex();
            selectedShow = movieTableView.getItems().get(index);
            userFrontPageViewModel.selectedMovie(movieTableView.getItems().get(index));
        }
    }

    public void onManageUsers() {
        viewHandler.openAdminUsersPage(userLoggedIn);
    }

    public void onAddMovie() {
        viewHandler.openAddMovieView();
    }

    public void onEditMovie() {
        viewHandler.openEditMovie(selectedShow);
    }

    public void onRemoveMovie() {
        if (selectedShow != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You are about to delete a movie from the database");
            alert.setContentText("Are you sure you want to delete the movie [" + selectedShow
                    .getName() + "] from the movie database?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                userFrontPageViewModel.removeMovie();
            }
        } else {
            System.out.println("no movie");
        }
    }

    public void onMyShowsButton(ActionEvent actionEvent) {
        viewHandler.showUserReservationPage(userLoggedIn);
    }

}
