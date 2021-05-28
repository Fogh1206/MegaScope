package test;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.model.Model;
import client.networking.ClientImpl;
import client.viewmodel.frontPage.UserFrontPageViewModel;
import client.viewmodel.login.LoginViewModel;
import client.viewmodel.registration.RegisterViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.User;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    private RegisterViewModel registerViewModel;
    private ClientFactory clientFactory;
    private ModelFactory modelFactory;

    @BeforeEach
    public void setup(){

        clientFactory=new ClientFactory();
        modelFactory = new ModelFactory(clientFactory);
        registerViewModel = new RegisterViewModel(modelFactory.getUserModel());

    }

    @Test
    public void testValidRegister(){
        StringProperty firstName = new SimpleStringProperty();
        StringProperty lastName = new SimpleStringProperty();
        StringProperty username = new SimpleStringProperty();
        StringProperty password = new SimpleStringProperty();
        StringProperty confirmPassword = new SimpleStringProperty();
        StringProperty phoneNumber = new SimpleStringProperty();
        StringProperty registrationMessageLabel = new SimpleStringProperty();

        firstName.bindBidirectional(registerViewModel.firstNameProperty());
        lastName.bindBidirectional(registerViewModel.lastNameProperty());
        username.bindBidirectional(registerViewModel.usernameProperty());
        password.bindBidirectional(registerViewModel.passwordProperty());
        confirmPassword.bindBidirectional(registerViewModel.confirmPasswordProperty());
        phoneNumber.bindBidirectional(registerViewModel.phoneNumberProperty());
        registrationMessageLabel.bindBidirectional(registerViewModel.registrationMessageLabelProperty());

        firstName.set("Test");
        lastName.set("Test");
        username.set("Test");
        password.set("Test");
        confirmPassword.set("Test");
        phoneNumber.set("12345678");

        User user = new User(firstName.get(), lastName.get(), username.get(),
                password.get(), phoneNumber.get(), "USER", false);

        assertTrue(registerViewModel.isValidInput(user));

    }

    @Test
    public void testTooSmallPassword(){
        StringProperty firstName = new SimpleStringProperty();
        StringProperty lastName = new SimpleStringProperty();
        StringProperty username = new SimpleStringProperty();
        StringProperty password = new SimpleStringProperty();
        StringProperty phoneNumber = new SimpleStringProperty();
        StringProperty registrationMessageLabel = new SimpleStringProperty();

        firstName.bindBidirectional(registerViewModel.firstNameProperty());
        lastName.bindBidirectional(registerViewModel.lastNameProperty());
        username.bindBidirectional(registerViewModel.usernameProperty());
        password.bindBidirectional(registerViewModel.passwordProperty());
        phoneNumber.bindBidirectional(registerViewModel.phoneNumberProperty());
        registrationMessageLabel.bindBidirectional(registerViewModel.registrationMessageLabelProperty());

        firstName.set("Test");
        lastName.set("Test");
        username.set("Test");
        password.set("Te");
        phoneNumber.set("12345678");

        User user = new User(firstName.get(), lastName.get(), username.get(),
                password.get(), phoneNumber.get(), "USER", false);

        assertTrue(registerViewModel.isValidInput(user));

    }

    @Test
    public void testTooLargePassword(){
        StringProperty firstName = new SimpleStringProperty();
        StringProperty lastName = new SimpleStringProperty();
        StringProperty username = new SimpleStringProperty();
        StringProperty password = new SimpleStringProperty();
        StringProperty phoneNumber = new SimpleStringProperty();
        StringProperty registrationMessageLabel = new SimpleStringProperty();

        firstName.bindBidirectional(registerViewModel.firstNameProperty());
        lastName.bindBidirectional(registerViewModel.lastNameProperty());
        username.bindBidirectional(registerViewModel.usernameProperty());
        password.bindBidirectional(registerViewModel.passwordProperty());
        phoneNumber.bindBidirectional(registerViewModel.phoneNumberProperty());
        registrationMessageLabel.bindBidirectional(registerViewModel.registrationMessageLabelProperty());

        firstName.set("Test");
        lastName.set("Test");
        username.set("Test");
        password.set("TestTestTestTest");
        phoneNumber.set("12345678");

        User user = new User(firstName.get(), lastName.get(), username.get(),
                password.get(), phoneNumber.get(), "USER", false);

        assertTrue(registerViewModel.isValidInput(user));

    }



    /*@Test
    public void testFailedRegister(){

    }*/


}
