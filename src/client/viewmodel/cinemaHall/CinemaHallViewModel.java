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
    private ArrayList<ObjectProperty<Paint>> colors;
    private ReservationList reservationList;

    private SeatList seatList;
    private SeatList changedSeatList;

    private HashMap<String, ObjectProperty<Paint>> colorIdMap;

    public CinemaHallViewModel(UserModel model) {
        this.model = model;
        colors = new ArrayList<>();
        colorIdMap = new HashMap<>();
        support = new PropertyChangeSupport(this);
        reservationList = new ReservationList();
        changedSeatList = new SeatList();

        for (int i = 0; i < 26; i++) {
            colors.add(i, new SimpleObjectProperty<>(Color.GREEN));
            colorIdMap.put("" + i, colors.get(i));
            System.out.println(colorIdMap.size());
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

                colors.get(seatList.get(i).getId()).setValue(Color.RED);
            } else {
                colors.get(seatList.get(i).getId()).setValue(Color.GREEN);
            }
        }

        colors.get(0).setValue(Color.WHITE);
    }

    public void resetColors() {
        for (int i = 0; i < 26; i++) {
            colors.get(i).setValue(Color.GREEN);
        }
    }

    private void onGetReservations(PropertyChangeEvent event) {
        reservationList = new ReservationList();
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();

        System.out.println(list.toString() + " Hello Guys");
        for (int i = 0; i < list.size(); i++) {
            if (colorIdMap.get(list.get(i)) != null) {
                colors.get(Integer.valueOf(list.get(i))).setValue(Color.RED);

            }
        }
    }

    public void getReservation(Show show) {
        model.getReservation(show);
    }

    public Property<Paint> getFillProperty(String id) {
        try {
            return colors.get(Integer.valueOf(id));
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
        } else {
            model.confirmSeats(reservationList);
        }
        changedSeatList = new SeatList();
    }

    public void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }

    public void addDisabledSeat(String str) {
        System.out.println(str);
        if (seatList.get(Integer.parseInt(str) - 1).isDisabled()) {
            seatList.get(Integer.parseInt(str) - 1).setDisabled(false);
            changedSeatList.set(seatList.get(Integer.parseInt(str) - 1));
        } else {
            seatList.get(Integer.parseInt(str) - 1).setDisabled(true);
            changedSeatList.set(seatList.get(Integer.parseInt(str) - 1));
        }
    }

    public void getAdminSeats() {
        model.getAdminSeats();
    }
}
