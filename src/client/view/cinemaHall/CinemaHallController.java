package client.view.cinemaHall;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public class CinemaHallController
{
  @FXML public GridPane gridPaneSeats;
  @FXML private TextArea textSeats;
  private CinemaHallViewModel cinemaHallViewModel;
  private UserModel userModel;
  private ViewHandler viewHandler;

  String[][] myBooking = new String[4][6];

  public void init(CinemaHallViewModel cinemaHallViewModel, ViewHandler viewHandler)
  {
    this.cinemaHallViewModel = cinemaHallViewModel;
    this.viewHandler = viewHandler;

    System.out.println(gridPaneSeats.getChildren().size());
    System.out.println(gridPaneSeats.getRowCount());
    System.out.println(gridPaneSeats.getColumnCount());

    for (int row = 0; row < gridPaneSeats.getRowCount(); row++)
    {
      for (int column = 0; column < gridPaneSeats.getColumnCount(); column++)
      {
       Rectangle rectangle = new Rectangle();

        rectangle.setWidth(70);
        rectangle.setHeight(60);

        if (row>2)
        {
          rectangle.setFill(Color.GOLD);
        }
        else if(row<=2)
        {
          rectangle.setFill(Color.GREEN);
        }
        int finalI = row;
        int finalJ = column;


        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
          @Override
          public void handle(MouseEvent t) {

            if (rectangle.getFill() == Color.RED)
            {
              rectangle.setFill(Color.GREEN);
              myBooking[finalI][finalJ] = null;
            } else if (rectangle.getFill() == Color.GREEN)
            {
              rectangle.setFill(Color.RED);
              myBooking[finalI][finalJ] = "Row[" + finalI + "] Seat[" + finalJ + "] Booked";

            }

          }
        });
        gridPaneSeats.add(rectangle,column,row);
      }
    }


    System.out.println(gridPaneSeats.getChildren().size());
  }

  @FXML private void updateSeats() {
    textSeats.clear();

    for (int i = 0; i < myBooking.length; i++)
    {
      for (int j = 0; j < myBooking[i].length; j++)
      {
        if (myBooking[i][j] != null)
          textSeats.appendText(myBooking[i][j] + "\n");
      }
    }
  }

  public void frontPageButton()
  {
    viewHandler.showFrontPage(null);
  }

    @FXML private void confirmSeats () {

    }


}

