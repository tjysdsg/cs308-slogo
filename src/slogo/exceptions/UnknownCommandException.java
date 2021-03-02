package slogo.exceptions;

/**
 * This exception is thrown when a token is not recognized.
 */
public class UnknownCommandException extends RuntimeException {
  private String command;

  /**
   * Create a new instance of this exception
   * @param command that caused the error
   */
  public UnknownCommandException(String command) {
    super(String.format("Command %s Not Found", command));
    this.command = command;
  }

  /**
   * Get the command that caused the error
   * @return the command
   */
  public String getCommand() {
    return command;
  }
}
