package client.viewmodel.admin;

import client.model.UserModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
  private StringProperty banButton = new SimpleStringProperty();

  private NewRegisteredUser selectedUser;

  public AdminViewModelUsers(UserModel userModel)
  {
    this.userModel = userModel;
    observableItems = new SimpleListProperty<>();
    support = new PropertyChangeSupport(this);
    items = new SimpleListProperty<>();
    userModel.addPropertyChangeListener("Users Result", this::onGetUsers);
    searchPhrase = new SimpleStringProperty();
    selectedUser = null;
    banButton.setValue("Ban");
  }

  private void onGetUsers(PropertyChangeEvent event)
  {

    List<NewRegisteredUser> list = (ArrayList<NewRegisteredUser>) event
        .getNewValue();

    ObservableList<NewRegisteredUser> observableList = FXCollections
        .observableArrayList();
    observableList.addAll(list);
    observableItems.setValue(observableList);
    items = FXCollections.observableArrayList(list);
  }

  public StringProperty banButtonProperty()
  {
    return banButton;
  }

  public StringProperty searchPhraseProperty()
  {
    return searchPhrase;
  }

  public Property<ObservableList<NewRegisteredUser>> observableItemsProperty()
  {
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

  public void search()
  {

    if (searchPhrase.getValue() == null || searchPhrase.getValue().equals(""))
    {
      System.out.println("Please");
      getUsers();
    }
    else
    {
      ObservableList<NewRegisteredUser> observableList = FXCollections
          .observableArrayList();
      for (int i = 0; i < observableItems.getValue().size(); i++)
      {
        System.out.println(observableItems.getValue().get(i).getUsername());

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

  public void getUsers()
  {
    userModel.getUsers();
  }

  public void manageUsers()
  {

    if (selectedUser != null)
    {
      System.out.println("Test1 " + selectedUser.getBanned());
      selectedUser.setBanned(!selectedUser.getBanned());
      System.out.println("Test2 " + selectedUser.getPhoneNumber());
      System.out.println("1" + selectedUser.toString());

      NewRegisteredUser user = new NewRegisteredUser(selectedUser.getId(),
          selectedUser.getFirstName(), selectedUser.getLastName(), selectedUser.getUsername(),
          selectedUser.getPassword(), selectedUser.getPhoneNumber(), selectedUser.getUserType(),
         selectedUser.getBanned());
      userModel.saveNewInfo(user);
      getUsers();
    }
  }

  public void selectedUserToModel(NewRegisteredUser user)
  {

    System.out.println("He im here " + user);
    selectedUser = user;
    System.out.println("           " + user.getBanned());
  }
}
