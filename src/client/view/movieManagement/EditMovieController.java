package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.EditMovieViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.Show;

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
    private Show show;

    public void init(EditMovieViewModel editMovieViewModel, ViewHandler viewHandler, Show show) {
        this.editMovieViewModel  = editMovieViewModel;
        this.viewHandler        = viewHandler;
        this.show = show;

        System.out.println("Id from edit movie controller"+ show.getMovie_id());
        movieNameTextField.textProperty().bindBidirectional(editMovieViewModel.movieNameProperty());
        dateOfReleaseTextField.textProperty().bindBidirectional(editMovieViewModel.dateOfReleaseProperty());
        mainActorsTextArea.textProperty().bindBidirectional(editMovieViewModel.mainActorsProperty());
        descriptionTextField.textProperty().bindBidirectional(editMovieViewModel.descriptionProperty());
        hourTextField.textProperty().bindBidirectional(editMovieViewModel.hourTimeOfShowProperty());
        minuteTextField.textProperty().bindBidirectional(editMovieViewModel.minuteTimeOfShowProperty());
        dateOfShowDatePicker.valueProperty().bindBidirectional(editMovieViewModel.dateOfShowProperty());

        movieNameTextField.setText(show.getName());
        dateOfReleaseTextField.setText(show.getDateOfRelease());
        mainActorsTextArea.setText(show.getMainActors());
        descriptionTextField.setText(show.getDescription());

        String[] times = show.getTimeOfShow().split(":");

        hourTextField.setText(times[0]);
        minuteTextField.setText(times[1]);
        System.out.println(show.getDateOfShow());
        dateOfShowDatePicker.setValue(LocalDate.parse(show.getDateOfShow()));

    }

    public void onSave(ActionEvent actionEvent){
        // your code here
        editMovieViewModel.editMovie(show.getMovie_id(),show.getShow_id());
    }

    public void onCancel(){

    }

}
