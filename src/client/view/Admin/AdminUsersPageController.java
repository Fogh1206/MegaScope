package client.view.Admin;

import client.view.ViewHandler;

import client.viewmodel.admin.AdminViewModelUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.User;

import java.beans.PropertyChangeEvent;

public class AdminUsersPageController {
    private AdminViewModelUsers adminViewModelUsers;
    private ViewHandler viewHandler;
    private User userLoggedIn;

    @FXML private TextField searchBar;
    @FXML private TableView<User> userTableView;
    @FXML private TableColumn<Object, String> usernameCol;
    @FXML private TableColumn<Object, String> firstNameCol;
    @FXML private TableColumn<Object, String> lastNameCol;
    @FXML private TableColumn<Object, String> phoneNoCol;
    @FXML private Button banButton;



    /**
     * Initializing Method for the GUI components
     *
     * @param adminViewModelUsers AdminViewModelUsers instance for ViewModel
     * @param viewHandler         object used handling the views
     * @param userLoggedIn        object used temporarily storing the User
     */
    public void init(AdminViewModelUsers adminViewModelUsers,
                     ViewHandler viewHandler, User userLoggedIn) {
        this.adminViewModelUsers = adminViewModelUsers;
        this.viewHandler = viewHandler;
        this.userLoggedIn = userLoggedIn;
        adminViewModelUsers.getUsers();

        userTableView.itemsProperty()
                .bindBidirectional(adminViewModelUsers.observableItemsProperty());
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        searchBar.textProperty()
                .bindBidirectional(adminViewModelUsers.searchPhraseProperty());
        banButton.textProperty().bindBidirectional(adminViewModelUsers.banButtonProperty());

        adminViewModelUsers.addPropertyChangeListener("Update", this::update);
    }

    private void update(PropertyChangeEvent event) {
        System.out.println("Update Users");
        userTableView.setItems(adminViewModelUsers.getItems());
    }

    public void onBanAction() {
        adminViewModelUsers.manageUsers();
    }

    public void onBackAction() {
        viewHandler.showFrontPage(userLoggedIn);
    }

    public void Search() {
        adminViewModelUsers.search();
    }

    public void selectUser() {

        if (userTableView.getSelectionModel().getSelectedItem() != null) {
            int index = userTableView.getSelectionModel().getSelectedIndex();
            if (!userTableView.getItems().get(index).getBanned()) {
                banButton.setText("Ban");
            } else banButton.setText("Unban");

            adminViewModelUsers
                    .selectedUserToModel(userTableView.getItems().get(index));
        }
    }
}
