package slogo.exceptions;

/**
 * This exception is thrown when a token is not recognized.
 */
public class UnknownIdentifierException extends ModelException {

  private String identifier;
  private static final String name = "UnknownIdentifierException";

  /**
   * Create a new instance of this exception
   *
   * @param identifier that caused the error
   */
  public UnknownIdentifierException(String identifier) {
    super(name);
    this.identifier = identifier;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, identifier);
  }

  /**
   * Get the command that caused the error
   *
   * @return the command
   */
  public String getIdentifier() {
    return identifier;
  }
}
