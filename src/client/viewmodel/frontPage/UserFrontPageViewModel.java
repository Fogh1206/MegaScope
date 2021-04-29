package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Movie;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class UserFrontPageViewModel
{

  private UserModel model;
  private PropertyChangeSupport support;

  private StringProperty username, button;
  private ObservableList<Movie> items;

  public UserFrontPageViewModel(UserModel userModel)
  {
    this.model = userModel;
    support = new PropertyChangeSupport(this);
    username = new SimpleStringProperty();
    button = new SimpleStringProperty();
    items = new SimpleListProperty<>();
    model.addPropertyChangeListener(EventType.GETMOVIES_RESULT.toString(),
        this::onGetMovies);
  }

  private void onGetMovies(PropertyChangeEvent event)
  {
    System.out.println(6);
    List<Movie> list = (ArrayList<Movie>) event.getNewValue();
    items = FXCollections.observableArrayList(list);
    support.firePropertyChange("Update", null,
        null);
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


  public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
  }

  public void close()
  {
    model.deactivateClient();
  }
}
