package client.viewmodel.registration;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.viewmodel.ViewModelFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.User;

import static org.junit.jupiter.api.Assertions.*;

class RegisterViewModelTest {

    private ClientFactory clientFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private RegisterViewModel registerViewModel;

    @BeforeEach
    void setUp() {
        clientFactory=new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(modelFactory);
        registerViewModel = viewModelFactory.getRegisterVM();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isValidInputZero() {
        User user = new User(null, null);
        assertTrue(registerViewModel.isValidInput(user));
    }

    @Test
    void isValidInputOne() {
        User user = new User("testUsername", "1234");
        assertTrue(registerViewModel.isValidInput(user));
    }

    @Test
    void isValidInputMany() {
        User user1 = new User("johnDoe", "normalPassword");
        assertTrue(registerViewModel.isValidInput(user1));
        User user2 = new User("randomName", "randomPassword");
        assertTrue(registerViewModel.isValidInput(user2));
    }

    @Test
    void isValidInputBoundary() {
        // Password has to have more than 3 characters and less or equal to 15 characters.
        // Lower left boundary: 3
        User user1 = new User("johnDoe", "12");
        assertThrows(IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user1);
        });

        // Lower right boundary: 4 [Already done in isValidInputOne()]

        // Upper right boundary: 15
        User user2 = new User("johnDoe", "fifteenPassword");
        assertTrue(registerViewModel.isValidInput(user2));

        // Upper left boundary: 16
        User user3 = new User("randomName", "0123456789101112");
        assertThrows(IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user3);
        });
    }

    @Test
    void isValidInputException() {
        // Too short values
        User user1 = new User("username1", "");
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user1);
        });

        User user2 = new User("username2", "t");
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user2);
        });

        User user3 = new User("username3", "ta");
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user3);
        });

        // Too long values
        User user4 = new User("username1", "0123456789101112");
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user4);
        });

        User user5 = new User("username2", "012345678910111213");
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user5);
        });

        User user6 = new User("username3", "01234567891011121314");
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            registerViewModel.isValidInput(user6);
        });
    }





}