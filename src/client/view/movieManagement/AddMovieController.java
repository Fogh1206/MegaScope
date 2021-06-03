package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.AddMovieViewModel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import shared.MovieShow.MovieShow;

import java.time.LocalDate;

public class AddMovieController {

    @FXML
    private ComboBox<MovieShow> existingMovie;
    @FXML
    private TextField movieNameTextField;
    @FXML
    private TextField dateOfReleaseTextField;
    @FXML
    private TextArea mainActorsTextArea;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField hourTextField;
    @FXML
    private TextField minuteTextField;
    @FXML
    private DatePicker dateOfShowDatePicker;
    @FXML
    private Label addMovieLabel;

    private AddMovieViewModel addMovieViewModel;
    private ViewHandler viewHandler;
    private MovieShow selectedMovieShow;


    /**
     * @param addMovieViewModel
     * @param viewHandler       Method for initializing the fields and for binding the values.
     */
    public void init(AddMovieViewModel addMovieViewModel, ViewHandler viewHandler) {
        this.addMovieViewModel = addMovieViewModel;
        this.viewHandler = viewHandler;
        addMovieViewModel.getMovies();
        selectedMovieShow = null;
        existingMovie.itemsProperty().bindBidirectional(addMovieViewModel.existingMovieProperty());

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


    /**
     * Void method for the button "onSave" functionality
     */
    public void onSave() {
        addMovieViewModel.addMovie(selectedMovieShow);
    }

    /**
     * Void method for closing the stage
     */
    public void onCancel() {
        addMovieViewModel.defaultFields();
        viewHandler.closeStage();
    }

    public void setSelected() {
        if (existingMovie.getSelectionModel().getSelectedItem() != null) {
            int index = existingMovie.getSelectionModel().getSelectedIndex();
            selectedMovieShow = existingMovie.getItems().get(index);
        }
    }
}
