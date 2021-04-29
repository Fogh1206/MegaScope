package client.viewmodel.admin;

import client.model.UserModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import shared.Movie;

public class AdminViewModelMovie
{
  private Property<ObservableList<Movie>> observableItems;
  private ObservableList<Movie> items;

  private UserModel userModel;

  public AdminViewModelMovie(UserModel userModel){
    this.userModel = userModel;
    items = new SimpleListProperty<>();
    observableItems = new SimpleListProperty<>();
  }


  public Property<ObservableList<Movie>> observableItemsProperty()
  {
    return observableItems;
  }
}
