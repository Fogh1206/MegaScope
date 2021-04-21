package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.User;

public class UserFrontPageViewModel {


    private UserModel userModel;

    private StringProperty username;

    public UserFrontPageViewModel(UserModel userModel){
        this.userModel = userModel;
      //  username = new SimpleStringProperty(user.getUsername());

    }

    public StringProperty usernameProperty() {
        return username;
    }



}
