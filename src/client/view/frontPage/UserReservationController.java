package client.view.frontPage;


import client.view.ViewHandler;
import client.viewmodel.frontPage.UserReservationViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.User;
import shared.UserReservationInfo;

import java.io.File;


/**
 * A class to control user reservation GUI
 */
public class UserReservationController {

    /**
     * Instance field
     */
    @FXML
    private Label usernameLabel;
    @FXML
    private TableView<UserReservationInfo> reservationTableView;
    @FXML
    private TableColumn<Object, String> reservationIdCol;
    @FXML
    private TableColumn<Object, String> movieTitleCol;
    @FXML
    private TableColumn<Object, String> timeCol;
    @FXML
    private TableColumn<Object, String> dateCol;
    @FXML
    private TableColumn<Object, String> seatCol;
    @FXML
    private ImageView logoView;

    private UserReservationViewModel userReservationViewModel;
    private ViewHandler viewHandler;
    private User userLoggedIn;
    private UserReservationInfo selectedInfo;

    /**
     * Initialising UserReservationController fields and binding values
     *
     * @param userReservationViewModel UserReservationViewModel instance for ViewModel
     * @param viewHandler              object used handling the views
     * @param userLoggedIn             object used temporarily storing the User
     */
    public void init(UserReservationViewModel userReservationViewModel, ViewHandler viewHandler, User userLoggedIn) {
        this.userReservationViewModel = userReservationViewModel;
        userReservationViewModel.getUserReservations(userLoggedIn);

        this.viewHandler = viewHandler;

        reservationTableView.itemsProperty().bindBidirectional(userReservationViewModel.observableItemsProperty());
        reservationIdCol.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time_show"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date_show"));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("seat_id"));

        this.userLoggedIn = userLoggedIn;
        usernameLabel.setText("Logged in as " + userLoggedIn.getUsername());
        try {
            File logoFile = new File("images/logo.png");
            Image logo = new Image(logoFile.toURI().toString());
            logoView.setImage(logo);
        } catch (NullPointerException e) {
            System.out.println("image problem");
        }
    }

    /**
     * Method from FX onAction that Cancels the reservation
     */
    public void onCancelReservation() {
        if (selectedInfo != null) {
            userReservationViewModel.cancelReservation(selectedInfo);
            selectedInfo=null;
        }
    }

    /**
     * Method from FX onAction that changes the view to FrontPage
     */
    public void onGoBack() {
        viewHandler.showFrontPage(userLoggedIn);
    }

    /**
     * Method for setting selected reservation
     */
    public void setSelected() {
        if (reservationTableView.getSelectionModel().getSelectedItem() != null) {
            int index = reservationTableView.getSelectionModel().getSelectedIndex();
            selectedInfo = reservationTableView.getItems().get(index);
            System.out.println(selectedInfo);
        }
    }
}
