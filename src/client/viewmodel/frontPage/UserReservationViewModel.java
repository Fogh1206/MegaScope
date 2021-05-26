package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Reservation;
import shared.User;
import shared.UserReservationInfo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class UserReservationViewModel
{

  /**
   * Instance field
   */
  private UserModel model;
  private PropertyChangeSupport support;
  private Reservation selectedReservation;

  private StringProperty movieTitle, time, date, seat;

  private SimpleListProperty<UserReservationInfo> observableItems;

  /**
   * Constructor
   *
   * @param model
   */
  public UserReservationViewModel(UserModel model)
  {
    this.model = model;
    support = new PropertyChangeSupport(this);
    movieTitle = new SimpleStringProperty();
    time = new SimpleStringProperty();
    date = new SimpleStringProperty();
    seat = new SimpleStringProperty();
    observableItems = new SimpleListProperty<>();

    model.addPropertyChangeListener("Reservations result",
        this::onGetReservations);
  }

  /**
   * Void method shows all reservations from the user
   *
   * @param event
   */
  public void onGetReservations(PropertyChangeEvent event)
  {
    ArrayList<UserReservationInfo> userReservationInfos = (ArrayList<UserReservationInfo>) event
        .getNewValue();
    ObservableList<UserReservationInfo> observableList = FXCollections
        .observableArrayList();
    observableList.addAll(userReservationInfos);
    observableItems.setValue(observableList);
  }

  /**
   * Void method adds listener
   *
   * @param name
   * @param listener
   */
  public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
  }

  /**
   * Void method gets user reservations
   *
   * @param user
   */
  public void getUserReservations(User user)
  {
    model.getUserReservations(user);
  }

  /**
   * Method for returning observableItems for the simpleListProperty
   *
   * @return observableItems
   */
  public SimpleListProperty<UserReservationInfo> observableItemsProperty()
  {
    return observableItems;
  }

  /**
   * Void method for cancelling reservation
   *
   * @param userReservationInfo
   */
  public void cancelReservation(UserReservationInfo userReservationInfo)
  {

    model.cancelReservation(userReservationInfo);

  }
}
