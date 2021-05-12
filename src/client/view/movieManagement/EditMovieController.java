package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.AddMovieViewModel;
import client.viewmodel.movieManagement.EditMovieViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.Movie;

import java.time.LocalDate;

public class EditMovieController {

    @FXML TextField movieNameTextField;
    @FXML TextField dateOfReleaseTextField;
    @FXML TextArea mainActorsTextArea;
    @FXML TextField descriptionTextField;
    @FXML TextField hourTextField;
    @FXML TextField minuteTextField;
    @FXML DatePicker dateOfShowDatePicker;

    private EditMovieViewModel editMovieViewModel;
    private ViewHandler viewHandler;

    public void init(EditMovieViewModel editMovieViewModel, ViewHandler viewHandler, Movie movie) {
        this.editMovieViewModel  = editMovieViewModel;
        this.viewHandler        = viewHandler;

        movieNameTextField.textProperty().bindBidirectional(editMovieViewModel.movieNameProperty());
        dateOfReleaseTextField.textProperty().bindBidirectional(editMovieViewModel.dateOfReleaseProperty());
        mainActorsTextArea.textProperty().bindBidirectional(editMovieViewModel.mainActorsProperty());
        descriptionTextField.textProperty().bindBidirectional(editMovieViewModel.descriptionProperty());
        hourTextField.textProperty().bindBidirectional(editMovieViewModel.hourTimeOfShowProperty());
        minuteTextField.textProperty().bindBidirectional(editMovieViewModel.minuteTimeOfShowProperty());
        dateOfShowDatePicker.valueProperty().bindBidirectional(editMovieViewModel.dateOfShowProperty());

        movieNameTextField.setText(movie.getName());
        dateOfReleaseTextField.setText(movie.getDateOfRelease());
        mainActorsTextArea.setText(movie.getMainActors());
        descriptionTextField.setText(movie.getDescription());

        String[] times = movie.getTimeOfShow().split(":");

        hourTextField.setText(times[0]);
        minuteTextField.setText(times[1]);
        dateOfShowDatePicker.setValue(LocalDate.parse(movie.getDateOfShow()));

    }

    public void onSave(ActionEvent actionEvent){
        // your code here
        editMovieViewModel.editMovie();


    }

    public void onCancel(){

    }

}
