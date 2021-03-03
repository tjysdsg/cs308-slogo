package slogo.exceptions;

/**
 * This exception is thrown when a token is not recognized.
 */
public class UnknownCommandException extends ModelException {
  private String command;
  private static final String name = "UnknownCommandException";

  /**
   * Create a new instance of this exception
   * @param command that caused the error
   */
  public UnknownCommandException(String command) {
    super(name);
    this.command = command;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, command);
  }

  /**
   * Get the command that caused the error
   * @return the command
   */
  public String getCommand() {
    return command;
  }
}
