package server.database;

import shared.NewRegisteredUser;
import shared.Request;
import shared.User;
import shared.util.EventType;

import java.sql.*;
import java.util.ArrayList;

public class ManageUserDAO implements UserDAO
{

  private static ManageUserDAO instance;
  private Controller controller;

  public ManageUserDAO()
  {
    try
    {
      DriverManager.registerDriver(new org.postgresql.Driver());
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    controller = Controller.getInstance();
  }

  public static synchronized ManageUserDAO getInstance()
  {
    if (instance == null)
    {
      instance = new ManageUserDAO();
    }
    return instance;
  }

  @Override public boolean validateUser(String username, String password)
  {
    PreparedStatement statement = null;
    try (Connection connection = controller.getConnection())
    {
      statement = connection.prepareStatement(
          "SELECT password FROM public.user_account WHERE username='" + username + "'");

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        if (resultSet.getString(1).equals(password))
        {
          statement.close();
          return true;
        }
      }
    }

    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    try
    {
      statement.close();
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override public NewRegisteredUser createUser(String firstName,
      String lastName, String username, String password, String phoneNumber,
      String birthday)
  {

    try (Connection connection = controller.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO user_account  VALUES (?, ?, ?, ?,?,?);");

      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setString(3, username);
      statement.setString(4, password);
      statement.setString(5, birthday);
      statement.setString(6, phoneNumber);
      statement.executeUpdate();
      statement.close();
      return new NewRegisteredUser(firstName, lastName, username, password,
          phoneNumber, birthday);
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

  @Override public ArrayList<User> getAllUsers()
  {
    return null;
  }
}
