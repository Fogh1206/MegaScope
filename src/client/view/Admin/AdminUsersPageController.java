package client.view.Admin;

import client.view.ViewHandler;

import client.viewmodel.admin.AdminUsersViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import shared.User;

import java.io.File;
import java.util.Optional;

public class AdminUsersPageController {
    private AdminUsersViewModel adminUsersViewModel;
    private ViewHandler viewHandler;
    private User userLoggedIn;

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<Object, String> usernameCol;
    @FXML
    private TableColumn<Object, String> firstNameCol;
    @FXML
    private TableColumn<Object, String> lastNameCol;
    @FXML
    private TableColumn<Object, String> phoneNoCol;
    @FXML
    private TableColumn<Object, String> banCol;
    @FXML
    private Button banButton;
    @FXML
    private ImageView logoView;
    @FXML
    private Label usernameLabel;


    /**
     * Initializing Method for the GUI components
     *
     * @param adminUsersViewModel AdminViewModelUsers instance for ViewModel
     * @param viewHandler         object used handling the views
     * @param userLoggedIn        object used temporarily storing the User
     */
    public void init(AdminUsersViewModel adminUsersViewModel,
                     ViewHandler viewHandler, User userLoggedIn) {
        this.adminUsersViewModel = adminUsersViewModel;
        this.viewHandler = viewHandler;
        this.userLoggedIn = userLoggedIn;
        adminUsersViewModel.getUsers();
        banButton.setText("Ban/Unban");
        userTableView.itemsProperty().bindBidirectional(adminUsersViewModel.observableItemsProperty());
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        banCol.setCellValueFactory(new PropertyValueFactory<>("banned"));

        searchBar.textProperty().bindBidirectional(adminUsersViewModel.searchPhraseProperty());
        banButton.textProperty().bindBidirectional(adminUsersViewModel.banButtonProperty());
        usernameLabel.setText("Logged in as " + userLoggedIn.getUsername());

        try {
            File logoFile = new File("images/logo.png");
            Image logo = new Image(logoFile.toURI().toString());
            logoView.setImage(logo);
        } catch (NullPointerException e) {
            System.out.println("image problem");
        }
    }

    public void onBanAction() {
        if (userTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You are about to ban a user from the system");
            alert.setContentText("Are you sure you want to ban the user ["
                    + userTableView.getSelectionModel().getSelectedItem().getUsername()
                    + "] from the movie database?");

            if (userTableView.getSelectionModel().getSelectedItem().getBanned()) {
                alert.setHeaderText(alert.getHeaderText().replace("ban", "unban"));
                alert.setContentText(alert.getContentText().replace("ban", "unban"));
            }

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                adminUsersViewModel.manageUsers();
            }
        } else {
            System.out.println("no user");
        }
    }

    public void onBackAction() {
        viewHandler.showFrontPage(userLoggedIn);
    }

    public void Search() {
        adminUsersViewModel.search();
    }

    public void selectUser() {
        if (userTableView.getSelectionModel().getSelectedItem() != null) {
            int index = userTableView.getSelectionModel().getSelectedIndex();
            if (!userTableView.getItems().get(index).getBanned()) {
                banButton.setText("Ban");
            } else banButton.setText("Unban");
            adminUsersViewModel.selectedUserToModel(userTableView.getItems().get(index));
        }
    }

    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Search();
        }
    }
}
