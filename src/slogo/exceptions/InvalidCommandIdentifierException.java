package slogo.exceptions;

/**
 * This Exception is used when a token is given the wrong number of parameters. It can greater or higher than the required amount
 */
public class InvalidCommandIdentifierException extends ModelException {
  private static final String name = "InvalidCommandIdentifierException";
  private String identifier;

  /**
   * Creates a new instance of this exception.
   * @param identifier the invalid name
   */
  public InvalidCommandIdentifierException(String identifier) {
    super(name);
    this.identifier = identifier;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, identifier);
  }
}