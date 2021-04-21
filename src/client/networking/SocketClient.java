package client.networking;

import shared.NewRegisteredUser;
import shared.Request;
import shared.User;
import shared.util.EventType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements Client {
    private PropertyChangeSupport support;
    private Socket socket;
    private ObjectOutputStream outToServer;
    private boolean running = true;


    public SocketClient() {
        support = new PropertyChangeSupport(this);
        startClient();
    }

    private void startClient() {
        try {
            socket = new Socket("localhost", 2910);
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            Thread thread = new Thread(this::listenToServer);
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//getMovies();
    }

    private void listenToServer() {

        try {
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            while(true) {
                Request req = (Request) inFromServer.readObject();
                support.firePropertyChange(req.type.toString(), null, req.arg);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    public void sendToServer(Request request,EventType registerResult)
    {
        try {
            outToServer.writeObject(request);
        } catch (IOException e) {
            support.firePropertyChange(registerResult.toString(), null, "Connection lost, restart program");
        }
    }
    public void deactivateClient()
    {
        running = false;
        try
        {
            outToServer.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isRunning()
    {
        return running;
    }



    @Override
    public void registerUser(NewRegisteredUser newUser) {
        Request req = new Request(EventType.REGISTER_REQUEST, newUser);
        sendToServer(req, EventType.REGISTER_RESULT);
    }

    @Override
    public void login(User user) {
        Request req = new Request(EventType.LOGIN_REQUEST, user);
        sendToServer(req, EventType.LOGIN_REQUEST);
    }

    @Override public void getMovies()
    {
        Request req = new Request(EventType.GETMOVIES_REQUEST, null);
        sendToServer(req, EventType.GETMOVIES_RESULT);
        System.out.println(12);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);

    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }
}
