package test;

import client.model.Model;
import client.networking.ClientImpl;
import client.viewmodel.frontPage.UserFrontPageViewModel;
import client.viewmodel.login.LoginViewModel;
import client.viewmodel.registration.RegisterViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTest {

    private RegisterViewModel registerViewModel;
    private ClientImpl client;

    @BeforeEach
    public void setup(){
        Model model = new Model(client);
        registerViewModel = new RegisterViewModel(model);
        }

    @Test
    public void testValidRegister(){
        StringProperty firstName = new SimpleStringProperty();
        StringProperty lastName = new SimpleStringProperty();
        StringProperty username = new SimpleStringProperty();
        StringProperty password = new SimpleStringProperty();
        StringProperty confirmPassword = new SimpleStringProperty();
        StringProperty phoneNumber = new SimpleStringProperty();

        firstName.bind(registerViewModel.firstNameProperty());
        lastName.bind(registerViewModel.lastNameProperty());
        username.bind(registerViewModel.usernameProperty());
        password.bind(registerViewModel.passwordProperty());
        confirmPassword.bind(registerViewModel.confirmPasswordProperty());
        phoneNumber.bind(registerViewModel.phoneNumberProperty());

        firstName.set("Test");
        lastName.set("Test");
        username.set("Test");
        password.set("Test");
        confirmPassword.set("Test");
        phoneNumber.set("12345678");

        registerViewModel.register();




    }


}
