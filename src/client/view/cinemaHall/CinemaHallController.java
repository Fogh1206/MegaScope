package client.view.cinemaHall;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import javafx.fxml.FXML;

import java.awt.*;

public class CinemaHallController
{
  private CinemaHallViewModel cinemaHallViewModel;
  private UserModel userModel;
  private ViewHandler viewHandler;
 // private TextArea textSeats;

  public void init(CinemaHallViewModel cinemaHallViewModel, ViewHandler viewHandler)
  {
    this.cinemaHallViewModel = cinemaHallViewModel;
    this.viewHandler = viewHandler;
  }



 /* @FXML private void row1Seat1Click()
  {
    textSeats.append("Row 1, seat 1");
  }

  */



  @FXML private void confirmSeats()
  {

  }
}
