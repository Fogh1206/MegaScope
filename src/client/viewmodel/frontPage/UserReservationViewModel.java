package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.*;
import shared.Reservation.Reservation;
import shared.User.User;
import shared.UserReservationInfo.UserReservationInfo;
import shared.UserReservationInfo.UserReservationInfoList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserReservationViewModel implements PropertyChangeSubject
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
    this.time = new SimpleStringProperty();
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
    UserReservationInfoList userReservationInfos = (UserReservationInfoList) event
            .getNewValue();
    ObservableList<UserReservationInfo> observableList = FXCollections.observableArrayList();
    for (int i = 0; i <userReservationInfos.getSize() ; i++) {
      observableList.add(userReservationInfos.get(i));
    }

    observableItems.setValue(observableList);
  }
  /**
   * Void method gets user reservations
   *
   * @param user
   */
  public void getUserReservations(User user) {
    model.getUserReservations(user);
  }

  /**
   * Method for returning observableItems for the simpleListProperty
   *
   * @return observableItems
   */
  public SimpleListProperty<UserReservationInfo> observableItemsProperty() {
    return observableItems;
  }

  /**
   * Void method for cancelling reservation
   *
   * @param userReservationInfo
   */
  public void cancelReservation(UserReservationInfo userReservationInfo) {
    model.cancelReservation(userReservationInfo);
  }

  /**
   * Void method adds listener
   *
   * @param name
   * @param listener
   */
  @Override
  public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
    support.addPropertyChangeListener(name, listener);
  }

  @Override
  public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
    support.removePropertyChangeListener(support.getPropertyChangeListeners()[0]);
  }
}

