package server.networking;

import server.database.ManageUserDAO;
import server.database.UserDAO;
import server.modelserver.ServerModel;
import shared.Movie;
import shared.NewRegisteredUser;
import shared.Request;
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
        ArrayList<NewRegisteredUser> users = userDAO.getAllUsers();
        Request response = new Request(EventType.GETUSER_RESULT, users);
        return response;
    }

    public Request getMoviesRequest() {
        System.out.println("Get Movies Requested");
        ArrayList<Movie> movies = userDAO.getAllMovies();
        Request response = new Request(EventType.GETMOVIES_RESULT, movies);
        System.out.println("Movies size" + movies.size());
        return response;
    }

    public Request getLoginRequest(NewRegisteredUser user) {

        NewRegisteredUser temp = userDAO
                .validateUser(user.getId(), user.getUsername(),
                        user.getPassword());
        Request response = new Request(EventType.LOGIN_RESULT, temp);
        return response;
    }

    public Request getRegisterRequest(NewRegisteredUser user) {

        System.out.println("Register requested");
        NewRegisteredUser temp = userDAO.createUser(user);

        Request response = new Request(EventType.REGISTER_RESULT,
                temp);
        return response;
    }

    public Request getEditMovieRequest(Movie movie) {

        System.out.println(movie);
        ArrayList<Movie> movies = userDAO
                .editMovie(movie);
        Request response = new Request(EventType.EDITMOVIE_RESULT, movies);

        return response;
    }

    public Request getSaveNewInfoRequest(NewRegisteredUser user) {

        NewRegisteredUser temp = userDAO
                .saveNewInfo(user);
        Request response = new Request(EventType.SAVENEWINFO_RESULT, temp);
        return response;
    }

    public Request getAddMovieRequst(Movie movie) {

        ArrayList<Movie> movies = userDAO
                .addMovie(movie);
        Request response = new Request(EventType.ADDMOVIE_RESULT, movies);
        System.out.println(movies);
        return response;
    }

    public Request getRemoveMovieRequest(Movie movie) {

        ArrayList<Movie> movies = userDAO.removeMovie(movie);
        Request response = new Request(EventType.REMOVEMOVIE_RESULT, movies);
        return response;
    }

    public Request getCloseRequest() {
        System.out.println("Close requested");
        Request response = new Request(EventType.CLOSE_RESULT, "Successful");
        return response;
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
                        outToClient.writeObject(getLoginRequest((NewRegisteredUser) request.arg));
                        break;
                    case REGISTER_REQUEST:
                        outToClient.writeObject(getRegisterRequest((NewRegisteredUser) request.arg));
                        break;
                    case EDITMOVIE_RESQUEST:
                        outToClient.writeObject(getEditMovieRequest((Movie) request.arg));
                        break;
                    case SAVENEWINFO_REQUEST:
                        outToClient.writeObject(getSaveNewInfoRequest((NewRegisteredUser) request.arg));
                        break;
                    case ADDMOVIE_REQUEST:
                        outToClient.writeObject(getAddMovieRequst((Movie) request.arg));
                        break;
                    case REMOVEMOVIE_REQUEST:
                        outToClient.writeObject(getRemoveMovieRequest((Movie) request.arg));
                        break;
                    case CLOSE_REQUEST:
                        System.out.println("Closing");
                        outToClient.writeObject(getCloseRequest());
                        close();
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
