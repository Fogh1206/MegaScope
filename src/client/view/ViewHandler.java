package client.view;

import client.view.frontPage.FrontPageController;
import client.view.login.LoginViewController;
import client.view.registration.RegisterController;
import client.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHandler {

    private Stage mainStage;
    private ViewModelFactory vmf;

    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
        mainStage = new Stage();
    }

    public void start() {
       showFrontPage();
        mainStage.show();
        mainStage.setResizable(false);
    }

    public void openLoginView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/Login.fxml"));
        try {
            Parent root = loader.load();
            LoginViewController ctrl = loader.getController();
            ctrl.init(vmf.getLoginViewModel(), this);
            mainStage.setTitle("Log in");
            Scene loginScene = new Scene(root);
            mainStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openRegisterView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/Register.fxml"));
        try {
            Parent root = loader.load();
            RegisterController ctrl = loader.getController();
            ctrl.init(vmf.getRegisterVM(), this);
            mainStage.setTitle("Register");
            Scene registerScene = new Scene(root);
            mainStage.setScene(registerScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFrontPage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/userFrontPage.fxml"));
        try
        {
            Parent root = loader.load();
            FrontPageController ctrl = loader.getController();
            ctrl.init(vmf.getFrontPage(),this);
            mainStage.setTitle("Front page");
            Scene frontPageScene = new Scene(root);
            mainStage.setScene(frontPageScene);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void close()
    {
        mainStage.close();
    }
}
