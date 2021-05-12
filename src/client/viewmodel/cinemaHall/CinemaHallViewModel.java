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
import java.util.HashMap;

public class CinemaHallViewModel {
    private UserModel model;
    private PropertyChangeSupport support;
    private ArrayList<ObjectProperty<Paint>> colors;

    private HashMap<String,ObjectProperty<Paint>> colorIdMap;

    public CinemaHallViewModel(UserModel model) {
        this.model = model;
        colors = new ArrayList<>();
        colorIdMap = new HashMap<>();
        support = new PropertyChangeSupport(this);

        for(int i = 0 ; i < 25 ; i++){
            colors.add(i,new SimpleObjectProperty<>(Color.YELLOW));
            colorIdMap.put(""+i,colors.get(i));
            System.out.println(colorIdMap.size());
        }

        model.addPropertyChangeListener(EventType.GETRESERVATIONS_RESULT.toString(), this::onGetReservations);
    }

    private void onGetReservations(PropertyChangeEvent event) {
        ArrayList<String> list = (ArrayList<String>) event.getNewValue();
        System.out.println(list.toString() + " Hello Guys");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(Integer.valueOf(list.get(i)));
            System.out.println(colorIdMap.get(Integer.valueOf(list.get(i))));
            if(colorIdMap.get(Integer.valueOf(list.get(i))) != null){
                colors.get(i).setValue(Color.RED);
                System.out.println(colors.get(i));
                System.out.println(colorIdMap.get(list.get(i)));
            }
        }
    }

    public void getReservation(Movie movie) {
        model.getReservation(movie);
    }

    public Property<Paint> getFillProperty(String id) {
        try {
            return colors.get(Integer.valueOf(id));
        } catch (ArrayIndexOutOfBoundsException e){
            // Not in list
            return null;
        }
    }

    public Property<Boolean> getDisableProperty(String id) {
        return null;
    }
}
