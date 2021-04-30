package client.viewmodel.user;

import client.model.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeSupport;

public class UserProfileViewModel {
    private StringProperty currentUsername=new SimpleStringProperty();
    private StringProperty currentFirstname=new SimpleStringProperty();
    private StringProperty currentLastname=new SimpleStringProperty();
    private StringProperty currentPhoneNumber=new SimpleStringProperty();
    private StringProperty newFirstName=new SimpleStringProperty();
    private StringProperty newLastName=new SimpleStringProperty();
    private StringProperty newPhoneNumber=new SimpleStringProperty();
    private StringProperty newUsername=new SimpleStringProperty();
    private StringProperty newPassword=new SimpleStringProperty();
    private StringProperty confirmPassword =new SimpleStringProperty();
    private UserModel model;
    private PropertyChangeSupport support;

    public UserProfileViewModel(UserModel model) {
        this.model=model;
        support = new PropertyChangeSupport(this);
    }


    public StringProperty newPhoneNumberProperty() {
        return newPhoneNumber;
    }

    public StringProperty currentUsernameProperty() {
        return currentUsername;
    }



    public StringProperty currentFirstnameProperty() {
        return currentFirstname;
    }


    public StringProperty currentLastnameProperty() {
        return currentLastname;
    }

    public StringProperty currentPhoneNumberProperty() {
        return currentPhoneNumber;
    }



    public StringProperty newFirstNameProperty() {
        return newFirstName;
    }



    public StringProperty newLastNameProperty() {
        return newLastName;
    }



    public StringProperty newUsernameProperty() {
        return newUsername;
    }


    public StringProperty newPasswordProperty() {
        return newPassword;
    }


    public StringProperty confirmPasswordProperty() {
        return confirmPassword;
    }

    public void save() {


    }


    public void defaultsValue()
    {




    }
}
