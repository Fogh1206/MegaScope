package server.database;

import shared.*;

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

    private void getMovieList(ArrayList<Show> showList, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select movies2.id, name, dateofrelease, mainactors," +
                " description, time_show, date_show, show.id from public.movies2 join public.show on show.movie_id = movies2.id");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Show temp = new Show(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getInt(8));
            showList.add(temp);
        }
        statement.close();
    }

    @Override
    public ArrayList<Show> getAllMovies() {
        ArrayList<Show> showList = new ArrayList<>();

        try (Connection connection = controller.getConnection()) {
            getMovieList(showList, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showList;
    }

    @Override
    public ArrayList<Show> addMovie(Show show) {

        ArrayList<Show> showList = new ArrayList<>();
        PreparedStatement statement = null;

        try (Connection connection = controller.getConnection()) {
            statement = connection.prepareStatement(
                    "INSERT INTO public.movies2 (id, name, dateofrelease, mainactors, description)" +
                            "VALUES (" + "DEFAULT" + ",'"
                            + show.getName() + "','" + show.getDateOfRelease() + "','"
                            + show.getMainActors() + "','" + show.getDescription() + "')");
            statement.executeUpdate();

            int id = 0;
            statement = connection.prepareStatement(
                    "select movies2.id from movies2 where name='" + show.getName() + "'");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            statement = connection.prepareStatement(
                    "INSERT INTO public.show (id, movie_id, time_show, date_show)" +
                            "VALUES (" + "DEFAULT" + ",'"
                            + id + "','" + show.getTimeOfShow() + "','"
                            + show.getDateOfShow() + "')");

            statement.executeUpdate();
            statement.close();
            getMovieList(showList, connection);
            return showList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Show> editMovie(Show show) {
        ArrayList<Show> showList = new ArrayList<>();
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE public.movies2 SET name='" + show.getName() + "',dateofrelease='" +
                            show.getDateOfRelease() + "',mainactors='" + show.getMainActors() +
                            "',description='" + show.getDescription() +
                            " 'where id='" + show.getMovie_id() + "'");

            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "UPDATE public.show SET id='" + show.getMovie_id() + "',time_show='" + show
                            .getTimeOfShow() +
                            "',date_show='" + show.getDateOfShow() +
                            "' where id='" + show.getMovie_id() + "'");
            statement.executeUpdate();
            statement.close();

            getMovieList(showList, connection);
            return showList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Show> removeMovie(Show show) {

        ArrayList<Show> showList = new ArrayList<>();
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "Delete from movies2 where id='" + show.getMovie_id() + "'");
            statement.executeUpdate();
            statement.close();
            getMovieList(showList, connection);
            return showList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getReservations(Show show) {
        ArrayList<String> strings = new ArrayList<>();

        PreparedStatement statement = null;
        try (Connection connection = controller.getConnection()) {
            System.out.println(show.getShow_id() + "----------Show ID");
            System.out.println(show.getMovie_id() + "----------Movie ID");
            statement = connection.prepareStatement("SELECT * FROM public.reservations WHERE show_id='" +
                    show.getShow_id() + "'");

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
    public ReservationList reserveMovie(ReservationList list) {

        ReservationList reservations = new ReservationList();

        PreparedStatement statement = null;
        Reservation temp = null;

        try (Connection connection = controller.getConnection()) {

            for (int i = 0; i < list.size(); i++) {

                statement = connection.prepareStatement(
                        "INSERT INTO public.reservations (reservation_id,seat_id,show_id ,user_id)"
                                + "VALUES (" + "DEFAULT" + ",'" + list.get(i).getSeat_no() + "','" + list.get(i).getShow_id()
                                + "','" + list.get(i).getUser_id() + "')");

                statement.executeUpdate();
            }
            statement.close();
            statement = connection.prepareStatement(
                    "SELECT * FROM public.reservations WHERE show_id='" + list.get(0).getShow_id() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new Reservation(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4));
                System.out.println(temp);
                reservations.add(temp);
            }
            return reservations;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public UserList getAllUsers() {

        UserList users = new UserList();

        PreparedStatement statement = null;
        try (Connection connection = controller.getConnection()) {
            statement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE type ='USER' or type='VIP' Order by id");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                User temp = new User(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));

                users.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User createUser(User user) {

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
                User temp = new User(resultSet.getInt(1),
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
    public User validateUser(int id, String username, String password) {
        User user = null;
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
                        User temp = new User(resultSet.getInt(1),
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
    public User saveNewInfo(User user) {
        User temp = null;
        System.out.println("Shout from dao"+user.getUserType());
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE public.users SET firstname='" + user.getFirstName() + "',lastname='"
                            + user.getLastName() + "',username='" + user.getUsername() + "',password='" + user.getPassword()
                            + "',phonenumber='" + user.getPhoneNumber()+ "',type='" + user.getUserType() + "',banned='" + user.getBanned()
                            + "' where id=" + user.getId() + "");

            statement.executeUpdate();
            statement.close();
            System.out.println("Empty" + user.getUsername() + "               " + user.getBanned());

            statement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE id='" + user.getId() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new User(resultSet.getInt(1),
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
