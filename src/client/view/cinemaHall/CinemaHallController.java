package client.view.cinemaHall;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import shared.Reservation;
import shared.Show;
import shared.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class CinemaHallController {
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

    public void init(CinemaHallViewModel cinemaHallViewModel,
                     ViewHandler viewHandler, User user, Show show) {
        this.cinemaHallViewModel = cinemaHallViewModel;
        cinemaHallViewModel.resetColors();
        this.viewHandler = viewHandler;
        this.user = user;

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

        System.out.println(gridPaneSeats.getChildren().size());

    }

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
                                "Row[" + finalRow + "] Seat[" + finalCol + "] " + rectangle.getId() + "enabled";
                        cinemaHallViewModel.addDisabledSeat(rectangle.getId());
                    } else if (rectangle.getFill() == Color.GREEN) {

                        rectangle.setFill(Color.RED);
                        cinemaHallViewModel.addDisabledSeat(rectangle.getId());
                        // TODO use an integer instead of an reservation to sendt the seats to ViewModel as ADMIN

                        myBooking[finalRow][finalCol] =
                                "Row[" + finalRow + "] Seat[" + finalCol + "] " + rectangle.getId() + "disabled";
                    }
                    updateSeats();
                });
                rectangle.fillProperty().bindBidirectional(cinemaHallViewModel.getFillProperty(rectangle.getId()));
            }
        }
    }


    private void updateSeats() {
        textSeats.clear();

        for (int i = 0; i < myBooking.length; i++) {
            for (int j = 0; j < myBooking[i].length; j++) {
                if (myBooking[i][j] != null)
                    textSeats.appendText(myBooking[i][j] + "\n");
            }
        }
    }

    public void frontPageButton() {
        viewHandler.showFrontPage(user);
    }

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

