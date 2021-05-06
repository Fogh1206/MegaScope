package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import shared.Movie;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserFrontPageViewModel
{

  private UserModel model;
  private PropertyChangeSupport support;

  private StringProperty username, button;
  private StringProperty searchPhrase;
  private ObjectProperty datePicked;
  private Property<ObservableList<Movie>> observableItems;
  private ObservableList<Movie> items;

  public UserFrontPageViewModel(UserModel model)
  {
    this.model = model;
    support = new PropertyChangeSupport(this);
    username = new SimpleStringProperty();
    datePicked = new SimpleObjectProperty();
    button = new SimpleStringProperty();
    searchPhrase = new SimpleStringProperty();
    items = new SimpleListProperty<>();
    observableItems = new SimpleListProperty<>();

    System.out.println("start");
    model.addPropertyChangeListener("Movie Result", this::onGetMovies);
    System.out.println("Koniec");

  }

  public void onGetMovies(PropertyChangeEvent event)
  {
    List<Movie> list = (ArrayList<Movie>) event.getNewValue();

    ObservableList<Movie> observableList = FXCollections.observableArrayList();
    observableList.addAll(list);
    System.out.println("Kappa " + observableList.size());
    observableItems.setValue(observableList);
    items = FXCollections.observableArrayList(list);
    if (searchPhrase.getValue() == null || searchPhrase.getValue().equals(""))
    {
      System.out.println("Please");
    }
    else
    {
      search();
    }
    System.out.println(datePicked.get());
    if (datePicked.get() == null)
    {
      System.out.println("Please datePick");
    }
    else
    {
      onDatePick();
    }
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
    model.getMovies();
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
      ObservableList<Movie> observableList = FXCollections
          .observableArrayList();
      for (int i = 0; i < observableItems.getValue().size(); i++)
      {
        if (observableItems.getValue().get(i).getName()
            .contains(searchPhrase.getValue()))
        {
          observableList.add(observableItems.getValue().get(i));
        }
      }
      observableItems.setValue(observableList);

    searchPhrase.setValue(null);
  }

  public void onDatePick()
  {
    ObservableList<Movie> observableList = FXCollections.observableArrayList();

    for (int i = 0; i < observableItems.getValue().size(); i++)
    {
      if (datePicked.get().toString()
          .equals(observableItems.getValue().get(i).getDateOfShow()))
      {
        observableList.add(observableItems.getValue().get(i));
      }
    }
    observableItems.setValue(observableList);

    datePicked.setValue(null);
  }

  public Property<LocalDate> getValue()
  {
    return datePicked;
  }

}
