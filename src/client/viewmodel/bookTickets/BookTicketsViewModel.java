package client.viewmodel.bookTickets;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.*;
import shared.util.EventType;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

public class BookTicketsViewModel {
    private UserModel model;
    private PropertyChangeSupport support;
    private ReservationList reservationList;

    private SeatList seatList;
    private SeatList changedSeatList;

    private HashMap<String, ObjectProperty<Paint>> colorIdMap;
    private StringProperty failLabelProperty;

    public BookTicketsViewModel(UserModel model) {
        this.model = model;
        colorIdMap = new HashMap<>();
        support = new PropertyChangeSupport(this);
        reservationList = new ReservationList();
        changedSeatList = new SeatList();
        failLabelProperty = new SimpleStringProperty();

        for (int i = 1; i < 26; i++) {

            colorIdMap.put("" + i, new SimpleObjectProperty<>(Color.GREEN));
        }

        model.addPropertyChangeListener(EventType.GETRESERVATIONS_RESULT.toString(), this::onGetReservations);
        model.addPropertyChangeListener(EventType.GETRESERVATIONSFAIL_RESULT.toString(), this::onGetReservationsFail);
        model.addPropertyChangeListener(EventType.GETADMINSEATS_RESULT.toString(), this::onGetAdminSeats);
    }

    private void onGetReservationsFail(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            reservationList = new ReservationList();
            ArrayList<String> list = (ArrayList<String>) event.getNewValue();
            System.out.println(list.toString() + " Hello Guys");
            for (int i = 0; i < list.size(); i++) {
                colorIdMap.get(list.get(i)).setValue(Color.RED);
            }
            failLabelProperty.setValue("The seat is already taken");
        });
    }

    /**
     * Method to update colors for seat-rectangles for admin with information from server
     */
    private void onGetAdminSeats(PropertyChangeEvent event) {
        reservationList = new ReservationList();
        seatList = (SeatList) event.getNewValue();
        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).isDisabled()) {
                colorIdMap.get("" + seatList.get(i).getId()).setValue(Color.RED);
            } else {
                colorIdMap.get("" + seatList.get(i).getId()).setValue(Color.GREEN);
            }
        }
    }

    /**
     * Method to reset colors for seat-rectangles
     */
    public void resetColors() {
        for (int i = 1; i < 26; i++) {
            colorIdMap.get("" + i).setValue(Color.GREEN);
        }
    }

    /**
     * Method to update colors for seat-rectangles for non-admin with information from server
     */
    private void onGetReservations(PropertyChangeEvent event) {
        reservationList = new ReservationList();
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();

        System.out.println(list.toString() + " Hello Guys");
        for (String s : list) {
            colorIdMap.get(s).setValue(Color.RED);
        }
    }

    /**
     * Method that through model and client send request for ReservationList for specific show
     */
    public void getReservation(MovieShow movieShow) {
        model.getReservation(movieShow);
    }

    public Property<Paint> getFillProperty(String id) {
        try {
            return colorIdMap.get("" + id);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Property<Boolean> getDisableProperty(String id) {
        return null;
    }

    /**
     * Method to update disableProperty for seat-rectangles
     */
    public void disableProperty(String id) {
        Property<Paint> objectProperty = getFillProperty(id);
        objectProperty.setValue(Color.GRAY);
    }

    /**
     * Method to confirm User Reservations
     */
    public void confirmSeats(User user) {
        if (user.getUserType().equals("ADMIN")) {
            System.out.println("Called confirm seats");
            model.adminConfirmSeats(changedSeatList);
            changedSeatList = new SeatList();
        } else {
            model.confirmSeats(reservationList);
        }

    }

    /**
     * Method for adding a Reservation to reservationList
     */
    public void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }

    /**
     * Method to update changedSeatList with changes about seats about disability
     */
    public void addDisabledSeat(String str) {
        seatList.get(Integer.parseInt(str) - 1).setDisabled(!seatList.get(Integer.parseInt(str) - 1).isDisabled());
        changedSeatList.set(seatList.get(Integer.parseInt(str) - 1));
        System.out.println(changedSeatList.size() + "size of new list");
    }

    /**
     * Method that through model and client send request for ReservationList for admin
     */
    public void getAdminSeats() {
        model.getAdminSeats();
    }

    public Property<String> getFailLabelProperty() {
        return failLabelProperty;
    }
}
