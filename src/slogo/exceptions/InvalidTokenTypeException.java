package slogo.exceptions;

/**
 * This exception is used when an incorrect token was used when a different one was expected
 * @author Oliver Rodas
 */
public class InvalidTokenTypeException extends ModelException {
  private static final String name = "InvalidTokenTypeException";
  private String nextToken;
  private String expected;
  private String actual;
  private String currentToken;

  /**
   * Creates a new instance of this exception.
   * @param nextToken the invalid name
   */
  public InvalidTokenTypeException(String currentToken, String expected, String nextToken, String actual) {
    super(name);
    this.currentToken = currentToken;
    this.nextToken = nextToken;
    this.expected = expected;
    this.actual = actual;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, currentToken, expected, nextToken, actual);
  }
}