package slogo.events;

/**
 * Displayable commands are used to communicate the
 * value and behavior of a command within the environment.
 */
public interface DisplayableCommand {

  /**
   * Returns the name used to reference this command.
   * @return The name of this command.
   */
  String getName();

  /**
   * Returns the sequence of commands that this
   * user-defined command represents.
   * @return The representation of a user-defined command.
   */
  String getSignature();
}
