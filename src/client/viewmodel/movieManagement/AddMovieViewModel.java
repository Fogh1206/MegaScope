package client.viewmodel.movieManagement;

import client.model.UserModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.MovieShow.MovieShow;
import shared.MovieShow.MovieShowsList;
import shared.PropertyChangeSubject;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class AddMovieViewModel implements PropertyChangeSubject {

    private StringProperty movieName;
    private StringProperty dateOfRelease;
    private StringProperty mainActors;
    private StringProperty description;
    private StringProperty hourTimeOfShow;
    private StringProperty minuteTimeOfShow;
    private ObjectProperty<LocalDate> dateOfShow;
    private StringProperty addMovieLabel;
    private PropertyChangeSupport support;

    private UserModel model;
    private Property<ObservableList<MovieShow>> existingMovie;

    /**
     * @param model One-argument constructor for initializing the fields
     */
    public AddMovieViewModel(UserModel model) {
        support = new PropertyChangeSupport(this);
        this.model = model;
        movieName = new SimpleStringProperty();
        dateOfRelease = new SimpleStringProperty();
        mainActors = new SimpleStringProperty();
        description = new SimpleStringProperty();
        hourTimeOfShow = new SimpleStringProperty();
        minuteTimeOfShow = new SimpleStringProperty();
        dateOfShow = new SimpleObjectProperty<>();
        addMovieLabel = new SimpleStringProperty();
        existingMovie = new SimpleListProperty<>();

        model.addPropertyChangeListener(EventType.GETMOVIESFORADD_RESULT.toString(), this::onGetMoviesForAdd);
    }

    private void onGetMoviesForAdd(PropertyChangeEvent event) {
        ObservableList<MovieShow> observableList = FXCollections.observableArrayList();
        observableList.add(null);
        MovieShowsList movieShowsList = (MovieShowsList) event.getNewValue();
        for (int i = 0; i < movieShowsList.getSize(); i++) {
            observableList.add(movieShowsList.get(i));
        }
        existingMovie.setValue(observableList);
    }

    public StringProperty movieNameProperty() {
        return movieName;
    }

    public StringProperty dateOfReleaseProperty() {
        return dateOfRelease;
    }

    public StringProperty mainActorsProperty() {
        return mainActors;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty hourTimeOfShowProperty() {
        return hourTimeOfShow;
    }

    public StringProperty minuteTimeOfShowProperty() {
        return minuteTimeOfShow;
    }


    public StringProperty addMovieLabelProperty() {
        return addMovieLabel;
    }

    public ObjectProperty dateOfShowProperty() {
        return dateOfShow;
    }


    /**
     * Void method for adding the movie
     */
    public void addMovie(MovieShow selectedMovieShow) {
        if (selectedMovieShow == null) {
            if (movieName.get() == null || movieName.get().equals("")) {
                addMovieLabel.setValue("Please input the movie name");
            } else if (dateOfRelease.get() == null || dateOfRelease.get().equals("")) {
                addMovieLabel.setValue("Please input the date of release");
            } else if (mainActors.get() == null || mainActors.get().equals("")) {
                addMovieLabel.setValue("Please input the main actors");
            } else if (description.get() == null || description.get().equals("")) {
                addMovieLabel.setValue("Please input the description");
            } else if (hourTimeOfShow.get() == null || hourTimeOfShow.get().equals("")) {
                addMovieLabel.setValue("Please input the hour of the show");
            } else if (minuteTimeOfShow.get() == null || minuteTimeOfShow.get().equals("")) {
                addMovieLabel.setValue("Please input the time of show");
            } else if (dateOfShow.get() == null || dateOfShow.get().equals("")) {
                addMovieLabel.setValue("Please input the date of show");
            } else {
                MovieShow movieShow = new MovieShow(movieName.get(), dateOfRelease.get(),
                        mainActors.get(), description.get(),
                        hourTimeOfShow.get() + ":" + minuteTimeOfShow.get(),
                        dateOfShow.get().toString());
                model.addMovie(movieShow);
                addMovieLabel.setValue("Successful");
                defaultFields();
                getMovies();
            }
        } else if (selectedMovieShow != null) {
            if (hourTimeOfShow.get() == null || hourTimeOfShow.get().equals("")) {
                addMovieLabel.setValue("Please input the hour of the show");
            } else if (minuteTimeOfShow.get() == null || minuteTimeOfShow.get().equals("")) {
                addMovieLabel.setValue("Please input the time of show");
            } else if (dateOfShow.get() == null || dateOfShow.get().equals("")) {
                addMovieLabel.setValue("Please input the date of show");
            } else {
                MovieShow movieShow = new MovieShow(selectedMovieShow.getName(), selectedMovieShow.getDateOfRelease(),
                        selectedMovieShow.getMainActors(), selectedMovieShow.getDescription(),
                        hourTimeOfShow.get() + ":" + minuteTimeOfShow.get(),
                        dateOfShow.get().toString());
                model.addMovie(movieShow);
                addMovieLabel.setValue("Successful");
            }

        }

    }

    /**
     * Void method for clearing the text-fields
     */
    public void defaultFields() {
        movieName.setValue("");
        mainActors.setValue("");
        description.setValue("");
        dateOfShow.setValue(null);
        dateOfRelease.setValue("");
        hourTimeOfShow.setValue("");
        minuteTimeOfShow.setValue("");
        addMovieLabel.setValue("");

    }

    /**
     * @param name
     * @param listener Void method for adding the listener
     */
    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(support.getPropertyChangeListeners()[0]);
    }

    public Property<ObservableList<MovieShow>> existingMovieProperty() {
        return existingMovie;
    }

    public void getMovies() {
        model.getMoviesForAdd();
    }
}
