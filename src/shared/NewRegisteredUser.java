package shared;

import java.io.Serializable;

public class NewRegisteredUser  implements Serializable
{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String birthday;

    public NewRegisteredUser( String firstName, String lastName, String username, String password,String phoneNumber,String birthday) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber=phoneNumber;
        this.birthday=birthday;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "NewRegisteredUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
