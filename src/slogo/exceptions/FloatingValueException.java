package slogo.exceptions;

/**
 * The type Floating value exception. Used for flagging a value that does not have a parent
 * @author Oliver Rodas
 */
public class FloatingValueException extends ModelException {
  private static final String name = "FloatingValueException";
  private String currentToken;

  /**
   * Create a new runtime exception whose message is the name
   *
   * @param currentToken the current token that threw the error
   */
  public FloatingValueException(String currentToken) {
    super(name);
    this.currentToken = currentToken;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, currentToken);
  }
}
