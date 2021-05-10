package server;

import server.data.Users;
import server.modelserver.ServerModel;
import server.modelserver.ServerModelManager;
import server.networking.Server;

import java.io.IOException;

public class RunServer
{
  public static void main(String[] args)
  {
    Server server = new Server();
    try
    {
      server.startServer();
    }
    catch (IOException e)
    {
      System.out.println("Complete failure to launch");
      e.printStackTrace();
    }
  }
}

