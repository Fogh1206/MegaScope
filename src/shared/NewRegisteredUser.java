package shared;

import java.io.Serializable;

public class NewRegisteredUser implements Serializable
{
  private int id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String phoneNumber;
  private String userType;
  private boolean banned;

  public NewRegisteredUser(int id, String firstName, String lastName,
                           String username, String password, String phoneNumber, String userType,boolean banned)
  {

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.userType = userType;
    this.banned=banned;
  }

  public NewRegisteredUser(int id, String firstName, String lastName,
      String username, String password, String phoneNumber)
  {

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;

  }

  public NewRegisteredUser(String firstName, String lastName, String username,
      String password, String phoneNumber,boolean banned)
  {

    this.id = 0;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.banned=banned;
  }

  public NewRegisteredUser(String username,String firstName,String lastName)
  {
    this.id = 0;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.phoneNumber = null;
  }
  public NewRegisteredUser(String username,String password)
  {
    this.id = 0;
    this.firstName = null;
    this.lastName = null;
    this.username = username;
    this.password = password;
    this.phoneNumber = null;
  }

  public boolean getBanned() {
    return banned;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getUserType() {
    return userType;
  }

  public int getId()
  {
    return id;
  }

  public void setBanned(boolean banned) {
    this.banned = banned;
  }

  @Override
  public String toString() {
    return "NewRegisteredUser{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", userType='" + userType + '\'' +
            ", banned='" + banned + '\'' +
            '}';
  }
}
