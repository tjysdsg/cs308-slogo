package slogo.exceptions;

public class FloatingValueException extends ModelException {
  private static final String name = "FloatingValueException";

  /**
   * Create a new runtime exception whose message is the name
   */
  public FloatingValueException() {
    super(name);
  }

  @Override
  public String buildException(String format) {
    return format;
  }
}
