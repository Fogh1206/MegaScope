package shared;

public class MovieTest
{


  public MovieTest(String name, String dateofrelease)
  {
    this.name = name;
    this.dateofrelease = dateofrelease;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDateofrelease()
  {
    return dateofrelease;
  }

  public void setDateofrelease(String dateofrelease)
  {
    this.dateofrelease = dateofrelease;
  }
}
