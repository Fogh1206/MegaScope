package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.EditMovieViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import shared.Show;

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
    private Show show;

    public void init(EditMovieViewModel editMovieViewModel, ViewHandler viewHandler, Show show) {
        this.editMovieViewModel = editMovieViewModel;
        this.viewHandler = viewHandler;
        this.show = show;

        System.out.println("Id from edit movie controller" + show.getMovie_id());
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
        movieNameTextField.setText(show.getName());
        dateOfReleaseTextField.setText(show.getDateOfRelease());
        mainActorsTextArea.setText(show.getMainActors());
        descriptionTextArea.setText(show.getDescription());
        mainActorsTextArea.setWrapText(true);
        descriptionTextArea.setWrapText(true);

        String[] times = show.getTimeOfShow().split(":");

        hourTextField.setText(times[0]);
        minuteTextField.setText(times[1]);
        System.out.println(show.getDateOfShow());
        dateOfShowDatePicker.setValue(LocalDate.parse(show.getDateOfShow()));

    }

    public void onSave(ActionEvent actionEvent) {

        editMovieViewModel.editMovie(show.getMovie_id(), show.getShow_id());
    }

    public void onCancel() {
        viewHandler.closeStage();
    }

}
