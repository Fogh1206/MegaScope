package client.view.cinemaHall;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;
import shared.Movie;
import shared.NewRegisteredUser;

import java.util.ArrayList;

public class CinemaHallController {
    @FXML
    public GridPane gridPaneSeats;
    @FXML
    private TextArea textSeats;
    private CinemaHallViewModel cinemaHallViewModel;
    private ArrayList<Integer> seats;
    private UserModel userModel;
    private ViewHandler viewHandler;
    private NewRegisteredUser user;
    private Movie movie;

    String[][] myBooking = new String[4][6];

    public void init(CinemaHallViewModel cinemaHallViewModel,
                     ViewHandler viewHandler, NewRegisteredUser user, Movie movie) {
        this.cinemaHallViewModel = cinemaHallViewModel;
        this.viewHandler = viewHandler;
        this.user = user;
        this.movie = movie;

        System.out.println(user.getUsername());
        System.out.println(movie.getName());
        System.out.println(gridPaneSeats.getChildren().size());
        System.out.println(gridPaneSeats.getRowCount());
        System.out.println(gridPaneSeats.getColumnCount());

        int id = 1;
        for (int row = 0; row < gridPaneSeats.getRowCount(); row++) {
            for (int column = 0; column < gridPaneSeats.getColumnCount(); column++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setId(String.valueOf(id));

                id++;
                rectangle.setWidth(70);
                rectangle.setHeight(60);
                //gridPaneSeats.setPadding(new Insets(0, 0, 0, 0));

                if (row > 2) {
                    rectangle.setFill(Color.DARKSLATEBLUE);
                } else if (row <= 2) {
                    rectangle.setFill(Color.GREEN);
                }
                int finalI = row;
                int finalJ = column;

                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {

                        if (rectangle.getFill() == Color.RED) {
                            rectangle.setFill(Color.GREEN);
                            myBooking[finalI][finalJ] = null;
                        } else if (rectangle.getFill() == Color.GREEN) {
                            rectangle.setFill(Color.RED);
                            myBooking[finalI][finalJ] =
                                    "Row[" + finalI + "] Seat[" + finalJ + "] " + rectangle.getId() + " Booked";

                        }
                    }
                });
                gridPaneSeats.add(rectangle, column, row);
                rectangle.fillProperty().bindBidirectional(cinemaHallViewModel.getFillProperty(rectangle.getId()));
                //rectangle.disableProperty().bindBidirectional(cinemaHallViewModel.getDisableProperty(rectangle.getId()));

            }
        }

        System.out.println(gridPaneSeats.getChildren().size());
        cinemaHallViewModel.getReservation(movie);
    }

    @FXML
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
        viewHandler.showFrontPage(null);
    }

    @FXML
    private void confirmSeats() {

    }

}

