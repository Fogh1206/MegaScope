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


    public Request getUserRequest() {
        System.out.println("Get Users Requested");
        UserList users = userDAO.getAllUsers();
        return new Request(EventType.GETUSER_RESULT, users);
    }

    public Request getMoviesRequest() {
        System.out.println("Get Movies Requested");
        ArrayList<Show> shows = userDAO.getAllMovies();
        Request response = new Request(EventType.GETMOVIES_RESULT, shows);
        System.out.println("Movies size" + shows.size());
        return new Request(EventType.GETMOVIES_RESULT, shows);
    }

    public Request getLoginRequest(User user) {
        System.out.println("Login requested");
        User temp = userDAO.validateUser(user.getId(), user.getUsername(), user.getPassword());
        return new Request(EventType.LOGIN_RESULT, temp);
    }

    public Request getRegisterRequest(User user) {
        System.out.println("Register requested");
        User temp = userDAO.registerUser(user);
        if (temp != null) {
            return new Request(EventType.REGISTER_RESULT, temp);
        } else {
            return new Request(EventType.REGISTERFAIL_RESULT, temp);
        }
    }

    public Request getEditMovieRequest(Show show) {
        System.out.println("EditMovie requested");
        ArrayList<Show> shows = userDAO.editMovie(show);
        return new Request(EventType.EDITMOVIE_RESULT, shows);
    }

    public Request getSaveNewInfoRequest(User user) {
        System.out.println("SaveNewInfo requested");
        User temp = userDAO.saveNewInfo(user);
        return new Request(EventType.SAVENEWINFO_RESULT, temp);
    }

    public Request getAddMovieRequest(Show show) {
        System.out.println("AddMovie requested");
        ArrayList<Show> shows = userDAO.addMovie(show);
        return new Request(EventType.ADDMOVIE_RESULT, shows);
    }

    public Request getRemoveMovieRequest(Show show) {
        System.out.println("RemoveMovie requested");
        ArrayList<Show> shows = userDAO.removeMovie(show);
        return new Request(EventType.REMOVEMOVIE_RESULT, shows);
    }

    public Request getCloseRequest() {
        System.out.println("Close requested");
        return new Request(EventType.CLOSE_RESULT, "Successful");
    }

    public Request getReservationsRequest(Show show) {
        System.out.println("GetReservations requested");
        ArrayList<String> seats = userDAO.getReservations(show);
        return new Request(EventType.GETRESERVATIONS_RESULT, seats);
    }

    public Request getReserveMovieRequest(ReservationList reservationList) {
        ReservationList reservations = userDAO.reserveMovie(reservationList);
        return new Request(EventType.RESERVEMOVIE_RESULT, reservations);
    }

    public Request getUserReservations(User user) {
        ArrayList<ArrayList<String>> information = userDAO.getUserReservation(user);
        return new Request(EventType.GETUSERRESERVATIONS_RESULT, information);
    }


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
                    case LOGIN_REQUEST:
                        outToClient.writeObject(getLoginRequest((User) request.arg));
                        break;
                    case REGISTER_REQUEST:
                        outToClient.writeObject(getRegisterRequest((User) request.arg));
                        break;
                    case EDITMOVIE_RESQUEST:
                        outToClient.writeObject(getEditMovieRequest((Show) request.arg));
                        break;
                    case SAVENEWINFO_REQUEST:
                        outToClient.writeObject(getSaveNewInfoRequest((User) request.arg));
                        break;
                    case ADDMOVIE_REQUEST:
                        outToClient.writeObject(getAddMovieRequest((Show) request.arg));
                        break;
                    case REMOVEMOVIE_REQUEST:
                        outToClient.writeObject(getRemoveMovieRequest((Show) request.arg));
                        break;
                    case CLOSE_REQUEST:
                        System.out.println("Closing");
                        outToClient.writeObject(getCloseRequest());
                        close();
                        break;
                    case GETRESERVATIONS_REQUEST:
                        outToClient.writeObject(getReservationsRequest((Show) request.arg));
                        break;
                    case RESERVEMOVIE_REQUEST:
                        outToClient.writeObject(getReserveMovieRequest((ReservationList) request.arg));
                        break;
                    case GETUSERRESERVATIONS_REQUEST:
                        outToClient.writeObject(getUserReservations((User)request.arg));
                        break;
                }
            } catch (Exception e) {

            }
        }
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
