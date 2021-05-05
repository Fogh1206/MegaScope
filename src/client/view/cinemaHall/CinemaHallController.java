package client.view.cinemaHall;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class CinemaHallController
{
  @FXML public GridPane gridPaneSeats;
  private CinemaHallViewModel cinemaHallViewModel;
  private UserModel userModel;
  private ViewHandler viewHandler;
  @FXML private TextArea textSeats;

  private ArrayList<Rectangle> seats;

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
          rectangle.setFill(Color.RED);
        }
        int finalI = row;
        int finalJ = column;

        //rectangle.setOnMouseClicked(e-> System.out.println("Row =" + finalI + " Column = " + finalJ));

        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
          @Override
          public void handle(MouseEvent t) {
            // TODO
            System.out.println("Row =" + finalI + " Column = " + finalJ);
          }
        });
        gridPaneSeats.add(rectangle,column,row);

      }
    }
    System.out.println(gridPaneSeats.getChildren().size());
  }


  @FXML private void confirmSeats()
  {

  }

  public void click(MouseEvent mouseEvent)
  {

  }

}
