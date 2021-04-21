package server.networking;

import server.database.ManageUserDAO;
import server.database.UserDAO;
import server.modelserver.ServerModel;
import shared.NewRegisteredUser;
import shared.Request;
import shared.User;
import shared.util.EventType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

public class ServerSocketHandler implements Runnable
{

  private ServerModel serverModel;
  private Socket socket;
  private ObjectOutputStream outToClient;
  private ObjectInputStream inFromClient;
  private UserDAO userDAO;
  private boolean connected = true;

  public ServerSocketHandler(ServerModel serverModel, Socket socket)
      throws IOException
  {
    this.serverModel = serverModel;
    this.socket = socket;
    outToClient = new ObjectOutputStream(socket.getOutputStream());
    inFromClient = new ObjectInputStream(socket.getInputStream());

    userDAO = new ManageUserDAO();

  }

  @Override public void run()
  {
    while (connected)
    {
      try
      {
        Request request = (Request) inFromClient.readObject();

        if (request.type.equals(EventType.GETMOVIES_REQUEST))
        {
          //          try
          //          {
          //            Statement statement = connection.createStatement();
          //            ResultSet resultSet = statement
          //                .executeQuery("SELECT * FROM adapterEx.Movies");
          //            String queryResult = "";
          //            while (resultSet.next())
          //            {
          //              System.out.println(resultSet.getString(1));
          //              queryResult = (resultSet.getString(1));
          //            }
          //            Request response = new Request(EventType.GETMOVIES_RESULT,
          //                queryResult);
          //            outToClient.writeObject(response);
          //          }
          //          catch (SQLException e)
          //          {
          //            System.out.println("Connection failure.");
          //            e.printStackTrace();
          //          }
        }
        if (request.type.equals(EventType.LOGIN_REQUEST))
        {
          {
            User user = (User) request.arg;
            String queryResult = "Incorrect password";
            userDAO.validateUser(user.getUsername(), user.getPassword());
            if (userDAO.validateUser(user.getUsername(), user.getPassword()))
            {

              queryResult = "Correct password";
            }
            Request response = new Request(EventType.LOGIN_RESULT, queryResult);
            outToClient.writeObject(response);
          }
        }
        if (request.type.equals(EventType.REGISTER_REQUEST))
        {
          System.out.println("Register requested");
          NewRegisteredUser user = (NewRegisteredUser) request.arg;
          userDAO.createUser(user.getFirstName(), user.getLastName(),
              user.getUsername(), user.getPassword(), user.getPhoneNumber(),
              user.getBirthday());

          Request response = new Request(EventType.REGISTER_RESULT,
              "Successful");
          outToClient.writeObject(response);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }

    }
  }

}
