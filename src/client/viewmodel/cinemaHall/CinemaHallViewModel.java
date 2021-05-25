package client.viewmodel.cinemaHall;

import client.model.UserModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.*;
import shared.util.EventType;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

public class CinemaHallViewModel {
    private UserModel model;
    private PropertyChangeSupport support;
    private ReservationList reservationList;

    private SeatList seatList;
    private SeatList changedSeatList;

    private HashMap<String, ObjectProperty<Paint>> colorIdMap;

    public CinemaHallViewModel(UserModel model) {
        this.model = model;
        colorIdMap = new HashMap<>();
        support = new PropertyChangeSupport(this);
        reservationList = new ReservationList();
        changedSeatList = new SeatList();

        for (int i = 1; i < 26; i++) {

            colorIdMap.put("" + i, new SimpleObjectProperty<>(Color.GREEN));
        }

        model.addPropertyChangeListener(EventType.GETRESERVATIONS_RESULT.toString(), this::onGetReservations);
        model.addPropertyChangeListener(EventType.GETADMINSEATS_RESULT.toString(), this::onGetAdminSeats);
    }

    private void onGetAdminSeats(PropertyChangeEvent event) {
        reservationList = new ReservationList();
        seatList = (SeatList) event.getNewValue();
        System.out.println(seatList.size() + "Hi");
        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).isDisabled()) {
                colorIdMap.get(""+seatList.get(i).getId()).setValue(Color.RED);
            } else {
                colorIdMap.get(""+seatList.get(i).getId()).setValue(Color.GREEN);
            }
        }
    }

    public void resetColors() {
        for (int i = 1; i < 26; i++) {

            colorIdMap.get(""+i).setValue(Color.GREEN);
        }
    }

    private void onGetReservations(PropertyChangeEvent event) {
        reservationList = new ReservationList();
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();

        System.out.println(list.toString() + " Hello Guys");
        for (int i = 0; i < list.size(); i++) {
                colorIdMap.get(list.get(i)).setValue(Color.RED);
        }
    }

    public void getReservation(Show show) {
        model.getReservation(show);
    }

    public Property<Paint> getFillProperty(String id) {
        try {
            return colorIdMap.get(""+id);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Not in list
            return null;
        }
    }

    public Property<Boolean> getDisableProperty(String id) {
        return null;
    }

    public void disableProperty(String id) {
        Property<Paint> objectProperty = getFillProperty(id);
        objectProperty.setValue(Color.GRAY);
    }

    public void confirmSeats(User user) {
        if (user.getUserType().equals("ADMIN")) {
            System.out.println("Called confirm seats");
            model.adminConfirmSeats(changedSeatList);
            changedSeatList = new SeatList();
        } else {
            model.confirmSeats(reservationList);
        }

    }

    public void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }

    public void addDisabledSeat(String str) {
        seatList.get(Integer.parseInt(str) - 1).setDisabled(!seatList.get(Integer.parseInt(str) - 1).isDisabled());
        changedSeatList.set(seatList.get(Integer.parseInt(str) - 1));
        System.out.println(changedSeatList.size()+"size of new list");
    }

    public void getAdminSeats() {
        model.getAdminSeats();
    }
}
