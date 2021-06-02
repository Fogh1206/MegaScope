package client.viewmodel.bookTickets;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.viewmodel.ViewModelFactory;
import client.viewmodel.movieManagement.AddMovieViewModel;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTicketsViewModelTest {

    private ClientFactory clientFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private BookTicketsViewModel bookTicketsViewModel;

    @BeforeEach
    void setUp() {
        clientFactory=new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(modelFactory);
        bookTicketsViewModel = viewModelFactory.getBookTicketsPage();
    }

    @Test
    void resetColors() {

        for(int i = 1 ; i < 26 ; i++){
            bookTicketsViewModel.getFillProperty(""+i).setValue(Color.BLUEVIOLET);
        }

        bookTicketsViewModel.resetColors();

        for(int i = 1 ; i < 26 ; i++){
            assertEquals(Color.GREEN,bookTicketsViewModel.getFillProperty(""+i).getValue());
        }

    }

    @Test
    void disablePropertyZero() {
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + 0).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + 0);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 0).getValue());
        });
    }

    @Test
    void disablePropertyOne() {
        bookTicketsViewModel.getFillProperty("" + 1).setValue(Color.GREEN);
        bookTicketsViewModel.disableProperty("" + 1);
        assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 1).getValue());
    }

    @Test
    void disablePropertyMany() {
        for(int i = 1 ; i < 5 ; i++){
            bookTicketsViewModel.getFillProperty(""+i).setValue(Color.BLUEVIOLET);
        }

        for(int i = 1 ; i < 5 ; i++){
            bookTicketsViewModel.disableProperty(""+i);
        }

        for (int i = 1 ; i < 5 ; i ++){
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + i).getValue());
        }

    }

    @Test
    void disablePropertyBoundary(){
        // Seat number has to have more than 0 characters and less or equal to 26 characters.
        // Lower left boundary: 0 [Already done in disablePropertyZero()]

        // Lower right boundary: 1 [Already done in disablePropertyOne()]

        // Upper right boundary: 25
        bookTicketsViewModel.getFillProperty("" + 25).setValue(Color.GREEN);
        bookTicketsViewModel.disableProperty("" + 25);
        assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 25).getValue());

        // Upper left boundary: 26
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + 26).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + 26);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 26).getValue());
        });



    }

    @Test
    void disablePropertyException(){

        // Too low value
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + 0).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + 0);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 0).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + -1).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + -1);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + -1).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + -2).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + -2);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + -2).getValue());
        });

        // Too high value
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + 26).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + 26);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 26).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + 27).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + 27);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 27).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty("" + 28).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty("" + 28);
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty("" + 28).getValue());
        });

    }


}