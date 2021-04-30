package client.view.user;

import client.view.ViewHandler;
import client.viewmodel.user.UserProfileViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class UserProfileController {
    private ViewHandler viewHandler;
    private UserProfileViewModel userProfileViewModel;

    @FXML
    private Label userCurrentFirstNameLabel;
    @FXML
    private Label userCurrentLastNameLabel;
    @FXML
    private Label userCurrentPhoneLabel;
    @FXML
    private Label userCurrentUsernameLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField setPasswordField;
    @FXML
    private TextField confirmPasswordField;


    public void init(ViewHandler viewHandler, UserProfileViewModel userProfileViewModel) {
        this.viewHandler = viewHandler;
        this.userProfileViewModel = userProfileViewModel;
        userCurrentUsernameLabel.textProperty().bindBidirectional(userProfileViewModel.currentUsernameProperty());
    userCurrentFirstNameLabel.textProperty().bindBidirectional(userProfileViewModel.currentFirstnameProperty());
    userCurrentLastNameLabel.textProperty().bindBidirectional(userProfileViewModel.currentLastnameProperty());
    userCurrentPhoneLabel.textProperty().bindBidirectional(userProfileViewModel.currentPhoneNumberProperty());
    firstnameTextField.textProperty().bindBidirectional(userProfileViewModel.newFirstNameProperty());
    lastnameTextField.textProperty().bindBidirectional(userProfileViewModel.newLastNameProperty());
    phoneTextField.textProperty().bindBidirectional(userProfileViewModel.newPhoneNumberProperty());
    usernameTextField.textProperty().bindBidirectional(userProfileViewModel.newUsernameProperty());
    setPasswordField.textProperty().bindBidirectional(userProfileViewModel.newPasswordProperty());
    confirmPasswordField.textProperty().bindBidirectional(userProfileViewModel.confirmPasswordProperty());


    }

    public void saveButtonOnAction(ActionEvent event) {
        userProfileViewModel.save();
    }

    public void closeOnAction(ActionEvent event) {
    }
}
