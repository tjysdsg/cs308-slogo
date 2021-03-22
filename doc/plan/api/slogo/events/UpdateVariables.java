package slogo.records;

/**
 * A functional interface that allows the view to listen in on updated variables within the model.
 */
@FunctionalInterface
public interface UpdateVariables {

  /**
   * Passes the information from a variable update as a callback.
   *
   * @param e - The information describing this event.
   */
  public void execute(VariablesRecord e);
}
