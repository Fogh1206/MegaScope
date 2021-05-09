package client.viewmodel.movieManagement;

import client.model.UserModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.Movie;

import java.time.LocalDate;

public class AddMovieViewModel {

    private StringProperty movieName;
    private StringProperty dateOfRelease;
    private StringProperty mainActors;
    private StringProperty description;
    private StringProperty hourTimeOfShow;
    private StringProperty minuteTimeOfShow;
    private ObjectProperty<LocalDate> dateOfShow;

    private UserModel model;

    public AddMovieViewModel(UserModel model){

        this.model = model;
        movieName       = new SimpleStringProperty();
        dateOfRelease   = new SimpleStringProperty();
        mainActors      = new SimpleStringProperty();
        description     = new SimpleStringProperty();
        hourTimeOfShow  = new SimpleStringProperty();
        minuteTimeOfShow= new SimpleStringProperty();
        dateOfShow      = new SimpleObjectProperty<>();

    }

    public StringProperty movieNameProperty(){
        return movieName;
    }

    public StringProperty dateOfReleaseProperty(){
        return dateOfRelease;
    }

    public StringProperty mainActorsProperty(){
        return mainActors;
    }

    public StringProperty descriptionProperty(){
        return description;
    }

    public StringProperty hourTimeOfShowProperty(){
        return hourTimeOfShow;
    }

    public StringProperty minuteTimeOfShowProperty(){
        return minuteTimeOfShow;
    }

    public ObjectProperty dateOfShowProperty(){
        return dateOfShow;
    }

    public void addMovie(){

        Movie movie = new Movie(movieName.get(), dateOfRelease.get(),
                mainActors.get(), description.get(),
                hourTimeOfShow.get() + ":" + minuteTimeOfShow.get(),
                dateOfShow.get().toString());

        System.out.println(movie);

        model.addMovie(movie);
    }

}
