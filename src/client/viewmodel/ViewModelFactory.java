package client.viewmodel;

import client.core.ModelFactory;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import client.viewmodel.frontPage.UserFrontPageViewModel;
import client.viewmodel.login.LoginViewModel;
import client.viewmodel.registration.RegisterViewModel;
import client.viewmodel.user.UserProfileViewModel;

public class ViewModelFactory
{
  private LoginViewModel loginViewModel;
  private RegisterViewModel registerViewModel;
  private ModelFactory mf;
  private UserFrontPageViewModel userFrontPageViewModel;
  private CinemaHallViewModel cinemaHallViewModel;
  private UserProfileViewModel userProfileViewModel;

  public ViewModelFactory(ModelFactory mf)
  {
    this.mf = mf;
    loginViewModel = new LoginViewModel(mf.getUserModel());

  }

  public RegisterViewModel getRegisterVM()
  {
    if (registerViewModel == null)
    {
      registerViewModel = new RegisterViewModel(mf.getUserModel());
    }
    return registerViewModel;
  }

  public LoginViewModel getLoginViewModel()
  {
    if (loginViewModel == null)
    {
      loginViewModel = new LoginViewModel(mf.getUserModel());
    }
    return loginViewModel;
  }

  public UserFrontPageViewModel getFrontPage()
  {
    if (userFrontPageViewModel == null)
    {
      userFrontPageViewModel = new UserFrontPageViewModel(mf.getUserModel());
    }

    return userFrontPageViewModel;
  }

  public CinemaHallViewModel getCinemaHallPage()
  {
    if(cinemaHallViewModel == null)
    {
      cinemaHallViewModel = new CinemaHallViewModel(mf.getUserModel());
    }

    return cinemaHallViewModel;
  }

  public UserProfileViewModel getUserProfileVM()
  {
    if( userProfileViewModel == null)
    {
      userProfileViewModel = new UserProfileViewModel(mf.getUserModel());
    }
    return userProfileViewModel;
  }

}


