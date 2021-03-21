package slogo.exceptions;

public class NotEnoughTokensException extends ModelException {
  private static final String NAME = "NotEnoughTokensException";
  private String currentToken;
  private String expectedType;
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
