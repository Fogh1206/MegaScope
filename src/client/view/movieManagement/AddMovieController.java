package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.AddMovieViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import shared.Show;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;

public class AddMovieController {

    @FXML
    TextField movieNameTextField;
    @FXML
    TextField dateOfReleaseTextField;
    @FXML
    TextArea mainActorsTextArea;
    @FXML
    TextArea descriptionTextArea;
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
        descriptionTextArea.textProperty().bindBidirectional(addMovieViewModel.descriptionProperty());
        hourTextField.textProperty().bindBidirectional(addMovieViewModel.hourTimeOfShowProperty());
        minuteTextField.textProperty().bindBidirectional(addMovieViewModel.minuteTimeOfShowProperty());
        dateOfShowDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 1);
            }
        });
        dateOfShowDatePicker.valueProperty().bindBidirectional(addMovieViewModel.dateOfShowProperty());
        addMovieLabel.textProperty().bindBidirectional(addMovieViewModel.addMovieLabelProperty());
        mainActorsTextArea.setWrapText(true);
        descriptionTextArea.setWrapText(true);
    }


    public void onSave() {
        addMovieViewModel.addMovie();
        Platform.runLater(this::onCancel);
    }

    public void onCancel() {
        addMovieViewModel.defaultFields();
        viewHandler.closeStage();
    }
}
