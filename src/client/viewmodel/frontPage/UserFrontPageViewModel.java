package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Movie;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class UserFrontPageViewModel
{

  public Property<ObservableList<Movie>> getNewItem;
  private UserModel model;

  private StringProperty username, button;
  private ObservableList<Movie> items;

  public UserFrontPageViewModel(UserModel userModel)
  {
    this.model = userModel;
    username = new SimpleStringProperty();
    button = new SimpleStringProperty();
    items = new SimpleListProperty<>();
    model.addPropertyChangeListener(EventType.GETMOVIES_RESULT.toString(),
        this::onGetMovies);

    System.out.println(5);
  }

  private void onGetMovies(PropertyChangeEvent event)
  {
    System.out.println(6);
    List<Movie> list = (ArrayList<Movie>) event.getNewValue();

    items = FXCollections.observableArrayList(list);

    System.out.println("HAHA" + items.toString());
  }

  public StringProperty usernameProperty()
  {
    return username;
  }

  public StringProperty buttonProperty()
  {
    return button;
  }

  public void getMovies()
  {

  }

  public ObservableList<Movie> getItems()
  {
    return items;
  }

  public ObservableList<Movie> getGetNewItem()
  {
    return getNewItem.get();
  }

  public Property<ObservableList<Movie>> getNewItemProperty()
  {
    return getNewItem;
  }
}
