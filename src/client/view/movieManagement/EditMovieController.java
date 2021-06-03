package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.EditMovieViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import shared.MovieShow.MovieShow;

import java.time.LocalDate;

public class EditMovieController {

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
    Label editMovieLabel;

    private EditMovieViewModel editMovieViewModel;
    private ViewHandler viewHandler;
    private MovieShow movieShow;

    /**
     * @param editMovieViewModel
     * @param viewHandler
     * @param movieShow               Method for initializing the fields and for binding the values.
     */
    public void init(EditMovieViewModel editMovieViewModel, ViewHandler viewHandler, MovieShow movieShow) {
        this.editMovieViewModel = editMovieViewModel;
        this.viewHandler = viewHandler;
        this.movieShow = movieShow;

        movieNameTextField.textProperty().bindBidirectional(editMovieViewModel.movieNameProperty());
        dateOfReleaseTextField.textProperty().bindBidirectional(editMovieViewModel.dateOfReleaseProperty());
        mainActorsTextArea.textProperty().bindBidirectional(editMovieViewModel.mainActorsProperty());
        descriptionTextArea.textProperty().bindBidirectional(editMovieViewModel.descriptionProperty());
        hourTextField.textProperty().bindBidirectional(editMovieViewModel.hourTimeOfShowProperty());
        minuteTextField.textProperty().bindBidirectional(editMovieViewModel.minuteTimeOfShowProperty());
        dateOfShowDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 1);
            }
        });

        dateOfShowDatePicker.valueProperty().bindBidirectional(editMovieViewModel.dateOfShowProperty());
        editMovieLabel.textProperty().bindBidirectional(editMovieViewModel.editMovieLabelProperty());
        movieNameTextField.setText(movieShow.getName());
        dateOfReleaseTextField.setText(movieShow.getDateOfRelease());
        mainActorsTextArea.setText(movieShow.getMainActors());
        descriptionTextArea.setText(movieShow.getDescription());
        mainActorsTextArea.setWrapText(true);
        descriptionTextArea.setWrapText(true);

        String[] times = movieShow.getTimeOfShow().split(":");

        hourTextField.setText(times[0]);
        minuteTextField.setText(times[1]);
        dateOfShowDatePicker.setValue(LocalDate.parse(movieShow.getDateOfShow()));

    }

    /**
     * Void method for the button "onSave" functionality
     */
    public void onSave() {
        editMovieViewModel.editMovie(movieShow.getMovie_id(), movieShow.getShow_id());
        Platform.runLater(this::onCancel);
    }

    /**
     * Void method for closing the stage
     */
    public void onCancel() {
        viewHandler.closeStage();
    }

}
