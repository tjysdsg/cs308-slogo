package slogo.exceptions;

/**
 * This Exception is used when a token is given the wrong number of parameters. It can greater or higher than the required amount
 */
public class InvalidTokenTypeException extends ModelException {
  private static final String name = "InvalidCommandIdentifierException";
  private String identifier;
  private String expected;
  private String actual;

  /**
   * Creates a new instance of this exception.
   * @param identifier the invalid name
   */
  public InvalidTokenTypeException(String identifier, String expected, String actual) {
    super(name);
    this.identifier = identifier;
    this.expected = expected;
    this.actual = actual;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, identifier, expected, actual);
  }
}