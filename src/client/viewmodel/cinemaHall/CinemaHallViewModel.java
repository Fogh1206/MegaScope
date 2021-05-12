package client.viewmodel.cinemaHall;

import client.model.UserModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.Movie;
import shared.util.EventType;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CinemaHallViewModel {
    private UserModel model;
    private PropertyChangeSupport support;
    private ObjectProperty<Paint> color1;
    private ObjectProperty<Paint> color2;
    private ObjectProperty<Paint> color3;
    private ObjectProperty<Paint> color4;
    private ObjectProperty<Paint> color5;
    private ObjectProperty<Paint> color6;
    private ObjectProperty<Paint> color7;
    private ObjectProperty<Paint> color8;
    private ObjectProperty<Paint> color9;
    private ObjectProperty<Paint> color10;
    private ObjectProperty<Paint> color11;
    private ObjectProperty<Paint> color12;
    private ObjectProperty<Paint> color13;
    private ObjectProperty<Paint> color14;
    private ObjectProperty<Paint> color15;
    private ObjectProperty<Paint> color16;
    private ObjectProperty<Paint> color17;
    private ObjectProperty<Paint> color18;
    private ObjectProperty<Paint> color19;
    private ObjectProperty<Paint> color20;
    private ObjectProperty<Paint> color21;
    private ObjectProperty<Paint> color22;
    private ObjectProperty<Paint> color23;
    private ObjectProperty<Paint> color24;
    private ObjectProperty<Paint> color25;


    public CinemaHallViewModel(UserModel model) {
        this.model = model;
        support = new PropertyChangeSupport(this);
        color1 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color2 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color3 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color4 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color5 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color6 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color7 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color8 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color9 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color10 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color11 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color12 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color13 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color14 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color15 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color16 = new SimpleObjectProperty<Paint>(Color.GREEN);
        color17 = new SimpleObjectProperty<Paint>();
        color18 = new SimpleObjectProperty<Paint>();
        color19 = new SimpleObjectProperty<Paint>();
        color20 = new SimpleObjectProperty<Paint>();
        color21 = new SimpleObjectProperty<Paint>();
        color22 = new SimpleObjectProperty<Paint>();
        color23 = new SimpleObjectProperty<Paint>();
        color24 = new SimpleObjectProperty<Paint>();
        color25 = new SimpleObjectProperty<Paint>();

        model.addPropertyChangeListener(EventType.GETRESERVATIONS_RESULT.toString(), this::onGetReservations);
    }

    private void onGetReservations(PropertyChangeEvent event) {
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();
        System.out.println(list.toString());
        for (int i = 0; i < list.size(); i++) {
            color1.setValue(Color.RED);
        }
    }

    public void getReservation(Movie movie) {
        model.getReservation(movie);
    }

    public Property<Paint> getFillProperty(String id) {
        switch (id) {
            case "1":
                return color1;
            case "2":
                return color2;
            case "3":
                return color3;
            case "4":
                return color4;
            case "5":
                return color5;
            case "6":
                return color6;
            default:
                return color8;
        }
    }

    public Property<Boolean> getDisableProperty(String id) {
        return null;
    }
}
