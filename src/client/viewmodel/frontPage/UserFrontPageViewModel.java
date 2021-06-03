package client.viewmodel.frontPage;

import client.model.UserModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.MovieShow;
import shared.MovieShowsList;
import shared.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class UserFrontPageViewModel implements PropertyChangeSubject {

    /**
     * Instance field
     */
    private UserModel model;
    private PropertyChangeSupport support;
    private MovieShow selectedMovieShow;
    private StringProperty username, button;
    private StringProperty searchPhrase;
    private ObjectProperty<LocalDate> datePicked;
    private Property<ObservableList<MovieShow>> observableItems;

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
        ObservableList<MovieShow> observableList = FXCollections.observableArrayList();
        MovieShowsList movieShowsList = (MovieShowsList) event.getNewValue();
        for (int i = 0; i < movieShowsList.getSize(); i++) {
            observableList.add(movieShowsList.get(i));
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
        ObservableList<MovieShow> observableList = FXCollections.observableArrayList();
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
        ObservableList<MovieShow> observableList = FXCollections.observableArrayList();
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
     * @param movieShow
     */
    public void addMovie(MovieShow movieShow) {
        model.addMovie(movieShow);
    }

    /**
     * Void method for editing movies
     *
     * @param movieShow
     */
    public void editMovie(MovieShow movieShow) {
        model.editMovie(movieShow);
    }

    /**
     * Void method removes movie
     */
    public void removeMovie() {
        if (selectedMovieShow != null) {
            model.removeMovie(selectedMovieShow);
        }
    }

    /**
     * Void method for selectedMovie
     *
     * @param movieShow
     */
    public void selectedMovie(MovieShow movieShow) {
        selectedMovieShow = movieShow;
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
    public Property<ObservableList<MovieShow>> observableItemsProperty() {
        return observableItems;
    }

    /**
     * Void method for adding the listener
     *
     * @param name
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(support.getPropertyChangeListeners()[0]);
    }
}
