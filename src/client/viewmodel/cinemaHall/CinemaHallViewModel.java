package client.viewmodel.cinemaHall;

import client.model.UserModel;


import java.beans.PropertyChangeSupport;

public class CinemaHallViewModel
{
  private UserModel model;
  private PropertyChangeSupport support;


  public CinemaHallViewModel(UserModel model)
  {
    this.model = model;
    support = new PropertyChangeSupport(this);

  }
}
