package server.modelserver;

import server.data.UserInt;
import shared.User;

public class ServerModelManager implements ServerModel  {

  private UserInt modelManager;

  public ServerModelManager(UserInt modelManager)
  {
    this.modelManager=modelManager;
  }

  @Override public String acceptUser(User user)
  {
    return modelManager.acceptUser(user);
  }


  public void nothing(){

  }
}
