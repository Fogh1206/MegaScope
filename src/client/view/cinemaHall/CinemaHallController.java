package client.view.cinemaHall;

import client.model.UserModel;
import client.view.ViewHandler;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import javafx.fxml.FXML;

public class CinemaHallController
{
  private CinemaHallViewModel cinemaHallViewModel;
  private UserModel userModel;
  private ViewHandler viewHandler;

  public void init(CinemaHallViewModel cinemaHallViewModel, ViewHandler viewHandler)
  {
    this.cinemaHallViewModel = cinemaHallViewModel;
    this.viewHandler = viewHandler;
  }

  @FXML private void confirmSeats()
  {

  }
}
