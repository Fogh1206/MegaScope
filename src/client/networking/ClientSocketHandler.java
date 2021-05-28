package client.networking;

import shared.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientSocketHandler implements Runnable
{
  private ObjectInputStream inputStream;
  private Socket socket;
  private ClientImpl client;

  /** Two-argument constructor */
  public ClientSocketHandler(Socket socket, ClientImpl client)
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
        client.receive(req);
      }
      catch (IOException | ClassNotFoundException e)
      {
        System.out.println("Connection lost");
      }
    }
  }

}
