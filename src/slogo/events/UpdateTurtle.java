package slogo.events;

/**
 * A functional interface that allows the view to listen on on updates made to the enironment's
 * turtle.
 */
public interface UpdateTurtle {

  /**
   * Passes the information from a turtle update as a callback.
   *
   * @param e - The information describing the updated turtle's position.
   */
  public void execute(TurtleInfo e);
}
