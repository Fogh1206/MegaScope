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
        Request response = new Request(EventType.GETUSER_RESULT, users);
        System.out.println(users.size());
        return response;
    }

    public Request getMoviesRequest() {
        System.out.println("Get Movies Requested");
        ArrayList<Show> shows = userDAO.getAllMovies();
        Request response = new Request(EventType.GETMOVIES_RESULT, shows);
        System.out.println("Movies size" + shows.size());
        return response;
    }

    public Request getLoginRequest(User user) {

        User temp = userDAO
                .validateUser(user.getId(), user.getUsername(),
                        user.getPassword());
        Request response = new Request(EventType.LOGIN_RESULT, temp);
        return response;
    }

    public Request getRegisterRequest(User user) {

        System.out.println("Register requested");
        User temp = userDAO.createUser(user);

        Request response = new Request(EventType.REGISTER_RESULT,
                temp);
        return response;
    }

    public Request getEditMovieRequest(Show show) {

        System.out.println(show);
        ArrayList<Show> shows = userDAO.editMovie(show);
        Request response = new Request(EventType.EDITMOVIE_RESULT, shows);

        return response;
    }

    public Request getSaveNewInfoRequest(User user) {

        User temp = userDAO.saveNewInfo(user);
        Request response = new Request(EventType.SAVENEWINFO_RESULT, temp);
        return response;
    }

    public Request getAddMovieRequst(Show show) {

        ArrayList<Show> shows = userDAO.addMovie(show);
        Request response = new Request(EventType.ADDMOVIE_RESULT, shows);
        System.out.println(shows);
        return response;
    }

    public Request getRemoveMovieRequest(Show show) {

        ArrayList<Show> shows = userDAO.removeMovie(show);
        Request response = new Request(EventType.REMOVEMOVIE_RESULT, shows);
        return response;
    }

    public Request getCloseRequest() {
        System.out.println("Close requested");
        Request response = new Request(EventType.CLOSE_RESULT, "Successful");
        return response;
    }

    public Request getReservationsRequest(Show show) {
        ArrayList<String> seats = userDAO.getReservations(show);
        Request response = new Request(EventType.GETRESERVATIONS_RESULT, seats);
        return response;
    }

    public Request getReserveMovieRequest(ReservationList reservationList) {

        ReservationList reservations = userDAO.reserveMovie(reservationList);
        Request request = new Request(EventType.RESERVEMOVIE_RESULT, reservations);
        return request;
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
                        outToClient.writeObject(getAddMovieRequst((Show) request.arg));
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
