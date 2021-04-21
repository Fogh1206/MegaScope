package client.networking;

import shared.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientSocketHandler implements Runnable
{
  private ObjectInputStream inputStream;
  private Socket socket;
  private Client client;

  /** Constructor */
  public ClientSocketHandler(Socket socket, Client client)
  {
    this.socket = socket;
    this.client = client;
  }

  /** Run method for the thread */
  @Override public void run()
  {
    try
    {
      inputStream = new ObjectInputStream(socket.getInputStream());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    while (client.isRunning())
    {
      try
      {
        Request req = (Request) inputStream.readObject();
        System.out.println(req.type);
        client.receive(req);
      }
      catch (IOException | ClassNotFoundException e)
      {
        e.printStackTrace();
      }

    }
  }

}
