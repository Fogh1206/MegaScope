package client.viewmodel.movieManagement;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.model.Model;
import client.viewmodel.ViewModelFactory;
import client.viewmodel.registration.RegisterViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.Show;

import static org.junit.jupiter.api.Assertions.*;

class EditMovieViewModelTest {

    private ClientFactory clientFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private EditMovieViewModel editMovieViewModel;

    @BeforeEach
    void setUp() {
        clientFactory=new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(modelFactory);
        editMovieViewModel = viewModelFactory.getEditMovieViewModel();

        editMovieViewModel.movieNameProperty().set("NewName");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void editMovie() {

        editMovieViewModel.editMovie(1000, 1000);

    }
}