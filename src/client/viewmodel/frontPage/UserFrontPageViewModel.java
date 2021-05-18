package client.viewmodel.frontPage;

import client.model.UserModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Show;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserFrontPageViewModel {

    private UserModel model;
    private PropertyChangeSupport support;
    private Show selectedShow;


    private StringProperty username, button;
    private StringProperty searchPhrase;
    private ObjectProperty datePicked;
    private Property<ObservableList<Show>> observableItems;

    public UserFrontPageViewModel(UserModel userModel) {
        this.model = userModel;
        support = new PropertyChangeSupport(this);
        username = new SimpleStringProperty();
        datePicked = new SimpleObjectProperty();
        button = new SimpleStringProperty();
        searchPhrase = new SimpleStringProperty();
        observableItems = new SimpleListProperty<>();

        userModel.addPropertyChangeListener("Movie Result", this::onGetMovies);
    }

    public void onGetMovies(PropertyChangeEvent event) {
        System.out.println("On get movies");
        ObservableList<Show> observableList = FXCollections.observableArrayList();
        observableList.addAll((ArrayList<Show>) event.getNewValue());
        observableItems.setValue(observableList);
        if (!(searchPhrase.getValue() == null || searchPhrase.getValue().equals(""))) {
            System.out.println("Not null searchbar");
            search();
        }
        if (!(datePicked.get() == null)) {
            System.out.println("Not null date");
            onDatePick();
        }
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }


    public void getMovies() {
        model.getMovies();
    }

    public void close() {
        model.deactivateClient();
    }

    public void search() {

        ObservableList<Show> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < observableItems.getValue().size(); i++) {
            if (observableItems.getValue().get(i).getName().contains(searchPhrase.getValue())) {
                observableList.add(observableItems.getValue().get(i));
            }
        }
        observableItems.setValue(observableList);

        searchPhrase.setValue(null);
    }

    public void onDatePick() {
        ObservableList<Show> observableList = FXCollections.observableArrayList();

        for (int i = 0; i < observableItems.getValue().size(); i++) {
            if (datePicked.get().toString().equals(observableItems.getValue().get(i).getDateOfShow())) {
                observableList.add(observableItems.getValue().get(i));
            }
        }
        observableItems.setValue(observableList);
        datePicked = new SimpleObjectProperty();
        datePicked.setValue(null);
    }

    public Property<LocalDate> getValue() {
        return datePicked;
    }

    public Show getSelectedMovie() {
        return selectedShow;
    }

    public void addMovie(Show show) {
        model.addMovie(show);
    }

    public void editMovie(Show show) {
        model.editMovie(show);
    }

    public void removeMovie() {
        if (selectedShow != null) {
            model.removeMovie(selectedShow);
        }
    }

    public void selectedMovie(Show show) {
        selectedShow = show;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty buttonProperty() {
        return button;
    }


    public Property<ObservableList<Show>> observableItemsProperty() {
        return observableItems;
    }

    public StringProperty searchPhraseProperty() {
        return searchPhrase;
    }
}
