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

    /**
     * Zero-argument constructor
     */
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

    /**
     * Sending {@link Request} object and {@link EventType} enum object to server with {@link ObjectOutputStream}.
     * @param request
     * @param Result
     */
    public void sendToServer(Request request, EventType Result) {
        try {
            outToServer.writeObject(request);
        } catch (IOException e) {
            support.firePropertyChange(Result.toString(), null, "Connection lost, restart program");
        }
    }

    /**
     * Close socket connection to server.
     */
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
        sendToServer(req, EventType.SAVENEWINFO_RESULT);
    }

    @Override
    public void removeMovie(MovieShow movieShow) {
        System.out.println("Client: RemoveMovie Req");
        Request req = new Request(EventType.REMOVEMOVIE_REQUEST, movieShow);
        sendToServer(req, EventType.REMOVEMOVIE_RESULT);
    }

    @Override
    public void getReservation(MovieShow movieShow) {
        System.out.println("Client: GetReservations Req");
        Request req = new Request(EventType.GETRESERVATIONS_REQUEST, movieShow);
        sendToServer(req, EventType.GETRESERVATIONS_RESULT);
    }

    @Override
    public void confirmSeats(ReservationList reservationList) {
        System.out.println("Client: ConfirmSeats Req");
        Request req = new Request(EventType.RESERVEMOVIE_REQUEST, reservationList);
        sendToServer(req, EventType.RESERVEMOVIE_RESULT);
    }

    @Override
    public void cancelReservation(UserReservationInfo userReservationInfo) {
        System.out.println("Client: CancelReservations Req");
        Request req = new Request(EventType.REMOVERESERVATION_REQUEST, userReservationInfo);
        sendToServer(req, EventType.REMOVERESERVATION_RESULT);
    }

    @Override
    public void adminConfirmSeats(SeatList seatList) {
        System.out.println("Client: AdminConfirmSeats");
        Request request = new Request(EventType.ADMINBLOCKSEATS_REQUEST, seatList);
        sendToServer(request, EventType.ADMINBLOCKSEATS_RESULT);
    }

    @Override
    public void getAdminSeats() {
        System.out.println("Client: getAdminSeats");
        Request request = new Request(EventType.GETADMINSEATS_REQUEST, null);
        sendToServer(request, EventType.GETADMINSEATS_RESULT);
    }

    @Override
    public void changeUserStatus(User user) {
        System.out.println("Client: ChangeUserStatus");
        Request request = new Request(EventType.CHANGEUSERSTATUS_REQUEST, user);
        sendToServer(request, EventType.CHANGEUSERSTATUS_RESULT);
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
    public void addMovie(MovieShow movieShow) {
        System.out.println("Client: AddMovie Req");
        Request request = new Request(EventType.ADDMOVIE_REQUEST, movieShow);
        sendToServer(request, EventType.ADDMOVIE_RESULT);
    }

    @Override
    public void editMovie(MovieShow movieShow) {
        System.out.println("Client: EditMovie Req");
        Request request = new Request(EventType.EDITMOVIE_RESQUEST, movieShow);
        sendToServer(request, EventType.EDITMOVIE_RESULT);
    }

    @Override
    public void getMoviesForAdd() {
        System.out.println("Client: GetMoviesForAdd Req");
        Request req = new Request(EventType.GETMOVIESFORADD_REQUEST, null);
        sendToServer(req, EventType.GETMOVIESFORADD_RESULT);
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
    public void getUserReservations(User user) {
        System.out.println("Client: GetUserReservations Req");
        Request req = new Request(EventType.GETUSERRESERVATIONS_REQUEST, user);
        sendToServer(req, EventType.GETUSERRESERVATIONS_RESULT);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }

    /**
     * Check if client is still running/online
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * Receive all incoming {@link Request} objects from Server and use ObserverPattern to tell {@link client.model.Model} class about received {@link Request}.
     * @param req
     */
    @Override
    public void receive(Request req) {
        System.out.println("Received " + req.type.toString());
        support.firePropertyChange(req.type.toString(), null, req.arg);
    }
}
