package client.viewmodel;

import client.core.ModelFactory;
import client.viewmodel.admin.AdminViewModelUsers;
import client.viewmodel.cinemaHall.CinemaHallViewModel;
import client.viewmodel.frontPage.UserFrontPageViewModel;
import client.viewmodel.login.LoginViewModel;
import client.viewmodel.movieManagement.AddMovieViewModel;
import client.viewmodel.movieManagement.EditMovieViewModel;
import client.viewmodel.registration.RegisterViewModel;
import client.viewmodel.user.UserProfileViewModel;

public class ViewModelFactory
{
  private ModelFactory mf;

  private LoginViewModel loginViewModel;
  private RegisterViewModel registerViewModel;
  private UserFrontPageViewModel userFrontPageViewModel;
  private CinemaHallViewModel cinemaHallViewModel;
  private UserProfileViewModel userProfileViewModel;
  private AdminViewModelUsers adminViewModelUsers;
  private AddMovieViewModel addMovieViewModel;
  private EditMovieViewModel editMovieViewModel;

  public ViewModelFactory(ModelFactory mf)
  {
    this.mf = mf;
  }

  public RegisterViewModel getRegisterVM()
  {
    if (registerViewModel == null)
    {
      registerViewModel = new RegisterViewModel(mf.getUserModel());
    }
    return registerViewModel;
  }

  public AddMovieViewModel getAddMovieViewModel(){
    if(addMovieViewModel == null){
      addMovieViewModel = new AddMovieViewModel(mf.getUserModel());
    }
    return addMovieViewModel;
  }

  public EditMovieViewModel getEditMovieViewModel(){
    if(editMovieViewModel == null){
      editMovieViewModel = new EditMovieViewModel(mf.getUserModel());
    }
    return editMovieViewModel;
  }

  public AdminViewModelUsers getUsersVM()
  {

    if (adminViewModelUsers == null)
    {
      adminViewModelUsers = new AdminViewModelUsers(mf.getUserModel());
    }
    return adminViewModelUsers;
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


