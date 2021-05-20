package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.AddMovieViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.Show;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;

public class AddMovieController {

    @FXML
    TextField movieNameTextField;
    @FXML
    TextField dateOfReleaseTextField;
    @FXML
    TextArea mainActorsTextArea;
    @FXML
    TextField descriptionTextField;
    @FXML
    TextField hourTextField;
    @FXML
    TextField minuteTextField;
    @FXML
    DatePicker dateOfShowDatePicker;
    @FXML
    Label addMovieLabel;

    private AddMovieViewModel addMovieViewModel;
    private ViewHandler viewHandler;
    private User userloggedin;

    public void init(AddMovieViewModel addMovieViewModel, ViewHandler viewHandler, User user) {
        this.addMovieViewModel = addMovieViewModel;
        this.viewHandler = viewHandler;
        userloggedin = user;

        movieNameTextField.textProperty().bindBidirectional(addMovieViewModel.movieNameProperty());
        dateOfReleaseTextField.textProperty().bindBidirectional(addMovieViewModel.dateOfReleaseProperty());
        mainActorsTextArea.textProperty().bindBidirectional(addMovieViewModel.mainActorsProperty());
        descriptionTextField.textProperty().bindBidirectional(addMovieViewModel.descriptionProperty());
        hourTextField.textProperty().bindBidirectional(addMovieViewModel.hourTimeOfShowProperty());
        minuteTextField.textProperty().bindBidirectional(addMovieViewModel.minuteTimeOfShowProperty());
        dateOfShowDatePicker.valueProperty().bindBidirectional(addMovieViewModel.dateOfShowProperty());
        addMovieLabel.textProperty().bindBidirectional(addMovieViewModel.addMovieLabelProperty());


    }



    public void onSave(ActionEvent actionEvent) {


        addMovieViewModel.addMovie();


    }

    public void onCancel(ActionEvent actionEvent) {

        System.out.println("lalalala");
        addMovieViewModel.defaultFields();
        viewHandler.closeStage();

    }


}
