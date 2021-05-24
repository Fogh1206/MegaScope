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
    private ArrayList<Integer> seats;
    private UserModel userModel;
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

        if (user.getUserType().equals("ADMIN")) {
            System.out.println("Open for admin");
            this.show = null;
            openForAdmin();
        } else {
            this.show = show;
            openForUser();
        }

        userLabel.setText(user.getUsername());

        try {
            File logoFile = new File("images/logo.png");
            Image logo = new Image(logoFile.toURI().toString());
            logoView.setImage(logo);
        } catch (NullPointerException e) {
            System.out.println("image problem");
        }

        System.out.println(gridPaneSeats.getChildren().size());

    }

    private void openForUser() {
        movieTitleLabel.setText(show.getName());

        int id = 1;
        for (int row = 0; row < gridPaneSeats.getRowCount(); row++) {
            for (int column = 0; column < gridPaneSeats.getColumnCount(); column++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setId(String.valueOf(id));
                createRectangle(rectangle);
                id++;
                gridPaneSeats.setPadding(new Insets(0, 0, 0, 40));

                if (row == 3) {
                    rectangle.setStyle("-fx-stroke: Gold; -fx-stroke-width: 5;");
                } else {
                    rectangle.setStyle("-fx-stroke: Black; -fx-stroke-width: 5;");
                }

                if (!user.getUserType().equals("VIP") && row == 3) {
                    rectangle.setDisable(true);
                    greenToGrey.setFill(Color.GREY);
                    cinemaHallViewModel.disableProperty(rectangle.getId());
                }

                int finalRow = row;
                int finalCol = column;

                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        System.out.println(rectangle.getFill().toString());
                        if (rectangle.getFill() == Color.YELLOW) {
                            rectangle.setFill(Color.GREEN);
                            myBooking[finalRow][finalCol] = null;
                        } else if (rectangle.getFill() == Color.GREEN) {
                            rectangle.setFill(Color.YELLOW);
                            Reservation reservation = new Reservation(Integer.valueOf(rectangle.getId()), show.getShow_id(), user.getId());
                            cinemaHallViewModel.addReservation(reservation);
                            myBooking[finalRow][finalCol] =
                                    "Row[" + finalRow + "] Seat[" + finalCol + "] " + rectangle.getId() + " Booked";
                        }
                        updateSeats();

                    }

                });
                gridPaneSeats.add(rectangle, column, row);
                rectangle.fillProperty().bindBidirectional(cinemaHallViewModel.getFillProperty(rectangle.getId()));
                //rectangle.disableProperty().bindBidirectional(cinemaHallViewModel.getDisableProperty(rectangle.getId()));
            }
        }
        cinemaHallViewModel.getReservation(show);
    }

    private void openForAdmin() {
        System.out.println("Call for admin");
        int id = 1;
        for (int row = 0; row < gridPaneSeats.getRowCount(); row++) {
            for (int column = 0; column < gridPaneSeats.getColumnCount(); column++) {
                Rectangle rectangle = new Rectangle();
                createRectangle(rectangle);
                rectangle.setId(String.valueOf(id));
                id++;
                if (row == 3) {
                    rectangle.setStyle("-fx-stroke: Gold; -fx-stroke-width: 5;");
                } else {
                    rectangle.setStyle("-fx-stroke: Black; -fx-stroke-width: 5;");
                }

                gridPaneSeats.setPadding(new Insets(0, 0, 0, 40));


                int finalRow = row;
                int finalCol = column;

                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        System.out.println(rectangle.getFill().toString());
                        if (rectangle.getFill() == Color.RED) {
                            rectangle.setFill(Color.GREEN);
                            myBooking[finalRow][finalCol] =
                                    "Row[" + finalRow + "] Seat[" + finalCol + "] " + rectangle.getId() + "enabled";
                            cinemaHallViewModel.addDisabledSeat(rectangle.getId());
                        } else if (rectangle.getFill() == Color.GREEN) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Warning");
                            alert.setHeaderText("You are about to block a seat");
                            alert.setContentText("Are you sure you want block the seat ["
                                    + rectangle.getId() + "]?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                rectangle.setFill(Color.RED);
                                cinemaHallViewModel.addDisabledSeat(rectangle.getId());
                                // TODO use an integer instead of an reservation to sendt the seats to ViewModel as ADMIN

                                myBooking[finalRow][finalCol] =
                                        "Row[" + finalRow + "] Seat[" + finalCol + "] " + rectangle.getId() + "disabled";
                            }
                        }

                        updateSeats();

                    }
                });
                gridPaneSeats.add(rectangle, column, row);
                rectangle.fillProperty().bindBidirectional(cinemaHallViewModel.getFillProperty(rectangle.getId()));
                //rectangle.disableProperty().bindBidirectional(cinemaHallViewModel.getDisableProperty(rectangle.getId()));
            }
        }
        cinemaHallViewModel.getAdminSeats();
    }

    private void createRectangle(Rectangle rectangle) {
        rectangle.setStyle("-fx-stroke: Black; -fx-stroke-width: 5;");
        rectangle.setWidth(70);
        rectangle.setHeight(60);
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
        cinemaHallViewModel.confirmSeats(user);
        textSeats.clear();
    }
}

