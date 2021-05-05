package client.viewmodel.admin;

import client.model.UserModel;
import javafx.beans.property.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Movie;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminViewModelMovie
{
  private UserModel model;
  private PropertyChangeSupport support;

  private StringProperty username, searchPhrase;
  private ObjectProperty datePicked;
  private Property<ObservableList<Movie>> observableItems;
  private ObservableList<Movie> items;



  public AdminViewModelMovie(UserModel userModel){
    this.model = userModel;
    support = new PropertyChangeSupport(this);
    username = new SimpleStringProperty();
    datePicked = new SimpleObjectProperty();
    searchPhrase = new SimpleStringProperty();
    items = new SimpleListProperty<>();
    observableItems = new SimpleListProperty<>();
  }

  public void onGetMovies(PropertyChangeEvent event){
    List<Movie> list = (ArrayList<Movie>) event.getNewValue();

    ObservableList<Movie> observableList = FXCollections.observableArrayList();
    observableList.addAll(list);
    observableItems.setValue(observableList);
    items = FXCollections.observableArrayList(list);

  }

  public void getMovies(){
    model.getMovies();
  }

  public ObservableList<Movie> getItems(){
    return items;
  }

  public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
    support.addPropertyChangeListener(name, listener);
  }

  public void close()
  {
    model.deactivateClient();
  }

  public Property<ObservableList<Movie>> observableItemsProperty()
  {
    return observableItems;
  }

  public StringProperty searchPhraseProperty()
  {
    return searchPhrase;
  }

  public void search()
  {
    if (searchPhrase.getValue() == null || searchPhrase.getValue().equals(""))
    {

      getMovies();
    }
    else
    {
      try
      {
        getMovies();
        Thread.sleep(500);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      ObservableList<Movie> observableList = FXCollections
              .observableArrayList();
      for (int i = 0; i < observableItems.getValue().size(); i++)
      {
        System.out.println(observableItems.getValue().get(i).getName());
        System.out.println(searchPhrase.toString());
        if (observableItems.getValue().get(i).getName()
                .contains(searchPhrase.getValue()))
        {
          observableList.add(observableItems.getValue().get(i));
        }
      }
      observableItems.setValue(observableList);
    }
    searchPhrase.setValue(null);
  }

  public void onDatePick()
  {
    System.out.println(datePicked.get().toString());

    if (datePicked.get() == null)
    {
      getMovies();
    }
    else
    {
      try
      {
        getMovies();
        Thread.sleep(1000);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      ObservableList<Movie> observableList = FXCollections
              .observableArrayList();

      for (int i = 0; i < observableItems.getValue().size(); i++)
      {
        System.out.println("C"+observableItems.getValue().get(i).getName());
        System.out.println("D"+searchPhrase.toString());
        if (datePicked.get().toString()
                .equals(observableItems.getValue().get(i).getDateOfShow()))
        {
          observableList.add(observableItems.getValue().get(i));
        }
      }

      observableItems.setValue(observableList);

    }
  }

  public Property<LocalDate> getValue()
  {
    return datePicked;
  }

}
