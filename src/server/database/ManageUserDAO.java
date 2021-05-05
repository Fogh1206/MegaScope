package server.database;

import shared.Movie;
import shared.NewRegisteredUser;

import shared.User;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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

  @Override public NewRegisteredUser saveNewInfo(int id, String firstName,
      String lastName, String username, String password, String phoneNumber)
  {

    try (Connection connection = controller.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "UPDATE public.users SET firstname='" + firstName + "',lastname='"
              + lastName + "',username='" + username + "',password='" + password
              + "',phonenumber='" + phoneNumber + "' where id=" + id + "");

      System.out.println(statement);
      statement.executeUpdate();
      statement.close();
      System.out.println(username);
      return new NewRegisteredUser(firstName, lastName, username, password,
          phoneNumber);
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

  @Override
  public boolean isAdmin(int id) {

    PreparedStatement statement = null;

    try (Connection connection = controller.getConnection()) {
      statement = connection.prepareStatement(
              "SELECT * FROM public.admins WHERE id ='" + id + "'");

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        if(resultSet.getInt(1) == id){
          return true;
        }
      }

    } catch (SQLException e){
      e.printStackTrace();
    }
    return false;
  }


  @Override public NewRegisteredUser validateUser(int id, String username,
      String password)
  {
    NewRegisteredUser user = null;
    PreparedStatement statement = null;
    try (Connection connection = controller.getConnection())
    {
      statement = connection.prepareStatement(
          "SELECT password FROM public.users WHERE username='" + username + "'");

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        if (resultSet.getString(1).equals(password))
        {
          statement.close();

          statement = connection.prepareStatement(
              "SELECT * FROM public.users WHERE username='" + username + "'");
          resultSet = statement.executeQuery();
          while (resultSet.next())
          {
            NewRegisteredUser temp = new NewRegisteredUser(resultSet.getInt(1),
                resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getString(5),
                resultSet.getString(6));
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
    NewRegisteredUser user = null;
    try (Connection connection = controller.getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO users(firstname,lastname,username,password,phonenumber)   VALUES (?, ?, ?, ?,?);");

      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setString(3, username);
      statement.setString(4, password);
      statement.setString(5, phoneNumber);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {

        statement = connection.prepareStatement(
            "SELECT * FROM public.users WHERE username='" + username + "'");
        resultSet = statement.executeQuery();
        while (resultSet.next())
        {
          NewRegisteredUser temp = new NewRegisteredUser(resultSet.getInt(1),
              resultSet.getString(2), resultSet.getString(3),
              resultSet.getString(4), resultSet.getString(5),
              resultSet.getString(6));
          user = temp;
          System.out.println(temp.getId());
        }
        statement.close();

      }

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }
    return user;
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

  @Override public ArrayList<NewRegisteredUser> getAllUsers()
  {


    ArrayList<NewRegisteredUser> userList = new ArrayList<>();

    PreparedStatement statement = null;
    try (Connection connection = controller.getConnection())
    {
      statement = connection.prepareStatement("SELECT * FROM public.users ");

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {

        NewRegisteredUser temp = new NewRegisteredUser(resultSet.getInt(1),resultSet.getString(2),
                resultSet.getString(3), resultSet.getString(4),
                resultSet.getString(5), resultSet.getString(6));

        userList.add(temp);
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

    return userList;

  }
}
