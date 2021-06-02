package server.networking;

import server.database.ManageUserDAO;
import server.database.UserDAO;
import shared.*;
import shared.util.EventType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketHandler implements Runnable {


    private Socket socket;
    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;
    private UserDAO userDAO;
    private boolean connected = true;

    /**
     * One-argument constructor for sending and receiving the objects in/from the client.
     *
     * @param socket
     */
    public ServerSocketHandler(Socket socket) {
        this.socket = socket;
        try {
            this.outToClient = new ObjectOutputStream(socket.getOutputStream());
            this.inFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDAO = ManageUserDAO.getInstance();
    }


    /**
     * Void method for reciving the requests from the client.
     */
    @Override
    public void run() {
        while (connected) {
            try {
                Request request = (Request) inFromClient.readObject();
                switch (request.type) {
                    case GETUSER_REQUEST:
                        outToClient.writeObject(getUserRequest());
                        break;
                    case GETMOVIES_REQUEST:
                        outToClient.writeObject(getMoviesRequest());
                        break;
                    case GETMOVIESFORADD_REQUEST:
                        outToClient.writeObject(getMoviesForAddRequest());
                        break;
                    case LOGIN_REQUEST:
                        outToClient.writeObject(getLoginRequest((User) request.arg));
                        break;
                    case REGISTER_REQUEST:
                        outToClient.writeObject(getRegisterRequest((User) request.arg));
                        break;
                    case EDITMOVIE_RESQUEST:
                        outToClient.writeObject(getEditMovieRequest((MovieShow) request.arg));
                        break;
                    case SAVENEWINFO_REQUEST:
                        outToClient.writeObject(getSaveNewInfoRequest((User) request.arg));
                        break;
                    case ADDMOVIE_REQUEST:
                        outToClient.writeObject(getAddMovieRequest((MovieShow) request.arg));
                        break;
                    case REMOVEMOVIE_REQUEST:
                        outToClient.writeObject(getRemoveMovieRequest((MovieShow) request.arg));
                        break;
                    case GETRESERVATIONS_REQUEST:
                        outToClient.writeObject(getReservationsRequest((MovieShow) request.arg));
                        break;
                    case RESERVEMOVIE_REQUEST:
                        outToClient.writeObject(getReserveMovieRequest((ReservationList) request.arg));
                        break;
                    case GETUSERRESERVATIONS_REQUEST:
                        outToClient.writeObject(getUserReservations((User) request.arg));
                        break;
                    case REMOVERESERVATION_REQUEST:
                        outToClient.writeObject(cancelReservation((UserReservationInfo) request.arg));
                        break;
                    case ADMINBLOCKSEATS_REQUEST:
                        outToClient.writeObject(adminConfirmSeats((SeatList) request.arg));
                        break;
                    case GETADMINSEATS_REQUEST:
                        outToClient.writeObject(getAdminSeats());
                        break;
                    case CHANGEUSERSTATUS_REQUEST:
                        outToClient.writeObject(changeUserStatus((User) request.arg));
                        break;
                    case CLOSE_REQUEST:
                        System.out.println("Closing");
                        outToClient.writeObject(getCloseRequest());
                        close();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.connected = false;
            }
        }
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getUserRequest() {
        System.out.println("Get Users Requested");
        UserList users = userDAO.getAllUsers();
        return new Request(EventType.GETUSER_RESULT, users);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    private Request changeUserStatus(User user) {
        System.out.println("Get Change user status");
        UserList users = userDAO.changeUserStatus(user);
        return new Request(EventType.CHANGEUSERSTATUS_RESULT, users);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getMoviesRequest() {
        System.out.println("Get Movies Requested");
        MovieShowsList shows = userDAO.getAllMovies();
        return new Request(EventType.GETMOVIES_RESULT, shows);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    private Request getMoviesForAddRequest() {
        System.out.println("Get MoviesForAdd Requested");
        MovieShowsList shows = userDAO.getAllMoviesUnique();
        return new Request(EventType.GETMOVIESFORADD_RESULT, shows);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getLoginRequest(User user) {
        System.out.println("Login requested");
        User temp = userDAO.validateUser(user.getUsername(), user.getPassword());
        if (temp == null) {
            return new Request(EventType.LOGIN_RESULT, temp);
        } else {
            if (!temp.getBanned()) {
                return new Request(EventType.LOGIN_RESULT, temp);
            } else if (temp.getBanned()) {
                return new Request(EventType.LOGINFAIL_RESULT, temp);
            }
        }
        return null;
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getRegisterRequest(User user) {
        System.out.println("Register requested");
        User temp = userDAO.registerUser(user);
        if (temp != null) {
            return new Request(EventType.REGISTER_RESULT, temp);
        } else {
            return new Request(EventType.REGISTERFAIL_RESULT, temp);
        }
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getEditMovieRequest(MovieShow movieShow) {
        System.out.println("EditMovie requested");
        MovieShowsList shows = userDAO.editMovie(movieShow);
        return new Request(EventType.EDITMOVIE_RESULT, shows);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getSaveNewInfoRequest(User user) {
        System.out.println("SaveNewInfo requested");
        User temp = userDAO.saveNewInfo(user);
        if (temp != null) {
            return new Request(EventType.SAVENEWINFO_RESULT, temp);
        } else {
            return new Request(EventType.SAVENEWINFOFAIL_RESULT, null);
        }
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getAddMovieRequest(MovieShow movieShow) {
        System.out.println("AddMovie requested");
        MovieShowsList shows = userDAO.addMovie(movieShow);
        return new Request(EventType.ADDMOVIE_RESULT, shows);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getRemoveMovieRequest(MovieShow movieShow) {
        System.out.println("RemoveMovie requested");
        MovieShowsList shows = userDAO.removeMovie(movieShow);
        return new Request(EventType.REMOVEMOVIE_RESULT, shows);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getCloseRequest() {
        System.out.println("Close requested");
        return new Request(EventType.CLOSE_RESULT, "Successful");
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getReservationsRequest(MovieShow movieShow) {
        System.out.println("GetReservations requested");
        ArrayList<String> seats = userDAO.getReservations(movieShow);
        return new Request(EventType.GETRESERVATIONS_RESULT, seats);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getReserveMovieRequest(ReservationList reservationList) {
        ReservationList reservations = userDAO.reserveMovie(reservationList);
        return new Request(EventType.RESERVEMOVIE_RESULT, reservations);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request getUserReservations(User user) {
        UserReservationInfoList information = userDAO.getUserReservation(user);
        return new Request(EventType.GETUSERRESERVATIONS_RESULT, information);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    public Request cancelReservation(UserReservationInfo userReservationInfo) {
        UserReservationInfoList updatedReservations = userDAO.cancelReservation(userReservationInfo);
        return new Request(EventType.REMOVERESERVATION_RESULT, updatedReservations);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    private Request adminConfirmSeats(SeatList arg) {
        System.out.println("AdminConfirmSeats Requested");
        SeatList list = userDAO.adminConfirmSeats(arg);
        return new Request(EventType.ADMINBLOCKSEATS_RESULT, list);
    }

    /**
     * Method that returns a specific request.
     *
     * @return
     */
    private Request getAdminSeats() {
        System.out.println("GetAdminSeats Requested");
        SeatList list = userDAO.getAdminSeats();
        return new Request(EventType.GETADMINSEATS_RESULT, list);
    }


    /**
     * Close all connections from the server
     */
    private void close() {
        try {
            System.out.println("Closing the socket");
            inFromClient.close();
            outToClient.close();
            socket.close();
            this.connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
