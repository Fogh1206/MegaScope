package client.viewmodel.bookTickets;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.viewmodel.ViewModelFactory;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTicketsViewModelTest {

    private ClientFactory clientFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private BookTicketsViewModel bookTicketsViewModel;

    @BeforeEach
    void setUp() {
        clientFactory = new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(modelFactory);
        bookTicketsViewModel = viewModelFactory.getBookTicketsPage();
    }

    @Test
    void resetColors() {

        for (int i = 1; i < 26; i++) {
            bookTicketsViewModel.getFillProperty(String.valueOf(i)).setValue(Color.BLUEVIOLET);
        }

        bookTicketsViewModel.resetColors();

        for (int i = 1; i < 26; i++) {
            assertEquals(Color.GREEN, bookTicketsViewModel.getFillProperty(String.valueOf(i)).getValue());
        }

    }

    @Test
    void disablePropertyZero() {
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(0)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(0));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(0)).getValue());
        });
    }

    @Test
    void disablePropertyOne() {
        bookTicketsViewModel.getFillProperty(String.valueOf(1)).setValue(Color.GREEN);
        bookTicketsViewModel.disableProperty(String.valueOf(1));
        assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(1)).getValue());
    }

    @Test
    void disablePropertyMany() {
        for (int i = 1; i < 5; i++) {
            bookTicketsViewModel.getFillProperty(String.valueOf(i)).setValue(Color.BLUEVIOLET);
        }

        for (int i = 1; i < 5; i++) {
            bookTicketsViewModel.disableProperty(String.valueOf(i));
        }

        for (int i = 1; i < 5; i++) {
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(i)).getValue());
        }

    }

    @Test
    void disablePropertyBoundary() {
        // Seat number has to have more than 0 characters and less or equal to 26 characters.
        // Lower left boundary: 0 [Already done in disablePropertyZero()]

        // Lower right boundary: 1 [Already done in disablePropertyOne()]

        // Upper right boundary: 25
        bookTicketsViewModel.getFillProperty(String.valueOf(25)).setValue(Color.GREEN);
        bookTicketsViewModel.disableProperty(String.valueOf(25));
        assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(25)).getValue());

        // Upper left boundary: 26
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(26)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(26));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(26)).getValue());
        });


    }

    @Test
    void disablePropertyException() {

        // Too low value
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(0)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(0));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(0)).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(-1)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(-1));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(-1)).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(-2)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(-2));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(-2)).getValue());
        });

        // Too high value
        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(26)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(26));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(26)).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(27)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(27));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(27)).getValue());
        });

        assertThrows(NullPointerException.class, () -> {
            bookTicketsViewModel.getFillProperty(String.valueOf(28)).setValue(Color.GREEN);
            bookTicketsViewModel.disableProperty(String.valueOf(28));
            assertEquals(Color.GRAY, bookTicketsViewModel.getFillProperty(String.valueOf(28)).getValue());
        });
    }
}