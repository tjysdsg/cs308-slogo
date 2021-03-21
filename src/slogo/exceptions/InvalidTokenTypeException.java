package slogo.exceptions;

/**
 * This Exception is used when a token is given the wrong number of parameters. It can greater or higher than the required amount
 */
public class InvalidTokenTypeException extends ModelException {
  private static final String name = "InvalidCommandIdentifierException";
  private String nextToken;
  private String expected;
  private String actual;
  private String currentToken;

  /**
   * Creates a new instance of this exception.
   * @param nextToken the invalid name
   */
  public InvalidTokenTypeException(String currentToken, String nextToken, String expected, String actual) {
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