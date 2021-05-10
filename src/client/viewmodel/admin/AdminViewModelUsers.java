package client.viewmodel.admin;

import client.model.UserModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import shared.NewRegisteredUser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class AdminViewModelUsers {
    private UserModel userModel;
    private PropertyChangeSupport support;
    private NewRegisteredUser selectedUser;

    private Property<ObservableList<NewRegisteredUser>> observableItems;
    private ObservableList<NewRegisteredUser> items;
    private StringProperty searchPhrase;
    private StringProperty banButton;


    public AdminViewModelUsers(UserModel userModel) {
        this.userModel = userModel;
        support = new PropertyChangeSupport(this);
        userModel.addPropertyChangeListener("Users Result", this::onGetUsers);

        observableItems = new SimpleListProperty<>();
        items = new SimpleListProperty<>();
        banButton = new SimpleStringProperty();
        searchPhrase = new SimpleStringProperty();
        selectedUser = null;
        banButton.setValue("Ban");
    }

    private void onGetUsers(PropertyChangeEvent event) {
        List<NewRegisteredUser> list = (ArrayList<NewRegisteredUser>) event
                .getNewValue();

        ObservableList<NewRegisteredUser> observableList = FXCollections
                .observableArrayList();
        observableList.addAll(list);
        observableItems.setValue(observableList);
        items = FXCollections.observableArrayList(list);
    }


    public void addPropertyChangeListener(String name,
                                          PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }


    public void search() {

        if (searchPhrase.getValue() == null || searchPhrase.getValue().equals("")) {

            getUsers();
        } else {
            ObservableList<NewRegisteredUser> observableList = FXCollections
                    .observableArrayList();
            for (int i = 0; i < observableItems.getValue().size(); i++) {
                System.out.println(observableItems.getValue().get(i).getUsername());

                if (observableItems.getValue().get(i).getUsername()
                        .contains(searchPhrase.getValue())) {
                    observableList.add(observableItems.getValue().get(i));
                }
            }
            observableItems.setValue(observableList);
        }
        searchPhrase.setValue(null);
    }

    public void getUsers() {
        userModel.getUsers();
    }

    public void manageUsers() {

        if (selectedUser != null) {

            selectedUser.setBanned(!selectedUser.getBanned());
            NewRegisteredUser user = new NewRegisteredUser(selectedUser.getId(),
                    selectedUser.getFirstName(), selectedUser.getLastName(), selectedUser.getUsername(),
                    selectedUser.getPassword(), selectedUser.getPhoneNumber(), selectedUser.getUserType(),
                    selectedUser.getBanned());
            userModel.saveNewInfo(user);

        }
    }

    public void selectedUserToModel(NewRegisteredUser user) {
        selectedUser = user;
    }

    public StringProperty banButtonProperty() {
        return banButton;
    }

    public StringProperty searchPhraseProperty() {
        return searchPhrase;
    }

    public Property<ObservableList<NewRegisteredUser>> observableItemsProperty() {
        return observableItems;
    }

    public ObservableList<NewRegisteredUser> getItems() {
        return items;
    }

}
