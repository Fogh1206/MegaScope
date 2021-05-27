package client.viewmodel.movieManagement;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.Show;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class AddMovieViewModel {

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

    /**
     * @param model
     * One-argument constructor for initializing the fields
     */
    public AddMovieViewModel(UserModel model){
        support = new PropertyChangeSupport(this);
        this.model = model;
        movieName       = new SimpleStringProperty();
        dateOfRelease   = new SimpleStringProperty();
        mainActors      = new SimpleStringProperty();
        description     = new SimpleStringProperty();
        hourTimeOfShow  = new SimpleStringProperty();
        minuteTimeOfShow= new SimpleStringProperty();
        dateOfShow      = new SimpleObjectProperty<>();
        addMovieLabel=new SimpleStringProperty();
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



    public StringProperty addMovieLabelProperty() {
        return addMovieLabel;
    }

    public ObjectProperty dateOfShowProperty(){
        return dateOfShow;
    }


    /**
     * Void method for adding the movie
     */
    public void addMovie(){

        if (movieName.get()==null || movieName.get().equals(""))
        {
            addMovieLabel.setValue("Please input the movie name");
        }
        else if (dateOfRelease.get()==null || dateOfRelease.get().equals(""))
        {
            addMovieLabel.setValue("Please input the date of release");
        }
         else if (mainActors.get()==null || mainActors.get().equals(""))
        {
            addMovieLabel.setValue("Please input the main actors");
        }
         else if (description.get()==null || description.get().equals(""))
        {
            addMovieLabel.setValue("Please input the description");
        }
         else if (hourTimeOfShow.get()==null || hourTimeOfShow.get().equals(""))
        {
            addMovieLabel.setValue("Please input the hour of the show");
        }
         else if (minuteTimeOfShow.get()==null || minuteTimeOfShow.get().equals(""))
        {
            addMovieLabel.setValue("Please input the time of show");
        }
        else if (dateOfShow.get()==null || dateOfShow.get().equals(""))
        {
            addMovieLabel.setValue("Please input the date of show");
        }
        else
        {
            Show show = new Show(movieName.get(), dateOfRelease.get(),
                    mainActors.get(), description.get(),
                    hourTimeOfShow.get() + ":" + minuteTimeOfShow.get(),
                    dateOfShow.get().toString());

            System.out.println(show);

            model.addMovie(show);
            addMovieLabel.setValue("Successful");
            defaultFields();


        }


    }

    /**
     * Void method for clearing the text-fields
     */
    public void defaultFields()
    {
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
     * @param listener
     * Void method for adding the listener
     */
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

}
