package client.viewmodel.frontPage;

import client.model.UserModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import shared.Movie;
import shared.Request;
import shared.util.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFrontPageViewModel
{

  private UserModel model;
  private PropertyChangeSupport support;

  private StringProperty username, button;
  private StringProperty searchPhrase;
  private ObjectProperty datePicked;
  private Property<ObservableList<Movie>> observableItems;
  private ObservableList<Movie> items;
  private Movie selectedMovie;
  public UserFrontPageViewModel(UserModel model)
  {
    this.model = model;
    support = new PropertyChangeSupport(this);
    username = new SimpleStringProperty();
    datePicked = new SimpleObjectProperty();
    button = new SimpleStringProperty();
    searchPhrase = new SimpleStringProperty();
    items = new SimpleListProperty<>();
    observableItems = new SimpleListProperty<>();

    System.out.println("start");
    model.addPropertyChangeListener("Movie Result", this::onGetMovies);
    System.out.println("Koniec");

  }

  public void onGetMovies(PropertyChangeEvent event)
  {
    List<Movie> list = (ArrayList<Movie>) event.getNewValue();

    ObservableList<Movie> observableList = FXCollections.observableArrayList();
    observableList.addAll(list);
    System.out.println("Kappa " + observableList.size());
    observableItems.setValue(observableList);
    items = FXCollections.observableArrayList(list);
    if (searchPhrase.getValue() == null || searchPhrase.getValue().equals(""))
    {
      System.out.println("Please");
    }
    else
    {
      search();
    }
    System.out.println(datePicked.get());
    if (datePicked.get() == null)
    {
      System.out.println("Please datePick");
    }
    else
    {
      onDatePick();
    }
  }

  public void addMovie(){
    Optional<Movie> movie = openAddMovieWindow().showAndWait();

    if(movie.isPresent()){
      System.out.println(movie.get().toString());
      model.addMovie(movie.get());
    }
  }

  public Dialog<Movie> openAddMovieWindow(){
    Dialog<Movie> dialog = new Dialog<>();
    ButtonType buttonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

    VBox sceneContainer = new VBox(5);
    HBox movieTitleContainer          = new HBox();
    HBox movieDateOfReleaseContainer  = new HBox();
    HBox movieMainActorsContainer     = new HBox();
    HBox movieDescriptionContainer    = new HBox();
    HBox movieTimeOfShowContainer     = new HBox();
    HBox movieDateOfShowContainer     = new HBox();

    Label movieTitleLabel             = new Label("Movie title");
    Label movieDateOfReleaseLabel     = new Label("Date of release");
    Label movieMainActorsLabel        = new Label("Movie actors");
    Label movieDescriptionLabel       = new Label("Movie description");
    Label movieTimeOfShowLabel        = new Label("Time of show");
    Label movieDateOfShow             = new Label("Date of show");

    TextField movieTitleTextField         = new TextField();
    TextField movieDateOfReleaseTextField = new TextField();
    TextField movieMainActorsTextField    = new TextField();
    TextField movieDescriptionTextField   = new TextField();
    TextField movieTimeOfShowTextField    = new TextField();
    TextField movieDateOfShowTextField    = new TextField();

    movieTitleContainer.getChildren().addAll(movieTitleLabel,movieTitleTextField);
    movieDateOfReleaseContainer.getChildren().addAll(movieDateOfReleaseLabel,movieDateOfReleaseTextField);
    movieMainActorsContainer.getChildren().addAll(movieMainActorsLabel,movieMainActorsTextField);
    movieDescriptionContainer.getChildren().addAll(movieDescriptionLabel,movieDescriptionTextField);
    movieTimeOfShowContainer.getChildren().addAll(movieTimeOfShowLabel,movieTimeOfShowTextField);
    movieDateOfShowContainer.getChildren().addAll(movieDateOfShow,movieDateOfShowTextField);

    sceneContainer.getChildren().addAll(movieTitleContainer,movieDateOfReleaseContainer,
            movieMainActorsContainer,movieDescriptionContainer,movieTimeOfShowContainer,
            movieDateOfShowContainer);

    Node addButton = dialog.getDialogPane().lookupButton(buttonType);
    addButton.setDisable(true);

    movieTitleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      addButton.setDisable(newValue.trim().isEmpty());
    });
    Platform.runLater(movieTitleTextField::requestFocus);

    dialog.getDialogPane().setContent(sceneContainer);

    dialog.setResultConverter(dialogButton -> {
      if(dialogButton == buttonType){

        return new Movie(movieTitleTextField.getText(), movieDateOfReleaseTextField.getText(),
                movieMainActorsTextField.getText(), movieDescriptionTextField.getText(),
                movieTimeOfShowTextField.getText(), movieDateOfShowTextField.getText());
      }
      return null;
    });
    return dialog;

  }

  public StringProperty usernameProperty()
  {
    return username;
  }

  public StringProperty buttonProperty()
  {
    return button;
  }

  public void getMovies()
  {
    model.getMovies();
  }

  public ObservableList<Movie> getItems()
  {
    return items;
  }

  public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name, listener);
  }

  public void close()
  {
    model.deactivateClient();
  }

  public Property<ObservableList<Movie>> observableItemsProperty()
  {
    return observableItems;
  }

  public StringProperty searchPhraseProperty()
  {
    return searchPhrase;
  }

  public void search()
  {

      ObservableList<Movie> observableList = FXCollections
          .observableArrayList();
      for (int i = 0; i < observableItems.getValue().size(); i++)
      {
        if (observableItems.getValue().get(i).getName()
            .contains(searchPhrase.getValue()))
        {
          observableList.add(observableItems.getValue().get(i));
        }
      }
      observableItems.setValue(observableList);

    searchPhrase.setValue(null);
  }

  public void onDatePick()
  {
    ObservableList<Movie> observableList = FXCollections.observableArrayList();

    for (int i = 0; i < observableItems.getValue().size(); i++)
    {
      if (datePicked.get().toString()
          .equals(observableItems.getValue().get(i).getDateOfShow()))
      {
        observableList.add(observableItems.getValue().get(i));
      }
    }
    observableItems.setValue(observableList);

    datePicked.setValue(null);
  }

  public Property<LocalDate> getValue()
  {
    return datePicked;
  }

  public void editMovie(Movie movie){

  }

  public void removeMovie(){
    if (selectedMovie!=null)
    {


    }




  }
  public void selectedMovie(Movie movie)
  {
    selectedMovie=movie;

  }
}
