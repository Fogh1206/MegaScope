package client.networking;

import shared.Movie;
import shared.NewRegisteredUser;
import shared.Request;
import shared.util.EventType;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client1 implements Client
{
  private PropertyChangeSupport support;
  private Socket socket;
  private ObjectOutputStream outToServer;
  private boolean running = true;

  public Client1()
  {
    support = new PropertyChangeSupport(this);
    try
    {
      socket = new Socket("localhost", 2910);
      outToServer = new ObjectOutputStream(socket.getOutputStream());
    }
    catch (IOException e)
    {
      throw new RuntimeException("Unable to connect to the server");
    }
    ClientSocketHandler clientSocketHandler = new ClientSocketHandler(socket,
        this);
    Thread thread = new Thread(clientSocketHandler);
    thread.setDaemon(true);
    thread.start();

  }

  public void sendToServer(Request request, EventType Result)
  {
    try
    {
      outToServer.writeObject(request);
    }
    catch (IOException e)
    {
      support.firePropertyChange(Result.toString(), null,
          "Connection lost, restart program");
    }
  }



  @Override public void deactivateClient()
  {
    System.out.println("CLose please");
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

  @Override public void saveNewInfo(NewRegisteredUser user)
  {
    System.out.println("client"+user.toString());
    Request req = new Request(EventType.SAVENEWINFO_REQUEST, user);
    NewRegisteredUser temp = (NewRegisteredUser) req.arg;
    System.out.println("Temp "+temp.getBanned());
    sendToServer(req, EventType.SAVENEWINFO_RESULT);
    System.out.println("client");
  }

  @Override
  public void removeMovie(Movie movie) {
    Request req=new Request(EventType.REMOVEMOVIE_REQUEST,movie);
    sendToServer(req,EventType.REMOVEMOVIE_RESULT);
  }


  @Override public void registerUser(NewRegisteredUser newUser)
  {
    Request req = new Request(EventType.REGISTER_REQUEST, newUser);
    sendToServer(req, EventType.REGISTER_RESULT);
  }

  @Override public void login(NewRegisteredUser user)
  {
    Request req = new Request(EventType.LOGIN_REQUEST, user);
    sendToServer(req, EventType.LOGIN_RESULT);

  }

  @Override public void addMovie(Movie movie){
    Request request = new Request(EventType.ADDMOVIE_REQUEST, movie);
    sendToServer(request, EventType.ADDMOVIE_RESULT);
  }

  @Override public void editMovie(Movie movie){
    Request request = new Request(EventType.EDITMOVIE_RESQUEST, movie);
    sendToServer(request, EventType.EDITMOVIE_RESULT);
    System.out.println("         L:ALALALLA             "+movie);
  }

  @Override public void getMovies()
  {
    System.out.println("Request getMovies");
    Request req = new Request(EventType.GETMOVIES_REQUEST, null);
    sendToServer(req, EventType.GETMOVIES_RESULT);
  }

  @Override public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);

  }

  @Override public void removePropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(name, listener);
  }



  /** Check if client is still running/online  */
  @Override public boolean isRunning()
  {
    return running;
  }

  @Override public void receive(Request req)
  {
    System.out.println("Received " + req.type.toString());
    support.firePropertyChange(req.type.toString(), null, req.arg);
  }

  @Override
  public void getUsers() {

    Request req = new Request(EventType.GETUSER_REQUEST, null);
    sendToServer(req, EventType.GETUSER_RESULT);

  }
}
