package test;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.viewmodel.login.LoginViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    private LoginViewModel loginViewModel;
    private ClientFactory clientFactory;
    private ModelFactory modelFactory;

    @BeforeEach
    public void setup(){

        clientFactory=new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        loginViewModel = new LoginViewModel(modelFactory.getUserModel());
    }

    @Test
    public void loginWithNullInfo(){
        User user = null;

        assertEquals(null, user);
    }

}
