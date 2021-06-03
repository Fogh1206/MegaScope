package client.viewmodel.login;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.viewmodel.ViewModelFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginViewModelTest {

    private ClientFactory clientFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private LoginViewModel loginViewModel;

    @BeforeEach
    void setUp() {
        clientFactory = new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(modelFactory);
        loginViewModel = viewModelFactory.getLoginViewModel();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void clearMessages() {
        loginViewModel.loginResultProperty().set("testResult");
        loginViewModel.clearMessages();
        assertEquals("", loginViewModel.loginResultProperty().get());
    }

    @Test
    void defaultFields() {
        loginViewModel.usernameProperty().set("testUsername");
        loginViewModel.passwordProperty().set("testPassword");
        loginViewModel.loginResultProperty().set("testResult");
        loginViewModel.defaultFields();
        assertEquals("", loginViewModel.usernameProperty().get());
        assertEquals("", loginViewModel.passwordProperty().get());
        assertEquals("", loginViewModel.loginResultProperty().get());
    }
}