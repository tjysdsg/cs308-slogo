package slogo.events;

/**
 * Displayable variables are used to communicate the
 * value of a variable within the environment.
 */
public interface DisplayableVariable {

  /**
   * The name that this variable has
   * within an environment.
   * @return The string representation of a variable
   */
  String getName();

  /**
   * The value which this variable contains
   * within the environment.
   *
   * @return The value of this variable.
   */
  double getValue();
}
