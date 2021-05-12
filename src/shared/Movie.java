package shared;

import java.io.Serializable;

public class Movie implements Serializable
{
  private String name;
  private String dateOfRelease;
  private String mainActors;
  private String description;
  private String timeOfShow;
  private String dateOfShow;
private int id;
  public Movie(String name, String dateOfRelease, String mainActors,
      String description, String timeOfShow, String dateOfShow)
  {
    this.name = name;
    this.dateOfRelease = dateOfRelease;
    this.mainActors = mainActors;
    this.description = description;
    this.timeOfShow = timeOfShow;
    this.dateOfShow = dateOfShow;
  }
  public Movie(int id,String name, String dateOfRelease, String mainActors,
               String description, String timeOfShow, String dateOfShow)
  {
    this.id=id;
    this.name = name;
    this.dateOfRelease = dateOfRelease;
    this.mainActors = mainActors;
    this.description = description;
    this.timeOfShow = timeOfShow;
    this.dateOfShow = dateOfShow;
  }

  public int getId() {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getDateOfRelease()
  {
    return dateOfRelease;
  }

  public String getMainActors()
  {
    return mainActors;
  }

  public String getDescription()
  {
    return description;
  }

  public String getTimeOfShow()
  {
    return timeOfShow;
  }

  public String getDateOfShow()
  {
    return dateOfShow;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override public String toString()
  {
    return "Movie{" + "name='" + name + '\'' + ", dateOfRelease='"
        + dateOfRelease + '\'' + ", mainActors='" + mainActors + '\''
        + ", description='" + description + '\'' + ", timeOfShow='" + timeOfShow
        + '\'' + ", dateOfShow='" + dateOfShow + '\'' + '}';
  }
}
