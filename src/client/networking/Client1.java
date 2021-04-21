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

  public void sendToServer(Request request, EventType registerResult)
  {
    try
    {
      outToServer.writeObject(request);
    }
    catch (IOException e)
    {
      support.firePropertyChange(registerResult.toString(), null,
          "Connection lost, restart program");
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


  @Override public void registerUser(NewRegisteredUser newUser)
  {
    Request req = new Request(EventType.REGISTER_REQUEST, newUser);
    sendToServer(req, EventType.REGISTER_RESULT);
  }

  @Override public void login(User user)
  {
    Request req = new Request(EventType.LOGIN_REQUEST, user);
    sendToServer(req, EventType.LOGIN_REQUEST);
  }

  @Override public void getMovies()
  {
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
    support.firePropertyChange(req.type.toString(), null, req.arg);
  }
}
