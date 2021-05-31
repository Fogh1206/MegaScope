package client.view.movieManagement;

import client.view.ViewHandler;
import client.viewmodel.movieManagement.AddMovieViewModel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import shared.MovieShow;

import java.time.LocalDate;

public class AddMovieController {

    @FXML
    private ComboBox existingMovie;
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
            selectedMovieShow = (MovieShow) existingMovie.getItems().get(index);
            System.out.println("Kappa");
            System.out.println(selectedMovieShow.getName());
        }
    }
}
