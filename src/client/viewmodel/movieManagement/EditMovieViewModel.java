package client.viewmodel.movieManagement;

import client.model.UserModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.Show;

import java.time.LocalDate;

public class EditMovieViewModel {

    private StringProperty movieName;
    private StringProperty dateOfRelease;
    private StringProperty mainActors;
    private StringProperty description;
    private StringProperty hourTimeOfShow;
    private StringProperty minuteTimeOfShow;
    private StringProperty editMovieLabel;
    private ObjectProperty<LocalDate> dateOfShow;

    private UserModel model;

    public EditMovieViewModel(UserModel model){
        this.model = model;
        movieName       = new SimpleStringProperty();
        dateOfRelease   = new SimpleStringProperty();
        mainActors      = new SimpleStringProperty();
        description     = new SimpleStringProperty();
        hourTimeOfShow  = new SimpleStringProperty();
        minuteTimeOfShow= new SimpleStringProperty();
        dateOfShow      = new SimpleObjectProperty<>();
        editMovieLabel=new SimpleStringProperty();

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



    public StringProperty editMovieLabelProperty() {
        return editMovieLabel;
    }

    public void editMovie(int id, int show_id){

    if (movieName.get().isEmpty() || dateOfRelease.get().isEmpty() || dateOfShow.get().equals(null) || mainActors.get().isEmpty() || description.get().isEmpty() || hourTimeOfShow.get().isEmpty() || minuteTimeOfShow.get().isEmpty())
    {
        editMovieLabel.setValue("Each field must be filled!!!");
    }
    else {

        Show show = new Show(id, movieName.get(), dateOfRelease.get(),
                mainActors.get(), description.get(),
                hourTimeOfShow.get() + ":" + minuteTimeOfShow.get(),
                dateOfShow.get().toString(), show_id);
        model.editMovie(show);
    }
    }

}
