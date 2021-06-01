package client.viewmodel;

import client.core.ModelFactory;
import client.viewmodel.admin.AdminUsersViewModel;
import client.viewmodel.bookTickets.BookTicketsViewModel;
import client.viewmodel.frontPage.UserFrontPageViewModel;
import client.viewmodel.frontPage.UserReservationViewModel;
import client.viewmodel.login.LoginViewModel;
import client.viewmodel.movieManagement.AddMovieViewModel;
import client.viewmodel.movieManagement.EditMovieViewModel;
import client.viewmodel.registration.RegisterViewModel;
import client.viewmodel.user.UserProfileViewModel;

public class ViewModelFactory {
    private ModelFactory mf;

    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    private UserFrontPageViewModel userFrontPageViewModel;
    private BookTicketsViewModel bookTicketsViewModel;
    private UserProfileViewModel userProfileViewModel;
    private AdminUsersViewModel adminUsersViewModel;
    private AddMovieViewModel addMovieViewModel;
    private EditMovieViewModel editMovieViewModel;
    private UserReservationViewModel userReservationViewModel;

    public ViewModelFactory(ModelFactory mf) {
        this.mf = mf;
    }

    /**
     * Returns declared object of {@link RegisterViewModel} if existing otherwise creates and initializes a new one and returns new {@link RegisterViewModel} object.
     * @return
     */
    public RegisterViewModel getRegisterVM() {
        if (registerViewModel == null) {
            registerViewModel = new RegisterViewModel(mf.getUserModel());
        }
        return registerViewModel;
    }
    /**
     * Returns declared object of {@link AddMovieViewModel} if existing otherwise creates and initializes a new one and returns new {@link AddMovieViewModel} object.
     * @return
     */
    public AddMovieViewModel getAddMovieViewModel() {
        if (addMovieViewModel == null) {
            addMovieViewModel = new AddMovieViewModel(mf.getUserModel());
        }
        return addMovieViewModel;
    }
    /**
     * Returns declared object of {@link EditMovieViewModel} if existing otherwise creates and initializes a new one and returns new {@link EditMovieViewModel} object.
     * @return
     */
    public EditMovieViewModel getEditMovieViewModel() {
        if (editMovieViewModel == null) {
            editMovieViewModel = new EditMovieViewModel(mf.getUserModel());
        }
        return editMovieViewModel;
    }

    /**
     * Returns declared object of {@link AdminUsersViewModel} if existing otherwise creates and initializes a new one and returns new {@link AdminUsersViewModel} object.
     * @return
     */
    public AdminUsersViewModel getUsersVM() {

        if (adminUsersViewModel == null) {
            adminUsersViewModel = new AdminUsersViewModel(mf.getUserModel());
        }
        return adminUsersViewModel;
    }
    /**
     * Returns declared object of {@link LoginViewModel} if existing otherwise creates and initializes a new one and returns new {@link LoginViewModel} object.
     * @return
     */
    public LoginViewModel getLoginViewModel() {
        if (loginViewModel == null) {
            loginViewModel = new LoginViewModel(mf.getUserModel());
        }
        return loginViewModel;
    }
    /**
     * Returns declared object of {@link UserFrontPageViewModel} if existing otherwise creates and initializes a new one and returns new {@link UserFrontPageViewModel} object.
     * @return
     */
    public UserFrontPageViewModel getFrontPage() {
        if (userFrontPageViewModel == null) {
            userFrontPageViewModel = new UserFrontPageViewModel(mf.getUserModel());
        }
        return userFrontPageViewModel;
    }
    /**
     * Returns declared object of {@link BookTicketsViewModel} if existing otherwise creates and initializes a new one and returns new {@link BookTicketsViewModel} object.
     * @return
     */
    public BookTicketsViewModel getCinemaHallPage() {
        if (bookTicketsViewModel == null) {
            bookTicketsViewModel = new BookTicketsViewModel(mf.getUserModel());
        }
        return bookTicketsViewModel;
    }
    /**
     * Returns declared object of {@link UserProfileViewModel} if existing otherwise creates and initializes a new one and returns new {@link UserProfileViewModel} object.
     * @return
     */
    public UserProfileViewModel getUserProfileVM() {
        if (userProfileViewModel == null) {
            userProfileViewModel = new UserProfileViewModel(mf.getUserModel());
        }
        return userProfileViewModel;
    }
    /**
     * Returns declared object of {@link UserReservationViewModel} if existing otherwise creates and initializes a new one and returns new {@link UserReservationViewModel} object.
     * @return
     */
    public UserReservationViewModel getUserReservationVM() {
        if (userReservationViewModel == null) {
            userReservationViewModel = new UserReservationViewModel(mf.getUserModel());
        }
        return userReservationViewModel;
    }
}


