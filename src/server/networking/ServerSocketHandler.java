package server.networking;

import server.database.ManageUserDAO;
import server.database.UserDAO;
import server.modelserver.ServerModel;
import shared.Movie;
import shared.NewRegisteredUser;
import shared.Request;
import shared.User;
import shared.util.EventType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class ServerSocketHandler implements Runnable {

    private ServerModel serverModel;
    private Socket socket;
    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;
    private UserDAO userDAO;
    private boolean connected = true;

    public ServerSocketHandler(ServerModel serverModel, Socket socket) {
        this.serverModel = serverModel;
        this.socket = socket;
        try {
            this.outToClient = new ObjectOutputStream(socket.getOutputStream());
            this.inFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        userDAO = new ManageUserDAO();

    }

    @Override
    public void run() {
        while (connected) {
            try {
                Request request = (Request) inFromClient.readObject();
                if (request.type.equals(EventType.GETUSER_REQUEST)) {
                    System.out.println("Get Users Requested");
                  /*  NewRegisteredUser temp=new NewRegisteredUser(userDAO.getAllUsers())*/
                    ArrayList<NewRegisteredUser> users = userDAO.getAllUsers();
                    Request response = new Request(EventType.GETUSER_RESULT, users);
                    outToClient.writeObject(response);
                }
                if (request.type.equals(EventType.GETMOVIES_REQUEST)) {
                    System.out.println("Get Movies Requested");
                    ArrayList<Movie> movies = userDAO.getAllMovies();
                    Request response = new Request(EventType.GETMOVIES_RESULT, movies);
                    System.out.println("Movies size" + movies.size());
                    outToClient.writeObject(response);
                }
                if (request.type.equals(EventType.LOGIN_REQUEST)) {
                    {
                        NewRegisteredUser user = (NewRegisteredUser) request.arg;
                        NewRegisteredUser temp = userDAO
                                .validateUser(user.getId(), user.getUsername(), user.getPassword());
                        Request response = new Request(EventType.LOGIN_RESULT, temp);
                        outToClient.writeObject(response);
                    }
                }
                if (request.type.equals(EventType.REGISTER_REQUEST)) {
                    System.out.println("Register requested");
                    NewRegisteredUser user = (NewRegisteredUser) request.arg;
                    userDAO.createUser(user.getFirstName(), user.getLastName(),
                            user.getUsername(), user.getPassword(), user.getPhoneNumber());


                    Request response = new Request(EventType.REGISTER_RESULT,
                            "Successful");
                    outToClient.writeObject(response);
                }
                if (request.type.equals(EventType.SAVENEWINFO_REQUEST)) {
                    NewRegisteredUser user = (NewRegisteredUser) request.arg;
                    NewRegisteredUser temp = userDAO.saveNewInfo(user.getId(), user.getFirstName(), user.getLastName(),
                            user.getUsername(), user.getPassword(), user.getPhoneNumber());
                    Request response = new Request(EventType.SAVENEWINFO_RESULT, temp);
                    outToClient.writeObject(response);
                    System.out.println(temp.getUsername());
                }

                if (request.type.equals(EventType.CLOSE_REQUEST)) {
                    System.out.println("Close requested");
                    Request response = new Request(EventType.CLOSE_RESULT, "Successful");
                    outToClient.writeObject(response);
                    close();
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
            System.out.println("HAHA");
            inFromClient.close();
            outToClient.close();
            socket.close();
            this.connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
