package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.User;

public class UserFrontPageViewModel {


    private UserModel userModel;

    private StringProperty username,button;


    public UserFrontPageViewModel(UserModel userModel){
        this.userModel = userModel;
      username = new SimpleStringProperty();
      button=new SimpleStringProperty();

    }

    public StringProperty usernameProperty() {
        return username;
    }


    public StringProperty buttonProperty() {
        return button;
    }
}
