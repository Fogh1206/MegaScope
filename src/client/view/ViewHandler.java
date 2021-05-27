package client.view;

import client.view.Admin.AdminUsersPageController;
import client.view.cinemaHall.CinemaHallController;
import client.view.frontPage.FrontPageController;
import client.view.frontPage.UserReservationController;
import client.view.login.LoginViewController;
import client.view.movieManagement.AddMovieController;
import client.view.movieManagement.EditMovieController;
import client.view.registration.RegisterController;
import client.view.user.UserProfileController;
import client.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.Show;
import shared.User;

import java.io.IOException;

public class ViewHandler {

    private Stage mainStage;
    private ViewModelFactory vmf;
    private Stage stage;


    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
        mainStage = new Stage();

    }

    public void start() {
        showFrontPage(null);
        mainStage.show();
        mainStage.setResizable(false);
    }

    public void openLoginView(User userLoggedIn) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/Login.fxml"));
        try {
            Parent root = loader.load();
            LoginViewController ctrl = loader.getController();
            ctrl.init(vmf.getLoginViewModel(), this, userLoggedIn);
            mainStage.setTitle("Log in");
            Scene loginScene = new Scene(root);
            loginScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    ctrl.onLoginAction();
                }
            });
            mainStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddMovieView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/addMovie.fxml"));
        try {
            Parent root = loader.load();
            AddMovieController ctrl = loader.getController();
            ctrl.init(vmf.getAddMovieViewModel(), this);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add new movie");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openEditMovie(Show show) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/editMovie.fxml"));
        try {
            Parent root = loader.load();
            EditMovieController ctrl = loader.getController();
            ctrl.init(vmf.getEditMovieViewModel(), this, show);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit movie");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAdminUsersPage(User userLoggedIn) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/adminUsers.fxml"));
        try {
            Parent root = loader.load();
            AdminUsersPageController ctrl = loader.getController();
            ctrl.init(vmf.getUsersVM(), this, userLoggedIn);
            mainStage.setTitle("Manage Users");
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
            registerScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    ctrl.registerButtonOnAction();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showFrontPage(User userLoggedIn) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/userFrontPage.fxml"));
        try {
            Parent root = loader.load();
            FrontPageController ctrl = loader.getController();
            ctrl.init(vmf.getFrontPage(), this, userLoggedIn);
            mainStage.setTitle("Front page");
            Scene frontPageScene = new Scene(root);
            mainStage.setScene(frontPageScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserReservationPage(User userLoggedIn) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/userReservations.fxml"));
        try {
            Parent root = loader.load();
            UserReservationController ctrl = loader.getController();
            ctrl.init(vmf.getUserReservationVM(), this, userLoggedIn);
            mainStage.setTitle("reservations for " + userLoggedIn.getUsername());
            Scene userReservationScene = new Scene(root);
            mainStage.setScene(userReservationScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCinemaHallPage(User userLoggedIn, Show show) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/cinemaHall.fxml"));
        try {
            Parent root = loader.load();
            CinemaHallController ctrl = loader.getController();
            ctrl.init(vmf.getCinemaHallPage(), this, userLoggedIn, show);
            mainStage.setTitle("Cinema Hall");
            Scene cinemaHallScene = new Scene(root);
            mainStage.setScene(cinemaHallScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openUserProfile(User userLoggedIn) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/userProfile.fxml"));
        try {
            Parent root = loader.load();
            UserProfileController ctrl = loader.getController();
            ctrl.init(vmf.getUserProfileVM(), this, userLoggedIn);
            mainStage.setTitle("Manage profile Page");
            Scene scene = new Scene(root);
            mainStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeStage() {
        stage.close();
    }

    public void close() {
        System.out.println("Closing the client");
        mainStage.close();
    }
}

