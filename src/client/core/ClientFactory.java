package client.core;

import client.networking.ClientImpl;
import client.networking.Client;

public class ClientFactory
{
  private ClientImpl client;

  public ClientImpl getClient()
  {
    if (client == null)
    {
      client = new Client();
    }
    return client;
  }

}
