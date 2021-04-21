package client.core;

import client.model.Model;
import client.model.UserModel;

public class ModelFactory {
    private UserModel userModel;
    private ClientFactory clientFactory;

    public ModelFactory(ClientFactory clientFactory) {
        this.clientFactory=clientFactory;
    }

    public UserModel getUserModel() {
        if(userModel == null) {
            userModel = new Model(clientFactory.getClient());
        }
        return userModel;
    }
}
