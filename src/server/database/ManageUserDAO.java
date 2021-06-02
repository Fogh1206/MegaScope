package server.database;

import shared.*;

import java.sql.*;
import java.util.ArrayList;

public class ManageUserDAO implements UserDAO {

    private ControllerDAO controllerDAO;
    private static ManageUserDAO instance;

    /**
     * Private zero-argument constructor (Singleton class).
     */
    private ManageUserDAO() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        controllerDAO = ControllerDAO.getInstance();
    }

    /**
     * Receive the singleton instance of {@link ManageUserDAO}.
     *
     * @return
     */
    public static synchronized ManageUserDAO getInstance() {
        if (instance == null) {
            instance = new ManageUserDAO();
        }
        return instance;
    }

    /**
     * Receive all {@link MovieShow} objects from database and add to the {@link MovieShowsList} object from parameter.
     *
     * @param showList
     * @param connection
     * @throws SQLException
     */
    private void getMovieList(MovieShowsList showList, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT movies.id, name, dateofrelease, " +
                "mainactors," + " description, time_show, date_show, show.id FROM public.movies JOIN public.show " +
                "on show.movie_id = movies.id ORDER BY movies.id");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            MovieShow temp = new MovieShow(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getInt(8));
            showList.addShow(temp);
        }
        statement.close();
    }

    /**
     * Return {@link MovieShowsList} object from retrieved {@link MovieShow} objects received from database.
     *
     * @return
     */
    @Override
    public MovieShowsList getAllMovies() {
        MovieShowsList showList = new MovieShowsList();
        try (Connection connection = controllerDAO.getConnection()) {
            getMovieList(showList, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showList;
    }


    /**
     * Return {@link MovieShowsList} object from retrieved {@link MovieShow} objects received from database.
     *
     * @return
     */

    @Override
    public MovieShowsList getAllMoviesUnique() {
        MovieShowsList showList = new MovieShowsList();
        try (Connection connection = controllerDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT movies.id, name, " +
                    "dateofrelease, mainactors," + " description FROM public.movies JOIN public.show on " +
                    "show.movie_id = movies.id ORDER BY movies.id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MovieShow temp = new MovieShow(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), null,
                        null, 0);
                showList.addShow(temp);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showList;
    }

    /**
     * Insert {@link MovieShow} object from parameter into database and return all {@link MovieShow} objects from database thereafter.
     *
     * @param movieShow
     * @return
     */
    @Override
    public MovieShowsList addMovie(MovieShow movieShow) {

        MovieShowsList showList = new MovieShowsList();
        PreparedStatement statement;
        int id = 0;
        try (Connection connection = controllerDAO.getConnection()) {
            statement = connection.prepareStatement(
                    "INSERT INTO public.movies (id, name, dateofrelease, mainactors, description)" + "VALUES " +
                            "(" + "DEFAULT" + ",'" + movieShow.getName() + "','" + movieShow.getDateOfRelease() + "','"
                            + movieShow.getMainActors() + "','" + movieShow.getDescription() + "') " +
                            "ON CONFLICT DO NOTHING");
            statement.executeUpdate();
            statement = connection.prepareStatement("SELECT movies.id FROM movies WHERE name='" +
                    movieShow.getName() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            statement = connection.prepareStatement("INSERT INTO public.show (id, movie_id, time_show, date_show)" +
                    "VALUES (" + "DEFAULT" + ",'" + id + "','" + movieShow.getTimeOfShow() + "','"
                    + movieShow.getDateOfShow() + "')");
            statement.executeUpdate();
            statement.close();
            getMovieList(showList, connection);
            return showList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Alter {@link MovieShow} object inside database and return all {@link MovieShow} objects from database in a {@link MovieShowsList} object.
     *
     * @param movieShow
     * @return
     */
    @Override
    public MovieShowsList editMovie(MovieShow movieShow) {
        MovieShowsList showList = new MovieShowsList();
        try (Connection connection = controllerDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE public.movies SET name='" + movieShow.getName() + "',dateofrelease='" +
                            movieShow.getDateOfRelease() + "',mainactors='" + movieShow.getMainActors() +
                            "',description='" + movieShow.getDescription() +
                            " 'WHERE id='" + movieShow.getMovie_id() + "'");
            statement.executeUpdate();
            statement = connection.prepareStatement(
                    "UPDATE public.show SET time_show='" + movieShow.getTimeOfShow() + "',date_show='"
                            + movieShow.getDateOfShow() + "' WHERE id='" + movieShow.getShow_id() + "'");
            statement.executeUpdate();
            statement.close();
            getMovieList(showList, connection);
            return showList;
        } catch (SQLException e) {
            throw new RuntimeException("SQL ERROR " + e);
        }
    }

    /**
     * Remove {@link MovieShow} from database which is equal to one in the database. Then return a {@link MovieShowsList} object of all {@link MovieShow} objects from database.
     *
     * @param movieShow
     * @return
     */
    @Override
    public MovieShowsList removeMovie(MovieShow movieShow) {
        MovieShowsList showList = new MovieShowsList();
        try (Connection connection = controllerDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM show WHERE id='" + movieShow.getShow_id() + "'");
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    " DELETE FROM movies WHERE NOT EXISTS(SELECT FROM show WHERE show.movie_id = movies.id)");
            statement.executeUpdate();
            statement.close();
            getMovieList(showList, connection);
            return showList;
        } catch (SQLException e) {
            throw new RuntimeException("SQL ERROR " + e);
        }
    }

    /**
     * Return reservations which contained the {@link MovieShow} object from the parameter.
     *
     * @param movieShow
     * @return
     */
    @Override
    public ArrayList<String> getReservations(MovieShow movieShow) {
        ArrayList<String> strings = new ArrayList<>();

        try (Connection connection = controllerDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT seat_id FROM public.seats WHERE blocked=true");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                strings.add(String.valueOf(resultSet.getInt(1)));
            }
            if (movieShow != null) {
                statement = connection.prepareStatement("SELECT * FROM public.reservations WHERE show_id='" +
                        movieShow.getShow_id() + "'");
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    strings.add(resultSet.getString(4));
                }
            } else {
                System.out.println("Null show");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     * Return {@link ReservationList} which contained the {@link Reservation} object from the parameter.
     *
     * @return
     */
    @Override
    public ReservationList reserveMovie(ReservationList list) {
        ReservationList reservations = new ReservationList();
        PreparedStatement statement = null;
        Reservation temp;

        try (Connection connection = controllerDAO.getConnection()) {
            for (int i = 0; i < list.size(); i++) {
                statement = connection.prepareStatement(
                        "INSERT INTO public.reservations (reservation_id,seat_id,show_id ,user_id)"
                                + "VALUES (" + "DEFAULT" + ",'" + list.get(i).getSeat_no() + "','" +
                                list.get(i).getShow_id() + "','" + list.get(i).getUser_id() + "')");
                statement.executeUpdate();
            }
            statement = connection.prepareStatement(
                    "SELECT * FROM public.reservations WHERE show_id='" + list.get(0).getShow_id() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new Reservation(resultSet.getInt(1),
                        resultSet.getInt(4), resultSet.getInt(3),
                        resultSet.getInt(2));
                reservations.add(temp);
            }
            statement.close();
            return reservations;
        } catch (SQLException e) {
            if (e.toString().contains("duplicate key")) {
                reservations.setFailed(true);
                try (Connection connection = controllerDAO.getConnection()) {

                    statement = connection.prepareStatement(
                            "SELECT * FROM public.reservations WHERE show_id='" + list.get(0).getShow_id() + "'");
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        temp = new Reservation(resultSet.getInt(1),
                                resultSet.getInt(4), resultSet.getInt(3),
                                resultSet.getInt(2));
                        reservations.add(temp);
                    }
                    statement.close();
                    return reservations;
                } catch (SQLException es) {
                    es.printStackTrace();
                }
            }
            return reservations;
        }
    }

    /**
     * Return {@link UserReservationInfoList} which contained the {@link UserReservationInfo} object from the parameter after a certain reservation was canceled.
     *
     * @return
     */
    @Override
    public UserReservationInfoList cancelReservation(UserReservationInfo userReservationInfo) {
        User user = null;
        try (Connection connection = controllerDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id FROM reservations WHERE reservation_id = " +
                            userReservationInfo.getReservation_id());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                statement = connection.prepareStatement("SELECT * FROM users WHERE id = "
                        + resultSet.getInt(1));
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    user = new User(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6),
                            resultSet.getString(7), resultSet.getBoolean(8));
                }
            }
            statement = connection.prepareStatement(
                    "DELETE FROM reservations WHERE reservation_id=" + userReservationInfo.getReservation_id());
            statement.executeUpdate();
            statement.close();

            return getUserReservation(user);
        } catch (SQLException e) {
            throw new RuntimeException("SQL ERROR " + e);
        }
    }

    /**
     * Return {@link SeatList} which contained the {@link Seat} object from the parameter after the seats are blocked by the admin.
     *
     * @return
     */
    @Override
    public SeatList adminConfirmSeats(SeatList seatList) {
        SeatList list = new SeatList();
        PreparedStatement statement;
        try (Connection connection = controllerDAO.getConnection()) {
            for (int i = 0; i < seatList.size(); i++) {
                statement = connection.prepareStatement("UPDATE public.seats SET blocked =" +
                        seatList.get(i).isDisabled() + "  WHERE seat_id = " + seatList.get(i).getId());
                statement.executeUpdate();
            }

            statement = connection.prepareStatement("SELECT * FROM public.seats ORDER by seat_id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Seat temp = new Seat(resultSet.getInt(1), resultSet.getBoolean(2));
                list.add(temp);
            }
            statement.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("SQL ERROR " + e);
        }
    }

    /**
     * Return {@link SeatList} which contained the {@link Seat} object from the parameter.
     *
     * @return
     */
    @Override
    public SeatList getAdminSeats() {
        SeatList seatList = new SeatList();
        PreparedStatement statement;
        try (Connection connection = controllerDAO.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM public.seats ORDER by seat_id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Seat temp = new Seat(resultSet.getInt(1), resultSet.getBoolean(2));
                seatList.add(temp);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("SQL ERROR " + e);
        }
        return seatList;
    }


    /**
     * Return {@link UserReservationInfoList} which contained the {@link UserReservationInfo} object from the parameter.
     *
     * @return
     */
    @Override
    public UserReservationInfoList getUserReservation(User user) {
        PreparedStatement statement;
        UserReservationInfoList userReservations = new UserReservationInfoList();
        UserReservationInfo temp;

        try (Connection connection = controllerDAO.getConnection()) {
            statement = connection.prepareStatement(
                    "SELECT reservations.reservation_id, movies.name, show.time_show, show.date_show," +
                            " reservations.seat_id " + "FROM ((reservations " +
                            "INNER JOIN show ON reservations.show_id = show.id) " +
                            "INNER JOIN movies ON show.movie_id = movies.id) " +
                            "WHERE user_id = " + user.getId() + ";");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new UserReservationInfo(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getInt(5));
                userReservations.add(temp);
            }
            statement.close();
            return userReservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Return {@link UserList} which contained the {@link User} object from the parameter.
     *
     * @return
     */
    @Override
    public UserList getAllUsers() {
        UserList users = new UserList();
        PreparedStatement statement = null;
        try (Connection connection = controllerDAO.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM public.users WHERE type ='NORM' OR " +
                    "type='VIP' Order BY id");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User temp = new User(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));
                users.add(temp);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Return {@link UserList} which contained the {@link User} object from the parameter after an user is banned.
     */
    @Override
    public UserList changeUserStatus(User user) {
        UserList users = new UserList();
        try (Connection connection = controllerDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE public.users SET banned='" + user.getBanned()
                            + "' where id=" + user.getId() + "");

            statement.executeUpdate();
            statement = connection.prepareStatement("SELECT * FROM public.users WHERE type ='NORM' OR " +
                    "type='VIP' Order BY id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User temp = new User(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));
                users.add(temp);
            }
            statement.close();
        } catch (SQLException e) {
            if (e.toString().contains("duplicate key")) {
                throw new RuntimeException("SQL ERROR " + e);
            }
        }
        return users;
    }


    /**
     * Return {@link User} after it is saved in database.
     *
     * @return
     */
    @Override
    public User registerUser(User user) {
        try (Connection connection = controllerDAO.getConnection()) {
            User temp;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO public.users(firstname,lastname,"
                    + "username,password,phonenumber,type) VALUES ('" + user.getFirstName() + "','" + user.getLastName()
                    + "','" + user.getUsername() + "','" + user.getPassword() + "','" + user.getPhoneNumber() + "','"
                    + "NORM" + "')");
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE username='" + user.getUsername() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new User(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));
                user = temp;
            }
            statement.close();
        } catch (SQLException e) {
            if (e.toString().contains("duplicate key")) {
                System.out.println("Register Fail");
            }
            throw new RuntimeException("SQL ERROR " + e);
        }
        return user;
    }

    /**
     * Return {@link User} after it is checked in the database successfully.
     *
     * @return
     */
    @Override
    public User validateUser(String username, String password) {
        User user = null;
        PreparedStatement statement;
        try (Connection connection = controllerDAO.getConnection()) {
            statement = connection.prepareStatement("SELECT password FROM public.users WHERE username='" +
                    username + "'");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString(1).equals(password)) {
                    statement = connection.prepareStatement(
                            "SELECT * FROM public.users WHERE username='" + username + "'");
                    resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        user = new User(resultSet.getInt(1),
                                resultSet.getString(2), resultSet.getString(3),
                                resultSet.getString(4), resultSet.getString(5),
                                resultSet.getString(6), resultSet.getString(7),
                                resultSet.getBoolean(8));
                    }
                    return user;
                }
            }
            statement.close();
        } catch (SQLException e) {
            if (e.toString().contains("duplicate key")) {
                System.out.println("Login fail");
            }
            throw new RuntimeException("SQL ERROR " + e);
        }
        return user;
    }

    /**
     * Return {@link User} after the new information about it is saved.
     *
     * @return
     */
    @Override
    public User saveNewInfo(User user) {
        User temp = null;
        try (Connection connection = controllerDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE public.users SET firstname='" + user.getFirstName() + "',lastname='"
                            + user.getLastName() + "',username='" + user.getUsername() + "',password='" +
                            user.getPassword() + "',phonenumber='" + user.getPhoneNumber() + "',type='" +
                            user.getUserType() + "',banned='" + user.getBanned() + "' WHERE id=" + user.getId() + "");
            statement.executeUpdate();
            statement = connection.prepareStatement(
                    "SELECT * FROM public.users WHERE id='" + user.getId() + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temp = new User(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getBoolean(8));
            }
            statement.close();
            return temp;
        } catch (SQLException e) {
            if (e.toString().contains("duplicate key")) {
                System.out.println("same username");
            }
            throw new RuntimeException("SQL ERROR " + e);
        }
    }
}
