package client.viewmodel.frontPage;

import client.model.UserModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Show;
import shared.ShowsList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class UserFrontPageViewModel {

    /**
     * Instance field
     */
    private UserModel model;
    private PropertyChangeSupport support;
    private Show selectedShow;
    private StringProperty username, button;
    private StringProperty searchPhrase;
    private ObjectProperty<LocalDate> datePicked;
    private Property<ObservableList<Show>> observableItems;

    /**
     * Constructor
     *
     * @param userModel
     */
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

    /**
     * Void method for viewing shows on frontpage, which updates after a search
     * or new date pick
     *
     * @param event
     */
    public void onGetMovies(PropertyChangeEvent event) {
        System.out.println("On get movies");
        ObservableList<Show> observableList = FXCollections.observableArrayList();
        ShowsList showsList = (ShowsList) event.getNewValue();
        for (int i = 0; i < showsList.getSize(); i++) {
            observableList.add(showsList.get(i));
        }
        observableItems.setValue(observableList);
        if (!(searchPhrase.getValue() == null || searchPhrase.getValue().equals(""))) {
            search();
        }
        if (!(datePicked.get() == null)) {
            onDatePick();
        }
    }

    /**
     * Void method for adding the listener
     *
     * @param name
     * @param listener
     */
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    /**
     * Method gets movies
     */
    public void getMovies() {
        model.getMovies();
    }

    /**
     * Method deactivates when the program is closed
     */
    public void close() {
        model.deactivateClient();
    }

    /**
     * Void method for searching for shows
     */
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

    /**
     * Void method for viewing shows on specific dates
     */
    public void onDatePick() {
        ObservableList<Show> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < observableItems.getValue().size(); i++) {
            if (datePicked.get().toString().equals(observableItems.getValue().get(i).getDateOfShow())) {
                observableList.add(observableItems.getValue().get(i));
            }
        }
        observableItems.setValue(observableList);
        datePicked.set(null);
    }

    /**
     * Get value
     *
     * @return datepicked
     */
    public ObjectProperty<LocalDate> getValue() {
        return datePicked;
    }

    /**
     * Void method adds movies to show
     *
     * @param show
     */
    public void addMovie(Show show) {
        model.addMovie(show);
    }

    /**
     * Void method for editing movies
     *
     * @param show
     */
    public void editMovie(Show show) {
        model.editMovie(show);
    }

    /**
     * Void method removes movie
     */
    public void removeMovie() {
        if (selectedShow != null) {
            model.removeMovie(selectedShow);
        }
    }

    /**
     * Void method for selectedMovie
     *
     * @param show
     */
    public void selectedMovie(Show show) {
        selectedShow = show;
    }


    /**
     * String property for a search phrase
     *
     * @return seachPhrase
     */
    public StringProperty searchPhraseProperty() {
        return searchPhrase;
    }

    /**
     * Method for returning observableItems for the ObservableList
     *
     * @return observableItems
     */
    public Property<ObservableList<Show>> observableItemsProperty() {
        return observableItems;
    }

}
