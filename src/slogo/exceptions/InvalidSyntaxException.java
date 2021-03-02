package slogo.exceptions;

/**
 * This exception is thrown when there is a token in a place where it should not be. If there is a malformed statement, then this is thrown
 */
public class InvalidSyntaxException extends RuntimeException {
  private String command;
  private String token;

  /**
   * Creates a new instance of this exception
   * @param command that was used
   * @param token that was incorrect
   */
  public InvalidSyntaxException(String token, String command) {
    super(String.format("Invalid Syntax: %s in %s", token, command));
    this.command = command;
    this.token = token;
  }
}
