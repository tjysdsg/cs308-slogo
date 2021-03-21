package slogo.exceptions;

public class NotEnoughTokensException extends ModelException {
  private static final String NAME = "NotEnoughTokensException";

  public NotEnoughTokensException() {
    super(NAME);
  }

  @Override
  public String buildException(String format) {
    return format;
  }
}
