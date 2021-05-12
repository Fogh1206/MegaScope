package server.database;

import shared.Movie;
import shared.NewRegisteredUser;
import shared.Reservation;

import java.sql.*;
import java.util.ArrayList;

public class ManageUserDAO implements UserDAO {

    private Controller controller;
    private static ManageUserDAO instance;

    private ManageUserDAO() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        controller = Controller.getInstance();
    }

    public static synchronized ManageUserDAO getInstance() {
        if (instance == null) {
            instance = new ManageUserDAO();
        }
        return instance;
    }

    @Override
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movieList = new ArrayList<>();

        PreparedStatement statement = null;
        try (Connection connection = controller.getConnection()) {
            //  statement = connection.prepareStatement("SELECT * FROM public.movies ");
            statement = connection.prepareStatement("select movies2.id, name, dateofrelease, mainactors, description, time_show, date_show from public.movies2 join public.show on show.movie_id = movies2.id");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Movie temp = new Movie(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7));
                System.out.println(1);
                movieList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movieList;
    }

    @Override
    public ArrayList<Movie> addMovie(Movie movie) {

        ArrayList<Movie> movieList = new ArrayList<>();
        PreparedStatement statement = null;

        try (Connection connection = controller.getConnection()) {

            statement = connection.prepareStatement(
                    "INSERT INTO public.movies2 (id, name, dateofrelease, mainactors, description)" +
                            "VALUES (" + "DEFAULT" + ",'"
                            + movie.getName() + "','" + movie.getDateOfRelease() + "','"
                            + movie.getMainActors() + "','" + movie.getDescription() + "')");

            statement.executeUpdate();

            int id = 0;
            statement = connection.prepareStatement(
                    "select movies2.id from movies2 where name='" + movie.getName() + "'");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                id = resultSet.getInt(1);
            }
            System.out.println(id);

            statement = connection.prepareStatement(
                    "INSERT INTO public.show (id, movie_id, time_show, date_show)" +
                            "VALUES (" + "DEFAULT" + ",'"
                            + id + "','" + movie.getTimeOfShow() + "','"
                            + movie.getDateOfShow() + "')");

            statement.executeUpdate();

            statement = connection.prepareStatement("select movies2.id, name, dateofrelease, mainactors, description, time_show, date_show from public.movies2 join public.show on show.movie_id = movies2.id");


            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Movie temp = new Movie(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7));

                movieList.add(temp);
            }
            statement.close();
            return movieList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Movie> editMovie(Movie movie) {

        System.out.println("I'm editting the movie " + movie.getId());
        ArrayList<Movie> movieList = new ArrayList<>();
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE public.movies2 SET name='" + movie.getName() + "',dateofrelease='"
                            + movie.getDateOfRelease() + "',mainactors='" + movie.getMainActors() + "',description='" + movie.getDescription()
                            + " 'where id='" + movie.getId() + "'");


            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "UPDATE public.show SET id='" + movie.getId() + "',time_show='" + movie.getTimeOfShow() + "',date_show='" + movie.getDateOfShow()
                            + "' where id='" + movie.getId() + "'");
            statement.executeUpdate();

            System.out.println("       " + movie.getName());
            System.out.println("          " + movie.getId());
            statement = connection.prepareStatement("select movies2.id, name, dateofrelease, mainactors, description, time_show, date_show from public.movies2 join public.show on show.movie_id = movies2.id");


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Movie temp = new Movie(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7));

                movieList.add(temp);
            }
            statement.close();

            return movieList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Movie> removeMovie(Movie movie) {

        ArrayList<Movie> movieList = new ArrayList<>();
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "Delete from movies2 where id='" + movie.getId() + "'");
            statement.executeUpdate();

            statement = connection.prepareStatement("select movies2.id, name, dateofrelease, mainactors, description, time_show, date_show from public.movies2 join public.show on show.movie_id = movies2.id");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Movie temp = new Movie(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7));

                movieList.add(temp);
            }
            statement.close();
            return movieList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getReservations(Movie movie) {
        ArrayList<String> strings = new ArrayList<>();

        PreparedStatement statement = null;
        try (Connection connection = controller.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM public.reservations WHERE movie_id='" + movie.getId() + "'");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                strings.add(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return strings;
    }

    @Override
    public Reservation reserveMovie(Reservation reservation) {
        PreparedStatement statement = null;
        Reservation temp = null;
        try (Connection connection = controller.getConnection()) {

            statement = connection.prepareStatement(
                    "INSERT INTO public.reservation (res_id,seat_id,movie_id ,user_id)" +
                            "VALUES (" + "DEFAULT" + ",'"
                            + reservation.getSeat_no() + "','" + reservation.getMovie_id() + "','"
                            + reservation.getUser_id() + "')");

            statement.executeUpdate();
            statement.close();
            statement = connection.prepareStatement(
                    "SELECT * FROM public.reservation WHERE id='" + reservation.getReservation_id() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new Reservation(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4));
                System.out.println(temp);
            }

            return temp;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<NewRegisteredUser> getAllUsers() {

        ArrayList<NewRegisteredUser> userList = new ArrayList<>();

        PreparedStatement statement = null;
        try (Connection connection = controller.getConnection()) {
            statement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE type ='USER' or type='VIP' Order by id");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                NewRegisteredUser temp = new NewRegisteredUser(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));

                userList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    @Override
    public NewRegisteredUser createUser(NewRegisteredUser user) {

        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users(firstname,lastname,username,password,phonenumber,type)   VALUES (?, ?, ?, ?,?,?);");

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, "USER");

            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE username='" + user.getUsername() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                NewRegisteredUser temp = new NewRegisteredUser(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));
                user = temp;
                System.out.println(temp.getId());
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public NewRegisteredUser validateUser(int id, String username, String password) {
        NewRegisteredUser user = null;
        PreparedStatement statement = null;
        try (Connection connection = controller.getConnection()) {
            statement = connection.prepareStatement(
                    "SELECT password FROM public.users WHERE username='" + username
                            + "'");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString(1).equals(password)) {
                    statement.close();

                    statement = connection.prepareStatement(
                            "SELECT * FROM public.users WHERE username='" + username + "'");
                    resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        NewRegisteredUser temp = new NewRegisteredUser(resultSet.getInt(1),
                                resultSet.getString(2), resultSet.getString(3),
                                resultSet.getString(4), resultSet.getString(5),
                                resultSet.getString(6), resultSet.getString(7),
                                resultSet.getBoolean(8));
                        user = temp;
                        System.out.println(temp);
                    }
                    statement.close();
                    return user;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public NewRegisteredUser saveNewInfo(NewRegisteredUser user) {
        NewRegisteredUser temp = null;
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE public.users SET firstname='" + user.getFirstName() + "',lastname='"
                            + user.getLastName() + "',username='" + user.getUsername() + "',password='" + user.getPassword()
                            + "',phonenumber='" + user.getPhoneNumber() + "',banned='" + user.getBanned()
                            + "' where id=" + user.getId() + "");

            statement.executeUpdate();
            statement.close();
            System.out.println("Empty" + user.getUsername() + "               " + user.getBanned());

            statement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE id='" + user.getId() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new NewRegisteredUser(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));
                System.out.println(temp);
            }

            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }
}
