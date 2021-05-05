package client.viewmodel.admin;

import client.model.UserModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Movie;
import shared.NewRegisteredUser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class AdminViewModelUsers
{
  private UserModel userModel;
  private Property<ObservableList<NewRegisteredUser>> observableItems;
  private ObservableList<NewRegisteredUser> items;
  private PropertyChangeSupport support;
  private StringProperty searchPhrase;

  public AdminViewModelUsers(UserModel userModel)
  {
    this.userModel = userModel;
    observableItems = new SimpleListProperty<>();
    support = new PropertyChangeSupport(this);
    items = new SimpleListProperty<>();
    userModel.addPropertyChangeListener("Users Result", this::onGetUsers);
    searchPhrase = new SimpleStringProperty();
  }

  private void onGetUsers(PropertyChangeEvent event) {

    List<NewRegisteredUser> list = (ArrayList<NewRegisteredUser>) event.getNewValue();

    ObservableList<NewRegisteredUser> observableList = FXCollections.observableArrayList();
    observableList.addAll(list);
    observableItems.setValue(observableList);
    items = FXCollections.observableArrayList(list);
  }


  public Property<ObservableList<NewRegisteredUser>> observableItemsProperty() {
    return observableItems;
  }
  public void addPropertyChangeListener(String name,
                                        PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
  }
  public ObservableList<NewRegisteredUser> getItems()
  {
    return items;
  }

  public void search() {

    if (searchPhrase.getValue() == null || searchPhrase.getValue().equals(""))
    {
      System.out.println("Please");
      getUsers();
    }
    else
    {
      try
      {
        getUsers();
        Thread.sleep(500);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      ObservableList<NewRegisteredUser> observableList = FXCollections
              .observableArrayList();
      for (int i = 0; i < observableItems.getValue().size(); i++)
      {

        if (observableItems.getValue().get(i).getUsername()
                .contains(searchPhrase.getValue()))
        {
          observableList.add(observableItems.getValue().get(i));
        }
      }
      observableItems.setValue(observableList);
    }
    searchPhrase.setValue(null);
  }



  public void getUsers() {
    userModel.getUsers();
  }
}
