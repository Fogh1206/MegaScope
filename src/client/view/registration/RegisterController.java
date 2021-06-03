package client.view.registration;

import client.view.ViewHandler;
import client.viewmodel.registration.RegisterViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.User.User;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.io.File;

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
    private TextField phoneTextField;
    @FXML
    private ImageView logoView;

    private RegisterViewModel registerViewModel;
    private ViewHandler viewHandler;


    /**
     * Method which is used to setup the controller.
     * Used as an constructor except it has to be called manually.
     *
     * @param registerViewModel
     * @param viewHandler
     */

    public void init(RegisterViewModel registerViewModel, ViewHandler viewHandler) {
        this.registerViewModel = registerViewModel;
        registerViewModel.clearMessages();
        this.viewHandler = viewHandler;

        firstnameTextField.textProperty().bindBidirectional(registerViewModel.firstNameProperty());
        lastnameTextField.textProperty().bindBidirectional(registerViewModel.lastNameProperty());
        usernameTextField.textProperty().bindBidirectional(registerViewModel.usernameProperty());
        setPasswordField.textProperty().bindBidirectional(registerViewModel.passwordProperty());
        confirmPasswordField.textProperty().bindBidirectional(registerViewModel.confirmPasswordProperty());
        confirmPasswordLabel.textProperty().bindBidirectional(registerViewModel.confirmPasswordLabelProperty());
        registrationMessageLabel.textProperty().bindBidirectional(registerViewModel.registrationMessageLabelProperty());
        phoneTextField.textProperty().bindBidirectional(registerViewModel.phoneNumberProperty());
        try {
            File logoFile = new File("images/logo.png");
            Image logo = new Image(logoFile.toURI().toString());
            logoView.setImage(logo);
        } catch (NullPointerException e) {
        }
        registerViewModel.addPropertyChangeListener(EventType.REGISTER_RESULT.toString(), this::onRegister);
    }

    /**
     * Method to run when the server signals that it has successfully registered the user in the database.
     *
     * @param event
     */
    private void onRegister(PropertyChangeEvent event) {
        User user = (User) event.getNewValue();
        if (user != null) {
            registerViewModel.removePropertyChangeListener(EventType.REGISTER_RESULT.toString(), this::onRegister);
            viewHandler.showFrontPage(user);
        }
    }

    /**
     * Method connected to FXML, so when button Register is pressed this method will run.
     * Registration process of the inputted Strings will be processed in @{@link RegisterViewModel}.
     */
    public void registerButtonOnAction() {
        registerViewModel.register();
    }

    /**
     * Method connected to FXML, so when button Close is pressed this method will run.
     * defaultFields() sets all TextField to be empty.
     */
    public void closeOnAction() {
        registerViewModel.defaultFields();
        registerViewModel.removePropertyChangeListener(EventType.REGISTER_RESULT.toString(), this::onRegister);
        viewHandler.openLoginView(null);
    }
}
