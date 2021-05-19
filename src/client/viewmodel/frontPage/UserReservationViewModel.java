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

public class UserReservationViewModel {

    private UserModel model;
    private PropertyChangeSupport support;
    private Reservation selectedReservation;

    private StringProperty movieTitle, time, date, seat;

    private SimpleListProperty<UserReservationInfo> observableItems;



    public UserReservationViewModel(UserModel model){
        this.model = model;
        support = new PropertyChangeSupport(this);
        movieTitle = new SimpleStringProperty();
        time = new SimpleStringProperty();
        date = new SimpleStringProperty();
        seat = new SimpleStringProperty();
        observableItems = new SimpleListProperty<>();

        model.addPropertyChangeListener("Reservations result", this::onGetReservations);
    }

    public void onGetReservations(PropertyChangeEvent event){

        ArrayList<ArrayList<String>> stringList = (ArrayList<ArrayList<String>>) event.getNewValue();
        ArrayList<UserReservationInfo> userReservationInfos = new ArrayList<>();
        for(int i = 0 ; i < stringList.size() ; i++){
            UserReservationInfo userReservationInfo = new UserReservationInfo(stringList.get(i));
            userReservationInfos.add(userReservationInfo);
        }

        ObservableList<UserReservationInfo> observableList = FXCollections.observableArrayList();
        observableList.addAll(userReservationInfos);
        observableItems.setValue(observableList);
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    public void getUserReservations(User user) {
        model.getUserReservations(user);
    }

    public SimpleListProperty<UserReservationInfo> observableItemsProperty() {
        return observableItems;
    }


}
