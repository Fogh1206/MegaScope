package client.networking;

import shared.*;
import shared.util.EventType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements ClientImpl {
    private PropertyChangeSupport support;
    private Socket socket;
    private ObjectOutputStream outToServer;
    private boolean running = true;

    public Client() {
        support = new PropertyChangeSupport(this);
        try {
            socket = new Socket("localhost", 2910);
            outToServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to the server");
        }
        ClientSocketHandler clientSocketHandler = new ClientSocketHandler(socket,
                this);
        Thread thread = new Thread(clientSocketHandler);
        thread.setDaemon(true);
        thread.start();

    }

    public void sendToServer(Request request, EventType Result) {
        try {
            outToServer.writeObject(request);
        } catch (IOException e) {
            support.firePropertyChange(Result.toString(), null,
                    "Connection lost, restart program");
        }
    }

    @Override
    public void deactivateClient() {
        System.out.println("Client: Close Req");
        Request req = new Request(EventType.CLOSE_REQUEST, null);
        sendToServer(req, EventType.CLOSE_REQUEST);
        running = false;
        try {
            outToServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveNewInfo(User user) {
        System.out.println("Client: SaveNewInfo Req");
        Request req = new Request(EventType.SAVENEWINFO_REQUEST, user);
        User temp = (User) req.arg;
        sendToServer(req, EventType.SAVENEWINFO_RESULT);
    }

    @Override
    public void removeMovie(Show show) {
        System.out.println("Client: RemoveMovie Req");
        Request req = new Request(EventType.REMOVEMOVIE_REQUEST, show);
        sendToServer(req, EventType.REMOVEMOVIE_RESULT);
    }

    @Override
    public void getReservation(Show show) {
        System.out.println("Client: GetReservations Req");
        Request req = new Request(EventType.GETRESERVATIONS_REQUEST, show);
        sendToServer(req, EventType.GETRESERVATIONS_RESULT);
    }

    @Override public void confirmSeats(ReservationList reservationList)
    {
        Request req = new Request(EventType.RESERVEMOVIE_REQUEST,reservationList);
        sendToServer(req,EventType.RESERVEMOVIE_RESULT);
    }

    @Override
    public void cancelReservation(UserReservationInfo userReservationInfo) {
        Request req = new Request(EventType.REMOVERESERVATION_REQUEST, userReservationInfo);
        sendToServer(req, EventType.REMOVERESERVATION_RESULT);
    }

    @Override
    public void adminConfirmSeats(ReservationList reservationList) {
        Request request = new Request(EventType.ADMINBLOCKSEATS_REQUEST, reservationList);
        sendToServer(request, EventType.ADMINBLOCKSEATS_RESULT);
    }

    @Override
    public void registerUser(User newUser) {
        System.out.println("Client: RegisterUser Req");
        Request req = new Request(EventType.REGISTER_REQUEST, newUser);
        sendToServer(req, EventType.REGISTER_RESULT);
    }

    @Override
    public void login(User user) {
        System.out.println("Client: Login Req");
        Request req = new Request(EventType.LOGIN_REQUEST, user);
        sendToServer(req, EventType.LOGIN_RESULT);

    }

    @Override
    public void addMovie(Show show) {
        System.out.println("Client: AddMovie Req");
        Request request = new Request(EventType.ADDMOVIE_REQUEST, show);
        sendToServer(request, EventType.ADDMOVIE_RESULT);
    }

    @Override
    public void editMovie(Show show) {
        System.out.println("Client: EditMovie Req");
        Request request = new Request(EventType.EDITMOVIE_RESQUEST, show);
        sendToServer(request, EventType.EDITMOVIE_RESULT);
    }

    @Override
    public void getMovies() {
        System.out.println("Client: GetMovies Req");
        Request req = new Request(EventType.GETMOVIES_REQUEST, null);
        sendToServer(req, EventType.GETMOVIES_RESULT);
    }

    @Override
    public void getUsers() {
        System.out.println("Client: GetUsers Req");
        Request req = new Request(EventType.GETUSER_REQUEST, null);
        sendToServer(req, EventType.GETUSER_RESULT);
    }

    @Override
    public void getUserReservations(User user){
        System.out.println("Client: GetUserReservations Req");
        Request req = new Request(EventType.GETUSERRESERVATIONS_REQUEST, user);
        sendToServer(req, EventType.GETUSERRESERVATIONS_RESULT);
    }

    @Override
    public void addPropertyChangeListener(String name,
                                          PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name,
                                             PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }

    /**
     * Check if client is still running/online
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void receive(Request req) {
        System.out.println("Received " + req.type.toString());
        support.firePropertyChange(req.type.toString(), null, req.arg);
    }
}
