package client.viewmodel.registration;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.viewmodel.ViewModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.User.User;

import static org.junit.jupiter.api.Assertions.*;

class RegisterViewModelTest {

    private ClientFactory clientFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private RegisterViewModel registerViewModel;

    @BeforeEach
    void setUp() {
        // Server need to be turned ON
        clientFactory = new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(modelFactory);
        registerViewModel = viewModelFactory.getRegisterVM();
    }


    @Test
    void isValidInputZero() {
        User user = new User(null, null);
        assertFalse(registerViewModel.isValidInput(user));
    }

    @Test
    void isValidInputOne() {
        User user = new User("testUsername", "1234");
        assertFalse(registerViewModel.isValidInput(user));
    }

    @Test
    void isValidInputMany() {
        User user1 = new User("johnDoe", "normalPassword");
        assertFalse(registerViewModel.isValidInput(user1));
        User user2 = new User("randomName", "randomPassword");
        assertFalse(registerViewModel.isValidInput(user2));
    }

    @Test
    void isValidInputBoundary() {
        // Password has to have more than 3 characters and less or equal to 15 characters.
        // Lower left boundary: 3
        User user1 = new User("Test", "Test", "johnDoe", "12", "123456789", "Norm", false);
        assertThrows(IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user1);
        });

        // Lower right boundary: 4 [Already done in isValidInputOne()]

        // Upper right boundary: 15
        User user2 = new User("Test", "Test", "johnDoe", "fifteenPassword", "123456789", "Norm", false);
        assertTrue(registerViewModel.isValidInput(user2));

        // Upper left boundary: 16
        User user3 = new User("Test", "Test", "randomName", "0123456789101112", "123456789", "Norm", false);
        assertThrows(IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user3);
        });
    }

}