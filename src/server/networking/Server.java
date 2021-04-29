package server.networking;

import server.modelserver.ServerModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

  private ServerModel serverModel;
  private ServerSocket serverSocket;
  private boolean running = true;

  private Socket socketClient;

  public Server(ServerModel serverModel)
  {
    this.serverModel = serverModel;

  }

  public void startServer() throws IOException
  {
    serverSocket = new ServerSocket(2910);
    System.out.println("Server started..");
    while (running)
    {
      try
      {
        System.out.println("Waiting for client..");
        socketClient = serverSocket.accept();
        ServerSocketHandler ssh = new ServerSocketHandler(serverModel,
            socketClient);
        (new Thread(ssh)).start();
        System.out.println("Client connected");
      }
      catch (IOException e)
      {
        System.out.println(e.getMessage());
      }
    }

  }

  public void closeServerSocket()
  {
    try
    {
      serverSocket.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void setRunning(boolean running)
  {
    this.running = running;
  }

}



