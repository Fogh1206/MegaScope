package client.viewmodel;

import client.core.ModelFactory;
import client.viewmodel.admin.AdminViewModelMovie;
import client.viewmodel.frontPage.UserFrontPageViewModel;
import client.viewmodel.login.LoginViewModel;
import client.viewmodel.registration.RegisterViewModel;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    private ModelFactory mf;
    private UserFrontPageViewModel userFrontPageViewModel;
    private AdminViewModelMovie adminViewModelMovie;

    public ViewModelFactory(ModelFactory mf) {
        this.mf = mf;
        loginViewModel = new LoginViewModel(mf.getUserModel());

    }

    public RegisterViewModel getRegisterVM() {
        if(registerViewModel == null) {
            registerViewModel = new RegisterViewModel(mf.getUserModel());
        }
        return registerViewModel;
    }

    public LoginViewModel getLoginViewModel(){
        if(loginViewModel == null){
            loginViewModel = new LoginViewModel(mf.getUserModel());
        }
        return loginViewModel;
    }

    public UserFrontPageViewModel getFrontPage()
    {
        if(userFrontPageViewModel == null)
        {
            userFrontPageViewModel = new UserFrontPageViewModel(mf.getUserModel());
        }
         return userFrontPageViewModel;
    }

    public AdminViewModelMovie getAdminMoviePage()
    {
        if (adminViewModelMovie == null)
        {
            adminViewModelMovie = new AdminViewModelMovie(mf.getUserModel());
        }
        return adminViewModelMovie;
    }

}


