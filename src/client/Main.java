package client;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.view.ViewHandler;
import client.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private ModelFactory model;
    private ViewModelFactory viewModelFactory;
    private ViewHandler viewHandler;
    private ClientFactory clientFactory;

    @Override
    public void start(Stage stage) {
        clientFactory = new ClientFactory();
        model = new ModelFactory(clientFactory);
        viewModelFactory = new ViewModelFactory(model);
        viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start();
    }

    /**
     * Method ro run before client GUI closes
     */
    @Override
    public void stop() {
        try {
            viewModelFactory.getFrontPage().close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
