package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Reservation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class UserReservationViewModel {

    private UserModel model;
    private PropertyChangeSupport support;
    private Reservation selectedReservation;

    private StringProperty movieTitle, time, date;
    private IntegerProperty seat;

    private Property<ObservableList> observableItems;



    public UserReservationViewModel(UserModel model){
        this.model = model;
        support = new PropertyChangeSupport(this);
        movieTitle = new SimpleStringProperty();
        time = new SimpleStringProperty();
        date = new SimpleStringProperty();
        seat = new SimpleIntegerProperty();

        model.addPropertyChangeListener("Reservations result", this::onGetReservations);
    }

    public void onGetReservations(PropertyChangeEvent event){
        ObservableList observableList = FXCollections.observableArrayList();

    }


}
