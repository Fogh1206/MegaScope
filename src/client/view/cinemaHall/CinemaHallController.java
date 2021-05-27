package client.view.cinemaHall;

import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import shared.Reservation;
import shared.Show;
import shared.User;

import java.io.File;

import java.util.Optional;

public class CinemaHallController {
    @FXML
    private Label failLabel;
    @FXML
    private Rectangle greenToGrey;
    @FXML
    public GridPane gridPaneSeats;
    @FXML
    public Label movieTitleLabel;
    @FXML
    public Label userLabel;
    @FXML
    private ImageView logoView;
    @FXML
    private TextArea textSeats;
    private CinemaHallViewModel cinemaHallViewModel;
    private ViewHandler viewHandler;
    private User user;
    private Show show;

    String[][] myBooking = new String[4][6];


    /**
     * Initializing Method for the GUI components
     *
     * @param cinemaHallViewModel CinemaHallViewModel instance for ViewModel
     * @param viewHandler         object used handling the views
     * @param userLoggedIn        object used temporarily storing the User
     */
    public void init(CinemaHallViewModel cinemaHallViewModel,
                     ViewHandler viewHandler, User userLoggedIn, Show show) {
        this.cinemaHallViewModel = cinemaHallViewModel;
        this.viewHandler = viewHandler;
        this.user = userLoggedIn;

        gridPaneSeats.setPadding(new Insets(0, 0, 0, 40));
        if (user.getUserType().equals("ADMIN")) {
            System.out.println("Open for admin");
            this.show = null;
            openForAdmin();
        } else {
            this.show = show;
            openForUser();
        }

        userLabel.setText("Logged in as " + user.getUsername());

        try {
            File logoFile = new File("images/logo.png");
            Image logo = new Image(logoFile.toURI().toString());
            logoView.setImage(logo);
        } catch (NullPointerException e) {
            System.out.println("image problem");
        }

        failLabel.textProperty().bindBidirectional(cinemaHallViewModel.getFailLabelProperty());
    }

    /**
     * Method for creating the GUI components
     *
     * @param rectangle Rectangle object for representating the seats
     * @param row       row of new rectangle object
     * @param column    column of new rectangle object
     */
    private void createRectangle(Rectangle rectangle, int row, int column) {
        rectangle.setStyle("-fx-stroke: Black; -fx-stroke-width: 5;");
        rectangle.setWidth(70);
        rectangle.setHeight(60);
        if (row == 3) {
            rectangle.setStyle("-fx-stroke: Gold; -fx-stroke-width: 5;");
        } else {
            rectangle.setStyle("-fx-stroke: Black; -fx-stroke-width: 5;");
        }
        gridPaneSeats.add(rectangle, column, row);
    }


    /**
     * Method for initializing the fields and for binding the values in case of Non-Admin use.
     */
    private void openForUser() {
        movieTitleLabel.setText(show.getName());
        cinemaHallViewModel.getReservation(show);
        int id = 1;
        for (int row = 0; row < gridPaneSeats.getRowCount(); row++) {
            for (int column = 0; column < gridPaneSeats.getColumnCount(); column++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setId(String.valueOf(id));
                createRectangle(rectangle, row, column);
                id++;

                if (!user.getUserType().equals("VIP") && row == 3) {
                    rectangle.setDisable(true);
                    greenToGrey.setFill(Color.GREY);
                    cinemaHallViewModel.disableProperty(rectangle.getId());
                }

                int finalRow = row;
                int finalCol = column;

                rectangle.setOnMouseClicked(t -> {
                    System.out.println(rectangle.getFill().toString());
                    if (rectangle.getFill() == Color.YELLOW) {
                        rectangle.setFill(Color.GREEN);
                        myBooking[finalRow][finalCol] = null;
                    } else if (rectangle.getFill() == Color.GREEN) {
                        rectangle.setFill(Color.YELLOW);
                        Reservation reservation = new Reservation(Integer.parseInt(rectangle.getId()), show.getShow_id(), user.getId());
                        cinemaHallViewModel.addReservation(reservation);
                        myBooking[finalRow][finalCol] =
                                "Row[" + finalRow + "] Column[" + finalCol + "] " + rectangle.getId() + " Booked";
                    }
                    updateSeats();
                });
                rectangle.fillProperty().bindBidirectional(cinemaHallViewModel.getFillProperty(rectangle.getId()));
            }
        }
    }

    /**
     * Method for initializing the fields and for binding the values in case of Admin use.
     */
    private void openForAdmin() {
        System.out.println("Call for admin");
        cinemaHallViewModel.getAdminSeats();
        int id = 1;
        for (int row = 0; row < gridPaneSeats.getRowCount(); row++) {
            for (int column = 0; column < gridPaneSeats.getColumnCount(); column++) {
                Rectangle rectangle = new Rectangle();
                createRectangle(rectangle, row, column);
                rectangle.setId(String.valueOf(id));
                id++;

                int finalRow = row;
                int finalCol = column;

                rectangle.setOnMouseClicked(t -> {
                    System.out.println(rectangle.getFill().toString());
                    if (rectangle.getFill() == Color.RED) {
                        rectangle.setFill(Color.GREEN);
                        myBooking[finalRow][finalCol] =
                                "Row[" + finalRow + "] Seat[" + finalCol + "] " + rectangle.getId() + " enabled";
                        cinemaHallViewModel.addDisabledSeat(rectangle.getId());
                    } else if (rectangle.getFill() == Color.GREEN) {

                        rectangle.setFill(Color.RED);
                        cinemaHallViewModel.addDisabledSeat(rectangle.getId());
                        // TODO use an integer instead of an reservation to sendt the seats to ViewModel as ADMIN

                        myBooking[finalRow][finalCol] =
                                "Row[" + finalRow + "] Seat[" + finalCol + "] " + rectangle.getId() + " disabled";
                    }
                    updateSeats();
                });
                rectangle.fillProperty().bindBidirectional(cinemaHallViewModel.getFillProperty(rectangle.getId()));
            }
        }
    }

    /**
     * Method from FX onAction that updates the textField textSeats
     */
    private void updateSeats() {
        textSeats.clear();

        for (int i = 0; i < myBooking.length; i++) {
            for (int j = 0; j < myBooking[i].length; j++) {
                if (myBooking[i][j] != null)
                    textSeats.appendText(myBooking[i][j] + "\n");
            }
        }
    }

    /**
     * Method connected to FXML, so when button BackToTheFrontPage is pressed this method will run.
     * Method changes scene from to the Front Page scene.
     */
    public void frontPageButton() {
        cinemaHallViewModel.resetColors();
        viewHandler.showFrontPage(user);
    }

    /**
     * Method connected to FXML, so when button Confirm Seat is pressed this method will run.
     * Method calls the confirmSeats method from correspondent ViewModel and then sets the TextField objects content to nothing
     */
    public void confirmSeats() {

        if (user.getUserType().equals("ADMIN")) {
            if (textSeats.getText() != "" || !textSeats.getText().isEmpty() || !textSeats.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("You are about to block/unblock seats for users");
                alert.setContentText("Are you sure you want to save current setting of seats?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    cinemaHallViewModel.confirmSeats(user);
                    textSeats.clear();
                    myBooking = new String[4][6];
                }
            } else {
                System.out.println("No change");
            }
        } else {
            cinemaHallViewModel.confirmSeats(user);
            textSeats.clear();
            myBooking = new String[4][6];
        }
    }
}

