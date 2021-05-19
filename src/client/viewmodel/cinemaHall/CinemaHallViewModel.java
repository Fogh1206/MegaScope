package client.viewmodel.cinemaHall;

import client.model.UserModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.Reservation;
import shared.ReservationList;
import shared.Show;
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

    private HashMap<String, ObjectProperty<Paint>> colorIdMap;

    public CinemaHallViewModel(UserModel model) {
        this.model = model;
        colors = new ArrayList<>();
        colorIdMap = new HashMap<>();
        support = new PropertyChangeSupport(this);
        reservationList = new ReservationList();

        for (int i = 0; i < 25; i++) {
            colors.add(i, new SimpleObjectProperty<>(Color.GREEN));
            colorIdMap.put("" + i, colors.get(i));
            System.out.println(colorIdMap.size());
        }

        model.addPropertyChangeListener(EventType.GETRESERVATIONS_RESULT.toString(), this::onGetReservations);
    }

    public void resetColors() {
        for (int i = 0; i < 25; i++) {
            colors.get(i).setValue(Color.GREEN);
        }
    }

    private void onGetReservations(PropertyChangeEvent event) {
        reservationList = new ReservationList();

        ArrayList<String> list = (ArrayList<String>) event.getNewValue();

        System.out.println(list.toString() + " Hello Guys");
        for (int i = 0; i < list.size(); i++) {
//            System.out.println("1" + list.get(i));
//            System.out.println("2" + Integer.valueOf(list.get(i)));
//            System.out.println("3 " + colorIdMap.get((list.get(i))));
            if (colorIdMap.get(list.get(i)) != null) {
                colors.get(Integer.valueOf(list.get(i))).setValue(Color.RED);
//                System.out.println("4" + colors.get(i));
//                System.out.println("5" + colorIdMap.get(list.get(i)));
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

    public void confirmSeats() {
        model.confirmSeats(reservationList);
    }

    public void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }
}
