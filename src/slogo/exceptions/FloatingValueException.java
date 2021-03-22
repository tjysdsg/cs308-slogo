package slogo.exceptions;

public class FloatingValueException extends ModelException {
  private static final String name = "FloatingValueException";
  private String currentToken;
  /**
   * Create a new runtime exception whose message is the name
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
