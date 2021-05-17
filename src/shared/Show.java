package shared;

import java.io.Serializable;

public class Show implements Serializable
{
  private String name;
  private String dateOfRelease;
  private String mainActors;
  private String description;
  private String timeOfShow;
  private String dateOfShow;
  private int movie_id;
  private int show_id;

  public Show(String name, String dateOfRelease, String mainActors,
      String description, String timeOfShow, String dateOfShow)
  {
    this.name = name;
    this.dateOfRelease = dateOfRelease;
    this.mainActors = mainActors;
    this.description = description;
    this.timeOfShow = timeOfShow;
    this.dateOfShow = dateOfShow;
  }
  public Show(int movie_id,String name, String dateOfRelease, String mainActors,
               String description, String timeOfShow, String dateOfShow,int show_id)
  {
    this.movie_id=movie_id;
    this.name = name;
    this.dateOfRelease = dateOfRelease;
    this.mainActors = mainActors;
    this.description = description;
    this.timeOfShow = timeOfShow;
    this.dateOfShow = dateOfShow;
    this.show_id = show_id;
  }

  public int getMovie_id()
  {
    return movie_id;
  }

  public int getShow_id()
  {
    return show_id;
  }

  public void setMovie_id(int movie_id)
  {
    this.movie_id = movie_id;
  }

  public void setShow_id(int show_id)
  {
    this.show_id = show_id;
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



  @Override public String toString()
  {
    return "Movie{" + "name='" + name + '\'' + ", dateOfRelease='"
        + dateOfRelease + '\'' + ", mainActors='" + mainActors + '\''
        + ", description='" + description + '\'' + ", timeOfShow='" + timeOfShow
        + '\'' + ", dateOfShow='" + dateOfShow + '\'' + '}';
  }
}
