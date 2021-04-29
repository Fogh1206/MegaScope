package client.view.registration;

import client.view.ViewHandler;
import client.viewmodel.registration.RegisterViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private Button registerButton;
    @FXML
    private TextField phoneTextField;


    private RegisterViewModel registerViewModel;
    private ViewHandler viewHandler;

    public void init(RegisterViewModel registerViewModel, ViewHandler viewHandler) {
        this.registerViewModel = registerViewModel;
        this.viewHandler = viewHandler;
        firstnameTextField.textProperty().bindBidirectional(registerViewModel.firstNameProperty());
        lastnameTextField.textProperty().bindBidirectional(registerViewModel.lastNameProperty());
        usernameTextField.textProperty().bindBidirectional(registerViewModel.usernameProperty());
        setPasswordField.textProperty().bindBidirectional(registerViewModel.passwordProperty());
        confirmPasswordField.textProperty().bindBidirectional(registerViewModel.confirmPasswordProperty());
        confirmPasswordLabel.textProperty().bindBidirectional(registerViewModel.confirmPasswordLabelProperty());
        registrationMessageLabel.textProperty().bindBidirectional(
                registerViewModel.registrationMessageLabelProperty());
        phoneTextField.textProperty().bindBidirectional(registerViewModel.phoneNumberProperty());
        registerViewModel.registrationMessageLabelProperty().addListener((observableValue, oldValue, newValue) -> onRegister(newValue));
//        registerButton.disableProperty().bind(registerVM.);

    }

    private void onRegister(String newValue) {
        if (newValue.equals("Register Result")) {
            viewHandler.openLoginView();
            System.out.println(newValue);
        }

    }

    public void registerButtonOnAction(ActionEvent event) {
        registerViewModel.register();

    }

    public void closeOnAction(ActionEvent event) {
        registerViewModel.defaultFields();
        viewHandler.openLoginView();

    }
}
