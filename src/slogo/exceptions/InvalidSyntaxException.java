package slogo.exceptions;

/**
 * This exception is thrown when there is a token in a place where it should not be. If there is a malformed statement, then this is thrown
 * @author Oliver Rodas
 */
public class InvalidSyntaxException extends ModelException {
  private String command;
  private String token;
  private static final String name = "InvalidSyntaxException";

  /**
   * Creates a new instance of this exception
   * @param command that was used
   * @param token that was incorrect
   */
  public InvalidSyntaxException(String token, String command) {
    super(name);
    this.command = command;
    this.token = token;
  }


  @Override
  public String buildException(String format) {
    return String.format(format, command, token);
  }
}
