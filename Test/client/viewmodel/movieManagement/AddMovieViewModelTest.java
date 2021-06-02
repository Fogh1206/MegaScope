package client.viewmodel.movieManagement;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.viewmodel.ViewModelFactory;
import client.viewmodel.login.LoginViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AddMovieViewModelTest {

    private ClientFactory clientFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private AddMovieViewModel addMovieViewModel;

    @BeforeEach
    void setUp() {
        clientFactory=new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(modelFactory);
        addMovieViewModel = viewModelFactory.getAddMovieViewModel();
    }

    @Test
    void defaultFields() {
        addMovieViewModel.movieNameProperty().set("testMovieName");
        addMovieViewModel.mainActorsProperty().set("testMainActors");
        addMovieViewModel.descriptionProperty().set("testDescription");
        addMovieViewModel.dateOfShowProperty().set("10/10/10");
        addMovieViewModel.dateOfReleaseProperty().set("testDateOfRelease");
        addMovieViewModel.hourTimeOfShowProperty().set("testHourTimeOfShow");
        addMovieViewModel.minuteTimeOfShowProperty().set("testMinuteTimeOfShow");
        addMovieViewModel.addMovieLabelProperty().set("testAddMovie");

        addMovieViewModel.defaultFields();

        assertEquals("", addMovieViewModel.movieNameProperty().get());
        assertEquals("", addMovieViewModel.mainActorsProperty().get());
        assertEquals("", addMovieViewModel.descriptionProperty().get());
        assertNull(addMovieViewModel.dateOfShowProperty().get());
        assertEquals("", addMovieViewModel.dateOfReleaseProperty().get());
        assertEquals("", addMovieViewModel.hourTimeOfShowProperty().get());
        assertEquals("", addMovieViewModel.minuteTimeOfShowProperty().get());
        assertEquals("", addMovieViewModel.addMovieLabelProperty().get());

    }
}