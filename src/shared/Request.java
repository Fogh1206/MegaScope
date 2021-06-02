package shared;

import shared.util.EventType;

import java.io.Serializable;

public class Request implements Serializable
{

  /**
   * Public instance field
   */
  public EventType type;
  public Object arg;

  /**
   * Constructor with 2 arguments
   *
   * @param type
   * @param arg
   */
  public Request(EventType type, Object arg)
  {
    this.type = type;
    this.arg = arg;
  }

}
