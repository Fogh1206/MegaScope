package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.login.LoginViewModel;
import client.viewmodel.movieManagement.AddMovieViewModel;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shared.Movie;
import shared.NewRegisteredUser;

public class AddMovieController {

    @FXML TextField movieNameTextField;
    @FXML TextField dateOfReleaseTextField;
    @FXML TextArea  mainActorsTextArea;
    @FXML TextField descriptionTextField;
    @FXML TextField hourTextField;
    @FXML TextField minuteTextField;
    @FXML DatePicker dateOfShowDatePicker;

    private AddMovieViewModel   addMovieViewModel;
    private ViewHandler         viewHandler;

    public void init(AddMovieViewModel addMovieViewModel, ViewHandler viewHandler) {
        this.addMovieViewModel  = addMovieViewModel;
        this.viewHandler        = viewHandler;

        movieNameTextField.textProperty().bindBidirectional(addMovieViewModel.movieNameProperty());
        dateOfReleaseTextField.textProperty().bindBidirectional(addMovieViewModel.dateOfReleaseProperty());
        mainActorsTextArea.textProperty().bindBidirectional(addMovieViewModel.mainActorsProperty());
        descriptionTextField.textProperty().bindBidirectional(addMovieViewModel.descriptionProperty());
        hourTextField.textProperty().bindBidirectional(addMovieViewModel.hourTimeOfShowProperty());
        minuteTextField.textProperty().bindBidirectional(addMovieViewModel.minuteTimeOfShowProperty());
        dateOfShowDatePicker.valueProperty().bindBidirectional(addMovieViewModel.dateOfShowProperty());
    }


    public void onSave(ActionEvent actionEvent){
            // your code here
            Movie movie = new Movie(movieNameTextField.getText(), dateOfReleaseTextField.getText(),
                    mainActorsTextArea.getText(), descriptionTextField.getText(),
                    hourTextField.getText() + ":" + minuteTextField.getText(),
                    dateOfShowDatePicker.getValue().toString());

            addMovieViewModel.addMovie();


    }

    public void onCancel(ActionEvent actionEvent){

    }


}
