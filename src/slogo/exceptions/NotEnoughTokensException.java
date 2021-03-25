package slogo.exceptions;

/**
 * This exception is used when another node is expected, but none are given
 * @author Oliver Rodas
 */
public class NotEnoughTokensException extends ModelException {
  private static final String NAME = "NotEnoughTokensException";
  private String currentToken;
  private String expectedType;

  /**
   * Instantiates a new Not enough tokens exception.
   *
   * @param currentToken the current token
   * @param expectedType the expected type
   */
  public NotEnoughTokensException(String currentToken, String expectedType) {
    super(NAME);
    this.currentToken = currentToken;
    this.expectedType = expectedType;
  }

  @Override
  public String buildException(String format) {
    return String.format(format, currentToken, expectedType);
  }
}
