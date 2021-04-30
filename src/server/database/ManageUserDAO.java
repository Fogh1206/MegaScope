package server.database;

import shared.Movie;
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

  @Override public NewRegisteredUser validateUser(String username,
      String password)
  {
    NewRegisteredUser user = null;
    PreparedStatement statement = null;
    try (Connection connection = controller.getConnection())
    {
      statement = connection.prepareStatement(
          "SELECT password FROM public.user_account WHERE username='" + username
              + "'");

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        if (resultSet.getString(1).equals(password))
        {
          statement.close();

          statement = connection.prepareStatement(
              "SELECT * FROM public.user_account WHERE username='" + username
                  + "'");
          resultSet = statement.executeQuery();
          while (resultSet.next())
          {
            NewRegisteredUser temp = new NewRegisteredUser(
                resultSet.getString(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getString(4),
                resultSet.getString(5));
            user = temp;
            System.out.println(temp);
          }
          statement.close();
          return user;
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
    return user;
  }

  @Override public NewRegisteredUser createUser(String firstName,
      String lastName, String username, String password, String phoneNumber)
  {

    try (Connection connection = controller.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO users(firstname,lastname,username,password,phonenumber)   VALUES (?, ?, ?, ?,?);");

      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setString(3, username);
      statement.setString(4, password);
      statement.setString(5, phoneNumber);
      statement.executeUpdate();
      statement.close();
      return new NewRegisteredUser(firstName, lastName, username, password,
          phoneNumber);
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

  @Override public ArrayList<Movie> getAllMovies()
  {
    ArrayList<Movie> movieList = new ArrayList<>();

    PreparedStatement statement = null;
    try (Connection connection = controller.getConnection())
    {
      statement = connection.prepareStatement("SELECT * FROM public.movies ");

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {

        Movie temp = new Movie(resultSet.getString(1), resultSet.getString(2),
            resultSet.getString(3), resultSet.getString(4),
            resultSet.getString(5), resultSet.getString(6));

        movieList.add(temp);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

    try
    {
      statement.close();
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }

    return movieList;
  }

  @Override public ArrayList<User> getAllUsers()
  {
    return null;
  }
}
