package client.view.cinemaHall;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

 // private TextArea textSeats;

  public void init(CinemaHallViewModel cinemaHallViewModel, ViewHandler viewHandler)
  {
    this.cinemaHallViewModel = cinemaHallViewModel;
    this.viewHandler = viewHandler;
    gridPaneSeats.setVisible(false);

   /* if (gridPaneSeats.getChildren() == null)
    {
      System.out.println("Gridpane seats are null!!!!!!!!!!!");
    }

    */
//    System.out.println("col"+gridPaneSeats.getId());
//    System.out.println(gridPaneSeats.getChildren().size());
//
//    for (int i = 0; i < gridPaneSeats.getRowCount(); i++)
//    {
//      for (int j = 0; j < gridPaneSeats.getColumnCount(); j++)
//      {
//        gridPaneSeats.getChildren().add(new Rectangle());
//      }
//    }
//
//    for (int i = 0; i < gridPaneSeats.getChildren().size(); i++)
//    {
//      seats.add((Rectangle) gridPaneSeats.getChildren().get(i));
//      seats.get(i).setOnMouseClicked(e-> confirmSeats());
//    }
  }



 /* @FXML private void row1Seat1Click()
  {
    textSeats.append("Row 1, seat 1");
  }

  */




  @FXML private void confirmSeats()
  {

  }

  public void click(MouseEvent mouseEvent)
  {

  }
}
