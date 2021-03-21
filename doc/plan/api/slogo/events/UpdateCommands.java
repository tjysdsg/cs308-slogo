package slogo.records;

/**
 * A functional interface that allows the view to listen in on updated commands within the model.
 */
@FunctionalInterface
public interface UpdateCommands {

  /**
   * Passes the information from a commands update as a callback.
   *
   * @param e - The information describing this event.
   */
  public void execute(CommandsRecord e);
}
